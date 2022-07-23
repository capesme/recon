package net.voxfun.vox.recon.listners;

import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.GameState;
import net.voxfun.vox.recon.manager.scoreboardManager;
import net.voxfun.vox.recon.mod.FormatBroadcast;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
        int timer = 2;

        if (gameManager.getGameState()  == GameState.LOBBY || gameManager.getGameState() == GameState.STARTING) {
            scoreboardManager.updateLobbyLeave();
        }

        event.setQuitMessage("");
        if (gameManager.getGameState() == GameState.ACTIVE) {
            DamagedListener.alivePlayers.remove(player);
            if (DamagedListener.alivePlayers.size() < 2) {
                gameManager.setGameState(GameState.WON);
            }
            for (Player players : Bukkit.getOnlinePlayers()) {
                    if (!players.getGameMode().equals(GameMode.SPECTATOR) && !players.hasPermission("recon.specs.seeChat")) {
                    } else {
                        players.sendMessage(FormatBroadcast.specFormat(player.getName() + " left the game."));
                    }
                }
            } else {
            Bukkit.broadcastMessage(FormatBroadcast.format(String.format("%s left the lobby.", event.getPlayer().getName())));
        }

        if ((playersAmount - 1) < 2) {
            GameManager.cleanup();
            gameManager.setGameState(GameState.LOBBY);
            if (gameManager.getGameState() == GameState.ACTIVE) {
                Bukkit.broadcastMessage(FormatBroadcast.format("Game has been stopped because the minimum player requirement isn't met."));
            }

            do {
                timer--;
                if (timer == 1) {
                    scoreboardManager.updateLobbyLeave();
                }

            } while (timer > 0);
        }
    }
}
