package net.voxfun.vox.recon.tasks;

import net.voxfun.vox.recon.manager.MapManager;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class RespawnOKTask extends BukkitRunnable {
    private int timeLeft = 1;
    private Player player;
    private Location location;

    public RespawnOKTask(Location location, Player player) {
        this.location = location;
        this.player = player;
    }

    @Override
    public void run() {
        Document selectedMap = MapManager.getMap();
        List<Document> mapSpawns = selectedMap.getList("spawns", Document.class);
        Random rand = new Random();
        Document xyz = mapSpawns.get(rand.nextInt(mapSpawns.size()));
        double x = xyz.getInteger("x");
        double y = xyz.getInteger("y");
        double z = xyz.getInteger("z");

        x = x + .5;
        z = z + .5;


        Location Spawn1 = new Location(player.getWorld(), x, y, z);
        World World = player.getWorld();

        if (World.getNearbyEntities(Spawn1, 1.5, 2 ,1.5).size() == 0) {
            timeLeft--;
                if (timeLeft <= 0) {
                cancel();
                player.teleport(Spawn1);
                }
            } else {
        }
    }
}