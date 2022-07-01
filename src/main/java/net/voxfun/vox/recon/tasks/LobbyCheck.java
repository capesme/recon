package net.voxfun.vox.recon.tasks;

import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.GameState;
import net.voxfun.vox.recon.manager.MinimumPlayerAmount;
import net.voxfun.vox.recon.manager.scoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyCheck extends BukkitRunnable {
    private final GameManager gameManager;
    private int playersAmount = 0;

    public LobbyCheck(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        playersAmount = Bukkit.getOnlinePlayers().size();
        if (gameManager.getGameState() != GameState.LOBBY || playersAmount < MinimumPlayerAmount.get()) {
            cancel();
            return;
        }
        scoreboardManager.updateLobby();

        if (playersAmount >= MinimumPlayerAmount.get()) {
            gameManager.setGameState(GameState.WAITING);
        }
    }
}
