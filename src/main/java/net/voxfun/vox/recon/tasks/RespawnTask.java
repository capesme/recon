package net.voxfun.vox.recon.tasks;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RespawnTask extends BukkitRunnable {
    private int timeLeft = 1;
    private Player player;
    private Location location;

    public RespawnTask(Location location, Player player) {
        this.location = location;
        this.player = player;
    }

    @Override
    public void run() {
        if (location == null) {
            cancel();
            return;
        }
        timeLeft--;
        if (timeLeft <= 0) {
            cancel();
            player.teleport(location);
        }
    }
}
