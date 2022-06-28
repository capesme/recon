package net.voxfun.vox.recon.listners;

import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.GameState;
import net.voxfun.vox.recon.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Collection;

public class PlayerChatListener implements Listener {
    private GameManager gameManager;

    public PlayerChatListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (player.getGameMode().equals(GameMode.SPECTATOR) && gameManager.getGameState().equals(GameState.ACTIVE)) {
            for(Player allPlayers : Bukkit.getOnlinePlayers())

                if (!allPlayers.getGameMode().equals(GameMode.SPECTATOR) || !player.hasPermission("recon.seeppecchat")) {
                    Player noneSpecs = allPlayers.getPlayer();
                    event.getRecipients().remove(noneSpecs);
                }

            event.setFormat(ChatColor.GRAY + "[SPECTATOR] " + player.getName() + ": " + message);
            event.setMessage(message);

        } else {
        }
    }
}
