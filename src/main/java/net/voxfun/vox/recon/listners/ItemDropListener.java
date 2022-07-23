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

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) { return; }
        Player player = event.getPlayer();
        if (player.getInventory().getHeldItemSlot() == 0 || player.getInventory().getHeldItemSlot() == 1 || player.getInventory().getHeldItemSlot() == 7 || player.getInventory().getHeldItemSlot() == 8) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupArrowEvent event) {
        event.setCancelled(gameManager.getGameState() == GameState.ACTIVE);
    }

    @EventHandler
    public void onInventoryChange (InventoryClickEvent event) {
        event.setCancelled(gameManager.getGameState() == GameState.LOBBY && !(event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)));
    }
}
