package net.voxfun.vox.recon.listners;

import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerMoveListener implements Listener {

    private GameManager gameManager;

    public PlayerMoveListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    private void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location blockUnder = player.getLocation();
        Location blockUnder2 = player.getLocation();
        blockUnder.setY(blockUnder.getY() - 1);
        blockUnder2.setY(blockUnder2.getY() - 2);

        if (blockUnder.getBlock().getType().equals(Material.BLACKSTONE_SLAB) || blockUnder2.getBlock().getType().equals(Material.BLACKSTONE_SLAB) && !(gameManager.getGameState().equals(GameState.ACTIVE))) {

            for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                player.getPlayer().hidePlayer(allPlayers);
            }


            if (!player.getInventory().contains(Material.BOW)) {
                player.getInventory().clear();

                ItemStack Bow = new ItemStack(Material.BOW);
                ItemMeta BowMeta = Bow.getItemMeta();
                BowMeta.setUnbreakable(true);

                Bow.setItemMeta(BowMeta);

                player.getInventory().setItem(0, Bow);
            }

            if (!player.getInventory().contains(Material.ARROW)) {
                ItemStack arrow = new ItemStack(Material.ARROW);

                player.getInventory().setItem(9, arrow);
            }


        } else {
            for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                player.getPlayer().showPlayer(allPlayers);
            }

            if (player.getInventory().contains(Material.BOW) && !(gameManager.getGameState().equals(GameState.ACTIVE))) {
                player.getInventory().clear();
            }
            return;
        }
    }

}
