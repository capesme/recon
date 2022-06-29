package net.voxfun.vox.recon.tasks;

import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
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
            Bukkit.getOnlinePlayers().forEach(player -> player.setLevel(0));
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 0);
            });
            return;
        }
        //Bukkit.broadcastMessage(FormatBroadcast.format(timeLeft + " until game starts!"));
        Bukkit.getOnlinePlayers().forEach(player -> player.setLevel(timeLeft));
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
        });
    }
}
