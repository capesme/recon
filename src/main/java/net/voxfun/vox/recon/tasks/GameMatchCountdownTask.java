package net.voxfun.vox.recon.tasks;

import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.GameState;
import org.bukkit.scheduler.BukkitRunnable;

public class GameMatchCountdownTask extends BukkitRunnable {
    private final GameManager gameManager;

    public GameMatchCountdownTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public static int matchTime = 600;

    @Override
    public void run() {
        if (gameManager.getGameState() != GameState.ACTIVE) {
            cancel();
            matchTime = 600;
            return;
        }
        matchTime--;
        if (matchTime <= 0) {
            cancel();
            gameManager.setGameState(GameState.WON);
        }
    }
};
