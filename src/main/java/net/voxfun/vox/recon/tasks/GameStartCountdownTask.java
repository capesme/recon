package net.voxfun.vox.recon.tasks;

import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.GameState;
import net.voxfun.vox.recon.mod.FormatBroadcast;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStartCountdownTask extends BukkitRunnable {
    private final GameManager gameManager;

    public GameStartCountdownTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    private int timeLeft = 6;

    @Override
    public void run() {
        if (gameManager.getGameState() != GameState.STARTING) {
            cancel();
            timeLeft = 6;
            return;
        }
        timeLeft--;
        if (timeLeft <= 0) {
            cancel();
            gameManager.setGameState(GameState.ACTIVE);
            return;
        }
        Bukkit.broadcastMessage(FormatBroadcast.format(timeLeft + " until game starts!"));
    }
}
