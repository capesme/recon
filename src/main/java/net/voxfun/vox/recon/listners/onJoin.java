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

    @EventHandler
    private void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setInvulnerable(true);
        player.setSaturation(player.getSaturation() + 100);
        player.setHealth(20d);
        player.setFoodLevel(20);
        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(new Location(player.getWorld(),216, 40, -41));
        event.setJoinMessage("");
        String formatted = FormatBroadcast.format(String.format("%s joined the lobby", event.getPlayer().getName()));
        if (gameManager.getGameState() == GameState.ACTIVE) {
            ScoreboardManager.addPlayer(event.getPlayer());
            event.getPlayer().sendMessage(formatted);
            event.getPlayer().sendMessage("There is a game currently active, please wait until it ends.");
        } else if (gameManager.getGameState() == GameState.LOBBY) {
            Map<String, Document> maps = MapManager.getMaps();
            maps.forEach((mapName, doc) -> {
                TextComponent textComponent = new TextComponent(mapName);
                textComponent.setText(FormatBroadcast.format(String.format("Vote for %s", ChatColor.GREEN + mapName)));
                textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vote " + mapName));
                player.spigot().sendMessage(textComponent);
            });
            Bukkit.broadcastMessage(formatted);
        }
    }
}
