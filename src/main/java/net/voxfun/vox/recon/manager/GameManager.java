package net.voxfun.vox.recon.manager;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.voxfun.vox.recon.index;
import net.voxfun.vox.recon.listners.DamagedListener;
import net.voxfun.vox.recon.mod.FormatBroadcast;
import net.voxfun.vox.recon.mod.GenerateId;
import net.voxfun.vox.recon.tasks.*;
import org.bson.Document;
import org.bson.json.JsonObject;
import org.bson.types.ObjectId;
import org.bukkit.*;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class GameManager {
    private final index plugin;
    private GameState gameState = GameState.LOBBY;
    private final BlockManager blockManager;
    private static PlayerManager playerManager;
    private final DamagedListener damagedListener;
    private static GameStartCountdownTask gameStartCountdownTask;
    private static GameMatchCountdownTask gameMatchCountdownTask;
    private static GameEndCountdownTask gameEndCountdownTask;
    private static PlayerInteractEvent playerInteractEvent;
    private static PreGameStartCountdownTask preGameStartCountdownTask;
    private static LobbyCheck lobbyCheck;
    public static AimPraticeMinigame aimPraticeMinigame;
    public static RespawnOKTask respawnOKTask;
    public String activeGameId = null;
    private static Location lastLocation = null;

    public GameManager(index plugin) {
        this.plugin = plugin;
        this.blockManager = new BlockManager(this);
        playerManager = new PlayerManager(this);
        this.damagedListener = new DamagedListener(this);
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.setHealth(20d);
            player.setSaturation(20f);
            player.setFoodLevel(20);
            player.setGameMode(GameMode.ADVENTURE);
        });
        switch (gameState) {
            case LOBBY:
                if (getGameState() == GameState.LOBBY); {
                    lobbyCheck = new LobbyCheck(this);
                    lobbyCheck.runTaskTimer(plugin, 5, 40);
                }

                if (getGameState() == GameState.LOBBY || getGameState() == GameState.WAITING); {
                    aimPraticeMinigame = new AimPraticeMinigame(this);
                    aimPraticeMinigame.runTaskTimer(plugin, 0, 0);
                }


                MapManager.reloadMaps();
                Map<String, Document> maps = MapManager.getMaps();
                Bukkit.getWorlds().forEach(world -> world.setDifficulty(Difficulty.EASY));
                Bukkit.getOnlinePlayers().forEach(player -> {
                    Location Spawn = new Location(player.getWorld(), 215, 40, -47);

                    if (player.getWorld().getNearbyEntities(Spawn, 12, 15, 10).contains(player) && !(player.getGameMode().equals(GameMode.SPECTATOR))) {
                        return;
                    } else {
                        player.teleport(Spawn);
                    }
                    player.setSaturation(player.getSaturation() + 100);
                    player.setInvulnerable(true);

                    player.setScoreboard(scoreboardManager.inLobbyScoreboardCreate());
                    scoreboardManager.inLobbyAddPlayer(player);

                    scoreboardManager.updateLobby();
                });
                maps.forEach((mapName, doc) -> {
                    TextComponent textComponent = new TextComponent(mapName);
                    textComponent.setText(FormatBroadcast.format(String.format("Vote for %s", ChatColor.GREEN + mapName)));
                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vote " + mapName));
                    Bukkit.spigot().broadcast(textComponent);
                });
                Bukkit.getOnlinePlayers().forEach(player -> player.setLevel(0));
                break;
            case WAITING:
                if (getGameState() == GameState.LOBBY || getGameState() == GameState.WAITING); {
                aimPraticeMinigame = new AimPraticeMinigame(this);
                aimPraticeMinigame.runTaskTimer(plugin, 0, 0);
            }

                preGameStart(false);
                break;
            case ACTIVE:
                Bukkit.broadcastMessage(FormatBroadcast.format("Game has started!"));
                playerManager.setGameModes(GameMode.ADVENTURE);
                playerManager.giveKits();
                Bukkit.getOnlinePlayers().forEach(player -> player.setInvulnerable(false));
                Bukkit.getWorlds().forEach(world -> world.setDifficulty(Difficulty.NORMAL));
                gameMatchCountdownTask = new GameMatchCountdownTask(this);
                gameMatchCountdownTask.runTaskTimer(plugin, 0, 20);
                break;
            case STARTING:
                startGame(false);
                break;
            case WON:
                endGame(false);
                break;
            case FORCED_START:
                startGame(true);
                Bukkit.getOnlinePlayers().forEach(player -> player.setLevel(0));
                break;
            case FORCED_END:
                endGame(true);
                break;
        }
    }

    public GameState getGameState() { return gameState; }
    public static void cleanup() {
        DamagedListener.alivePlayers.clear();
        DamagedListener.deaths.clear();
        DamagedListener.kills.clear();
        scoreboardManager.clear();
        PreGameStartCountdownTask.preGameTime = 61;
        if (gameStartCountdownTask != null) {
            gameStartCountdownTask.cancel();
            gameStartCountdownTask = null;
        }
        if (gameEndCountdownTask != null) {
            gameEndCountdownTask.cancel();
            gameEndCountdownTask = null;
        }
        if (gameMatchCountdownTask != null) {
            gameMatchCountdownTask.cancel();
            gameMatchCountdownTask = null;
        }
        if (preGameStartCountdownTask != null) {
            preGameStartCountdownTask.cancel();
            preGameStartCountdownTask = null;
        }

        playerManager.clearKits(true);

        final World[] currentWorld = {null};

        for (Player allPlayers : Bukkit.getOnlinePlayers()) {
            allPlayers.showPlayer(allPlayers);
        }

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.setInvulnerable(true);
            PlayerManager.setGameMode(player, PlayerManager.getGameMode(player));
            currentWorld[0] = player.getWorld();
        });

        if (currentWorld[0] != null) {
            currentWorld[0].getEntities().forEach(entity -> {
                if (entity.getName().equals("ReconArrow")) { entity.remove(); }
            });
        }

        lastLocation = null;
    }
    public BlockManager getBlockManager() { return blockManager; }
    private void preGameStart(Boolean forced) {
        if (Bukkit.getOnlinePlayers().size() > (MinimumPlayerAmount.get() - 1) && gameState.equals(GameState.WAITING)) {
            preGameStartCountdownTask = new PreGameStartCountdownTask(this);
            preGameStartCountdownTask.runTaskTimer(plugin, 0, 60);
        }
    }
    private void startGame(Boolean forced) {
        if (Bukkit.getOnlinePlayers().size() < 2) {
            setGameState(GameState.LOBBY);
            return;
        }
        String gameIdF = GenerateId.random();
        activeGameId = gameIdF;
        MongoCollection<Document> games = index.database.getCollection("recon_games");
        MapManager.setMap();
        Document selectedMapF = MapManager.getMap();
        List<Document> mapSpawnsF = selectedMapF.getList("spawns", Document.class);
        List<Document> mapSettingsF = selectedMapF.getList("settings", Document.class);
        Scoreboard scoreboardF = Bukkit.getScoreboardManager().getMainScoreboard();
        Team teamF = scoreboardF.registerNewTeam("game_setting_" + activeGameId);
        mapSettingsF.forEach(setting -> {
            if (setting.containsKey("NAME_TAG_VISIBILITY")) {
                if (setting.get("NAME_TAG_VISIBILITY").toString().equals("NEVER")) {
                    teamF.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
                }
            }
            if (setting.containsKey("ALLOWED_INTERACTED_BLOCKS")) { new allowedInteractionBlockList(setting.getList("ALLOWED_INTERACTED_BLOCKS", String.class)); }
        });
        List<Object> playerDocumentF = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(player -> {
            DamagedListener.alivePlayers.add(player);
            Random rand = new Random();
            Document xyz = mapSpawnsF.get(rand.nextInt(mapSpawnsF.size()));
            if (xyz == null) { xyz = mapSpawnsF.get(rand.nextInt(mapSpawnsF.size())); }
            Integer x = xyz.getInteger("x");
            Integer y = xyz.getInteger("y");
            Integer z = xyz.getInteger("z");
            Location Spawn = new Location(player.getWorld(), x, y, z);
            Player Player = player;
            World World = player.getWorld();

            if (World.getNearbyEntities(Spawn, 1.5, 2.5 ,1.5).size() == 0) {
                player.teleport(Spawn);
                return;
            } else {
                new RespawnOKTask(new Location(World, x, y, z), Player).runTaskTimer(plugin, 1, 20);
            }

            playerDocumentF.add(
                    new Document("uuid", player.getUniqueId().toString().replace("-", ""))
                            .append("username", player.getName())
                            .append("stats", new JsonObject("{\"kills\": 0, \"deaths\": 0}"))
            );
            player.setInvulnerable(true);
            player.setScoreboard(scoreboardManager.inGameCreate());
            scoreboardManager.inGameAddPlayer(player);
            player.teleport(Spawn);
            PlayerManager.setGame(player, gameIdF);
            teamF.addEntry(player.getName());
        });
        Document gameF = new Document("_id", new ObjectId());
        gameF.append("id", gameIdF);
        gameF.append("createdAt", new Date());
        gameF.append("map", selectedMapF.get("mapName"));
        gameF.append("players", playerDocumentF);
        gameF.append("status", "active");
        try {
            games.insertOne(gameF);
        } catch (NoSuchMethodError e) {
            index.logger.info(e.toString());
        }
        playerManager.saveInventorys();
        if (forced) {
            setGameState(GameState.ACTIVE);
        } else {
            gameStartCountdownTask = new GameStartCountdownTask(this);
            gameStartCountdownTask.runTaskTimer(plugin, 0, 20);
        }
    }
    private void endGame(Boolean forced) {
        MongoCollection<Document> games = index.database.getCollection("recon_games");
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.getInventory().clear();
            player.setInvulnerable(true);
            for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                player.showPlayer(allPlayers);
            }
            scoreboardManager.clear();
            PlayerManager.setGameMode(player, GameMode.SPECTATOR);
            if (Bukkit.getScoreboardManager().getMainScoreboard().getTeam(player.getUniqueId().toString()) != null) {
                Bukkit.getScoreboardManager().getMainScoreboard().getTeam(player.getUniqueId().toString()).addEntry(player.getName());
            }
        });
        MapManager.reloadMaps();
        Map<Player, Integer> allKillsF = DamagedListener.getKills();
        if (allKillsF.size() > 0) {
            Integer mostKills = Collections.max(allKillsF.values());
            List<Player> winners = new ArrayList<>();
            for (Map.Entry<Player, Integer> entry : allKillsF.entrySet()) {
                Player player = entry.getKey();
                Integer kills = DamagedListener.getKills().getOrDefault(player, 0);
                Integer deaths = DamagedListener.getDeaths().getOrDefault(player, 0);
                int xp = kills - deaths;
                Document query = new Document();
                query.append("id", activeGameId);
                query.append("players.uuid", player.getUniqueId().toString().replace("-", ""));
                BasicDBObject updateFields = new BasicDBObject();
                updateFields.append("players.$.stats.kills", kills);
                updateFields.append("players.$.stats.deaths", deaths);
                if (entry.getValue().equals(mostKills)) {
                    winners.add(entry.getKey());
                    xp = kills - deaths + 10;
                    updateFields.append("players.$.stats.won", true);
                } else {
                    updateFields.append("players.$.stats.won", false);
                }
                if (xp < 0) { xp = 0; }
                player.sendMessage(String.format("You had %s kills and %s deaths.", kills, deaths));
                player.sendMessage(String.format("Totalling over %s xp.", xp));
                games.updateOne(query, new BasicDBObject("$set", updateFields));
            }
            sendWinnerRequest(activeGameId, winners);
            if (winners.size() == 2) {
                Bukkit.broadcastMessage(FormatBroadcast.format(String.format("%s and %s have won the game!", winners.get(0).getName(), winners.get(1).getName())));
            } else {
                winners.stream().map(HumanEntity::getName).forEach(player -> Bukkit.broadcastMessage(FormatBroadcast.format(String.format("%s has won the game", player))));
            }
        } else {
            Bukkit.broadcastMessage(FormatBroadcast.format("No one won the game!"));
        }
        games.updateOne(new Document("id", activeGameId), new BasicDBObject("$set", new BasicDBObject("status", "finished").append("finishedOn", new Date())));
        Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("game_setting_" + activeGameId);
        if (team != null) { team.unregister(); }
        if (forced) {
            setGameState(GameState.LOBBY);
            cleanup();
        } else {
            gameEndCountdownTask = new GameEndCountdownTask(this);
            gameEndCountdownTask.runTaskTimer(plugin, 0, 20);
        }
    }
    private static void sendWinnerRequest(String matchId, List<Player> winners) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("https://voxfun.net/api/match/win?key=likhwef892uh3jnfajknf");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.connect();
            String Winners = winners.stream().map(player -> String.format("{\"UUID\": \"%s\", \"name\": \"%s\", \"xp\": 12, \"deaths\": 2, \"kills\": 5}", player.getUniqueId(), player.getName())).collect(Collectors.toList()).stream().collect(StringBuilder::new, (x, y) -> x.append(y), (a, b) -> a.append(", ").append(b)).toString();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(String.format("{\"matchId\": \"%s\", \"winners\": [%s]}", matchId, Winners));
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) { connection.disconnect(); }
        }
    }
}
