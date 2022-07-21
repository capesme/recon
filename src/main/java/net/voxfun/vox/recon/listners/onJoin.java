package net.voxfun.vox.recon.listners;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.voxfun.vox.recon.manager.*;
import net.voxfun.vox.recon.mod.FormatBroadcast;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Map;

public class onJoin implements Listener {
    private GameManager gameManager;

    public onJoin(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    private int playersAmount = 0;


    @EventHandler
    private void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        playersAmount = Bukkit.getOnlinePlayers().size();

        if (gameManager.getGameState() == GameState.LOBBY && playersAmount >= MinimumPlayerAmount.get()) {
            gameManager.setGameState(GameState.WAITING);
        }

        player.setInvulnerable(true);
        player.setSaturation(player.getSaturation() + 100);
        player.setHealth(20d);
        player.setFoodLevel(20);
        player.getInventory().clear();
        player.setLevel(0);
        player.teleport(new Location(player.getWorld(),215.5, 40, -47));
        event.setJoinMessage("");

        if (gameManager.getGameState() == GameState.ACTIVE) {
            scoreboardManager.inGameAddPlayer(event.getPlayer());
            event.getPlayer().sendMessage("There is a game currently active, please wait until it ends.");
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
            for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                if (!allPlayers.getGameMode().equals(GameMode.SPECTATOR) && !allPlayers.hasPermission("recon.specs.seeSpecs")) {
                    allPlayers.hidePlayer(player);
                }

                if (!allPlayers.getGameMode().equals(GameMode.SPECTATOR) && !allPlayers.hasPermission("recon.specs.seeChat")) {
                } else {
                    allPlayers.sendMessage(FormatBroadcast.specFormat(player.getName() + " joined the game."));
                }
            }
        } else if (gameManager.getGameState() == GameState.LOBBY || gameManager.getGameState() == GameState.WAITING) {
            Map<String, Document> maps = MapManager.getMaps();
            maps.forEach((mapName, doc) -> {
                TextComponent textComponent = new TextComponent(mapName);
                textComponent.setText(FormatBroadcast.format(String.format("Vote for %s", ChatColor.GREEN + mapName)));
                textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vote " + mapName));
                player.spigot().sendMessage(textComponent);
                player.setGameMode(GameMode.ADVENTURE);
                for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                    player.showPlayer(allPlayers);
                }
            });

            {

                Bukkit.getOnlinePlayers().forEach(player1 -> {
                    scoreboardManager.inLobbyScoreboardCreate();
                    scoreboardManager.inLobbyAddPlayer(player1);

                    scoreboardManager.updateLobby();
                });
            }
            Bukkit.broadcastMessage(FormatBroadcast.format(player.getName() + " joined the lobby " + ChatColor.GREEN + "(" + playersAmount + "/15)"));
        }
    }
}
