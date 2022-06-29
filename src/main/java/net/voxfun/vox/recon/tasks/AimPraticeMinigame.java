package net.voxfun.vox.recon.tasks;

import com.sun.org.apache.bcel.internal.generic.FieldOrMethod;
import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.GameState;
import net.voxfun.vox.recon.mod.FormatBroadcast;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

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

        World World = Bukkit.getWorld("OITC");

        int allZombies = World.getEntitiesByClass(Zombie.class).size();

        Location Spawn1 = new Location(World, 196.5, 40, -32.5);
        Location Spawn2 = new Location(World, 194.5, 42, -32.5);
        Location Spawn3 = new Location(World, 196.5, 40, -32.5);
        Location Spawn4 = new Location(World, 196.5, 40, -32.5);

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

       if (allZombies < 3 && (World.getNearbyEntities(Spawn1, 1, 2 ,1).size() == 0)) {
           World.spawnEntity(Spawn1, EntityType.ZOMBIE);

           World.getEntitiesByClass(Zombie.class).forEach(zombie -> {
               Location lookHere = new Location(World,204, 40, -36);
               float yaw = lookHere.getYaw();

               zombie.getLocation().setYaw(yaw);
               zombie.teleport(zombie);
           });
       }

        if (allZombies < 3 && (World.getNearbyEntities(Spawn2, 1, 2 ,1).size() == 0)) {
            World.spawnEntity(Spawn2, EntityType.ZOMBIE);

            World.getEntitiesByClass(Zombie.class).forEach(zombie -> {
                Location lookHere = new Location(World,204, 40, -36);

                zombie.getLocation().setDirection(lookHere.getDirection());
            });
        }
    }
}
