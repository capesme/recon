package net.voxfun.vox.recon.listners;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.GameState;
import net.voxfun.vox.recon.manager.MapManager;
import net.voxfun.vox.recon.manager.ScoreboardManager;
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

        if (playersAmount == 1 && gameManager.getGameState() == GameState.WAITING) {
            gameManager.setGameState(GameState.LOBBY);
        }

        if (playersAmount == 1 && gameManager.getGameState() == GameState.LOBBY) {
            gameManager.setGameState(GameState.LOBBY);
        }

        player.setInvulnerable(true);
        player.setSaturation(player.getSaturation() + 100);
        player.setHealth(20d);
        player.setFoodLevel(20);
        player.getInventory().clear();
        player.setLevel(0);
        player.teleport(new Location(player.getWorld(),215.5, 40, -47));
        event.setJoinMessage("");
        String formatted = FormatBroadcast.format(String.format("%s joined the lobby " + ChatColor.GREEN + "(" + playersAmount + "/15)", event.getPlayer().getName()));

        if (gameManager.getGameState() == GameState.ACTIVE) {
            ScoreboardManager.addPlayer(event.getPlayer());
            event.getPlayer().sendMessage(formatted);
            event.getPlayer().sendMessage("There is a game currently active, please wait until it ends.");
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
        } else if (gameManager.getGameState() == GameState.LOBBY || gameManager.getGameState() == GameState.WAITING) {
            Map<String, Document> maps = MapManager.getMaps();
            maps.forEach((mapName, doc) -> {
                TextComponent textComponent = new TextComponent(mapName);
                textComponent.setText(FormatBroadcast.format(String.format("Vote for %s", ChatColor.GREEN + mapName)));
                textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vote " + mapName));
                player.spigot().sendMessage(textComponent);
                player.setGameMode(GameMode.ADVENTURE);
            });

            if (gameManager.getGameState() == GameState.LOBBY && playersAmount >= 2) {
                gameManager.setGameState(GameState.WAITING);
            }

            Bukkit.broadcastMessage(formatted);
        }
    }
}
