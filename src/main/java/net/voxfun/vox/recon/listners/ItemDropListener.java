package net.voxfun.vox.recon.listners;

import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.GameState;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;

public class ItemDropListener implements Listener {
    private GameManager gameManager;

    public ItemDropListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void DisallowedItems() {
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) { return; }
        Player player = event.getPlayer();
        GameMode gamemode = player.getGameMode();
        int slotZero = 0;
        int slotOne = 1;
        int slotSeven = 7;
        int slotEight = 8;
        if (player.getInventory().getHeldItemSlot() == slotZero) {
            event.setCancelled(true);
        }
        if (player.getInventory().getHeldItemSlot() == slotOne) {
            event.setCancelled(true);
        }
        if (player.getInventory().getHeldItemSlot() == slotSeven) {
            event.setCancelled(true);
        }
        if (player.getInventory().getHeldItemSlot() == slotEight) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupArrowEvent event) {
        if (gameManager.getGameState() == GameState.ACTIVE) {
            event.setCancelled(true);
        } else {
            event.setCancelled(false);
        }
    }

    @EventHandler
    public void onInventoryChange (InventoryClickEvent event) {
        if (gameManager.getGameState() == GameState.LOBBY && !(event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE))) {
            event.setCancelled(true);
        } else {
            event.setCancelled(false);
        }
    }
}
