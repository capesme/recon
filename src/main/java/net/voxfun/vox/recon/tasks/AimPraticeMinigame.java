package net.voxfun.vox.recon.tasks;

import com.mongodb.client.MongoCollection;
import net.voxfun.vox.recon.index;
import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.GameState;
import net.voxfun.vox.recon.manager.MapManager;
import net.voxfun.vox.recon.mod.FormatBroadcast;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class AimPraticeMinigame extends BukkitRunnable {
    private final GameManager gameManager;

    public AimPraticeMinigame(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        if (gameManager.getGameState() == GameState.ACTIVE) {
            cancel();
        }

        // Rest of the Thingy lmao

        World World = Bukkit.getWorld("OITC");

        int allZombies = World.getEntitiesByClass(Zombie.class).size();

        //List<Document> mapSpawns = selectedMap.getList("spawns", Document.class);
        //Random rand = new Random();
        //Document xyz = mapSpawns.get(rand.nextInt(mapSpawns.size()));
        //Integer x = xyz.getInteger("x");
        //Integer y = xyz.getInteger("y");
        //Integer z = xyz.getInteger("z");
        //Location Spawn = new Location(World, x, y, z);

        for(Player player : Bukkit.getOnlinePlayers()) {
            if (!player.getPlayer().getInventory().contains(Material.ARROW) && player.getPlayer().getInventory().contains(Material.BOW)) {
                ItemStack arrow = new ItemStack(Material.ARROW);

                player.getPlayer().getInventory().setItem(9, arrow);
            }
        }

        for(Arrow arrows : World.getEntitiesByClass(Arrow.class)) {
            if (!(arrows.getAttachedBlock().getType() == Material.DRAGON_EGG)) {
                arrows.remove();
            }
        }

        World.getEntitiesByClass(Zombie.class).forEach(zombie -> {
            zombie.setAI(false);
            zombie.setAdult();
            zombie.getEquipment().clear();
            zombie.setSilent(true);
        });

        //if (allZombies < 3 && (World.getNearbyEntities(Spawn, 1, 2 ,1).size() == 0)) {
        //   World.spawnEntity(Spawn, EntityType.ZOMBIE);

        //   World.getEntitiesByClass(Zombie.class).forEach(zombie -> {
               //       Location lookHere = new Location(World,204, 40, -36);
               //       float yaw = lookHere.getYaw();

               //       zombie.getLocation().setYaw(yaw);
               //       zombie.teleport(zombie);
               //   });
        //}
    }
}
