package net.voxfun.vox.recon.tasks;

import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.GameState;
import net.voxfun.vox.recon.manager.MinimumPlayerAmount;
import net.voxfun.vox.recon.manager.scoreboardManager;
import net.voxfun.vox.recon.mod.FormatBroadcast;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class PreGameStartCountdownTask extends BukkitRunnable {
    private final GameManager gameManager;

    public PreGameStartCountdownTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public static int preGameTime = 61;
    private int playersAmount = 0;

    @Override
    public void run() {
        playersAmount = Bukkit.getOnlinePlayers().size();

        if (gameManager.getGameState() != GameState.WAITING && playersAmount >= MinimumPlayerAmount.get()) {
            cancel();
            return;
        }

        if (playersAmount >= MinimumPlayerAmount.get() && preGameTime == 60 && gameManager.getGameState() == GameState.WAITING) {
            Bukkit.broadcastMessage(FormatBroadcast.format(preGameTime + " seconds until teleportation!"));
        }

        if (preGameTime <= MinimumPlayerAmount.get()) {
            gameManager.setGameState(GameState.STARTING);
            Bukkit.broadcastMessage(FormatBroadcast.format("You're being teleported!"));
        }

        if (preGameTime > 1) {
            scoreboardManager.updateLobby();
        } else {
            scoreboardManager.clear();
        }

            preGameTime--;
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.setLevel(preGameTime);
        });
    }

    public static Integer get() {
        return preGameTime;
    }

}
