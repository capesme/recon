package net.voxfun.vox.recon.manager;

import net.voxfun.vox.recon.index;
import net.voxfun.vox.recon.listners.DamagedListener;
import net.voxfun.vox.recon.mod.FormatBroadcast;
import net.voxfun.vox.recon.mod.GenerateId;
import net.voxfun.vox.recon.tasks.GameEndCountdownTask;
import net.voxfun.vox.recon.tasks.GameMatchCountdownTask;
import net.voxfun.vox.recon.tasks.GameStartCountdownTask;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bson.Document;
import org.bson.json.JsonObject;
import org.bson.types.ObjectId;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class GameManager {
    private final index plugin;
    private GameState gameState = GameState.LOBBY;
    private final BlockManager blockManager;
    private static PlayerManager playerManager;
    private final DamagedListener damagedListener;
    private static GameStartCountdownTask gameStartCountdownTask;
    private static GameMatchCountdownTask gameMatchCountdownTask;
    private static GameEndCountdownTask gameEndCountdownTask;
    public String activeGameId = null;

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
                MapManager.reloadMaps();
                Map<String, Document> maps = MapManager.getMaps();
                Bukkit.getWorlds().forEach(world -> world.setDifficulty(Difficulty.EASY));
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.setInvulnerable(true);
                    player.teleport(new Location(player.getWorld(), 215, 40, -47));
                    player.setSaturation(player.getSaturation() + 100);
                });
                maps.forEach((mapName, doc) -> {
                    TextComponent textComponent = new TextComponent(mapName);
                    textComponent.setText(FormatBroadcast.format(String.format("Vote for %s", ChatColor.GREEN + mapName)));
                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vote " + mapName));
                    Bukkit.spigot().broadcast(textComponent);
                });
                ScoreboardManager.clear();
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
        ScoreboardManager.clear();
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

        playerManager.clearKits(true);

        final World[] currentWorld = {null};

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
    }
    public BlockManager getBlockManager() { return blockManager; }
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
            playerDocumentF.add(
                    new Document("uuid", player.getUniqueId().toString().replace("-", ""))
                            .append("username", player.getName())
                            .append("stats", new JsonObject("{\"kills\": 0, \"deaths\": 0}"))
            );
            player.setInvulnerable(true);
            player.setScoreboard(ScoreboardManager.create());
            player.teleport(new Location(player.getWorld(), x, y, z));
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
            e.printStackTrace();
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
            Bukkit.getLogger().info(winners.stream().map(player -> player.getName()).toString());
        } else {
            Bukkit.broadcastMessage(FormatBroadcast.format("No one won the game!"));
        }
        games.updateOne(
                new Document("id", activeGameId),
                new BasicDBObject("$set", new BasicDBObject("status", "finished").append("finishedOn", new Date()))
        );
        Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("game_setting_" + activeGameId);
        if (team != null) { team.unregister(); }
        if (forced) {
            setGameState(GameState.LOBBY);
        } else {
            gameEndCountdownTask = new GameEndCountdownTask(this);
            gameEndCountdownTask.runTaskTimer(plugin, 0, 20);
        }
    }
}
