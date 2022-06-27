package net.voxfun.vox.recon.tasks;

import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.GameState;
import net.voxfun.vox.recon.mod.FormatBroadcast;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Sound;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PreGameStartCountdownTask extends BukkitRunnable {
    private final GameManager gameManager;

    public PreGameStartCountdownTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    private int preGameTime = 61;
    private int playersAmount = 0;

    @Override
    public void run() {
        if (gameManager.getGameState() != GameState.WAITING) {
            cancel();
            playersAmount = Bukkit.getOnlinePlayers().size();
            return;
        }

        if (playersAmount >= 2 && preGameTime == 60 && gameManager.getGameState() == GameState.WAITING) {
            Bukkit.broadcastMessage(FormatBroadcast.format(preGameTime + " seconds until teleportation!"));
        }
            preGameTime--;
        if (playersAmount >= 2 && preGameTime == 0 && gameManager.getGameState() == GameState.WAITING) {
            Bukkit.broadcastMessage(FormatBroadcast.format("You're being teleported to the map!"));
            gameManager.setGameState(GameState.STARTING);
        }

        Bukkit.getOnlinePlayers().forEach(player -> player.setLevel(preGameTime));
    }
}
