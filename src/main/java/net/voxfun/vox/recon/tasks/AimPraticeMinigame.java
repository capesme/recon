package net.voxfun.vox.recon.tasks;

import com.mongodb.client.MongoCollection;
import net.voxfun.vox.recon.index;
import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.GameState;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AimPraticeMinigame extends BukkitRunnable {
    private final GameManager gameManager;
    public static Map<String, Document> mapCache = new HashMap<>();

    public AimPraticeMinigame(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        if (gameManager.getGameState() == GameState.ACTIVE) {
            cancel();
        }

        World World = Bukkit.getWorld("OITC");
        int allZombies = World.getEntitiesByClass(Zombie.class).size();
        Document selectedMap;
        if (mapCache.get("ZombieSpawn") != null) {
            selectedMap = mapCache.get("ZombieSpawn");
        } else {
            MongoCollection<Document> mapCollection = index.database.getCollection("recon_maps");
            selectedMap = mapCollection.find(new Document("mapName", "ZombieSpawn")).first();
            mapCache.put("ZombieSpawn", selectedMap);
            System.out.println(mapCache.get("ZombieSpawn"));
        }

        List<Document> mapSpawns = selectedMap.getList("spawns", Document.class);
        Random rand = new Random();
        Document xyz = mapSpawns.get(rand.nextInt(mapSpawns.size()));

        double x = xyz.getInteger("x");
        double y = xyz.getInteger("y");
        double z = xyz.getInteger("z");

        // hardcoded to test if it works, you can update the database when you want.
        x = x + .5;
        y = y + .5;

        Location Spawn = new Location(World, x, y, z);

        if (allZombies < 4 && (World.getNearbyEntities(Spawn, 1, 2 ,1).size() == 0)) {
            World.spawnEntity(Spawn, EntityType.ZOMBIE);
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.getPlayer().getInventory().contains(Material.ARROW) && player.getPlayer().getInventory().contains(Material.BOW)) {
                ItemStack arrow = new ItemStack(Material.ARROW);
                player.getPlayer().getInventory().setItem(9, arrow);
            }
        }

        // This was causing errors
//        for (Arrow arrows : World.getEntitiesByClass(Arrow.class)) {
//            if (!(arrows.getAttachedBlock().getType() == Material.DRAGON_EGG)) {
//                arrows.remove();
//            }
//        }

        World.getEntitiesByClass(Zombie.class).forEach(zombie -> {
            zombie.setAI(false);
            zombie.setAdult();
            zombie.getEquipment().clear();
            zombie.setSilent(true);
            zombie.setVisualFire(false);

            Location lookHere = new Location(World,204, 40, -36);
            float yaw = lookHere.getYaw();
            zombie.getLocation().setYaw(yaw);
            zombie.teleport(zombie);
        });
    }

    public void end() {
        World World = Bukkit.getWorld("OITC");
        World.getEntitiesByClass(Zombie.class).forEach(zombie -> zombie.remove());
    }
}
