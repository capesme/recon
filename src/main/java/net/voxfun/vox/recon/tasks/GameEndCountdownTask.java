package net.voxfun.vox.recon.tasks;

import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.GameState;
import org.bukkit.scheduler.BukkitRunnable;

public class GameEndCountdownTask extends BukkitRunnable {
    private final GameManager gameManager;

    public GameEndCountdownTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    private int Time = 10;

    @Override
    public void run() {
        if (gameManager.getGameState() != GameState.WON) {
            cancel();
            Time = 10;
            return;
        }
        Time--;
        if (Time <= 0) {
            cancel();
            gameManager.setGameState(GameState.LOBBY);
            GameManager.cleanup();
        }
    }
};
