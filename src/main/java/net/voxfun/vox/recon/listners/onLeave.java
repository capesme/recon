package net.voxfun.vox.recon.listners;

import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.GameState;
import net.voxfun.vox.recon.manager.MinimumPlayerAmount;
import net.voxfun.vox.recon.manager.scoreboardManager;
import net.voxfun.vox.recon.mod.FormatBroadcast;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class onLeave implements Listener {
    private GameManager gameManager;

    public onLeave(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    private int playersAmount = 0;

    @EventHandler
    private void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        playersAmount = Bukkit.getOnlinePlayers().size();

        if (playersAmount <= 2) {
            gameManager.setGameState(GameState.LOBBY);
            GameManager.cleanup();
            Bukkit.broadcastMessage(FormatBroadcast.format("Game has been stopped because the minimum player requirement isn't met."));
        }

        event.setQuitMessage("");
        Bukkit.broadcastMessage(FormatBroadcast.format(String.format("%s left the lobby", event.getPlayer().getName())));
        if (gameManager.getGameState() == GameState.ACTIVE) {
            DamagedListener.alivePlayers.remove(player);
            if (DamagedListener.alivePlayers.size() < 2) {
                gameManager.setGameState(GameState.WON);
            }
        }
    }
}
