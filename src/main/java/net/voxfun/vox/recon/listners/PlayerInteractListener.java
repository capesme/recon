package net.voxfun.vox.recon.listners;

import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.allowedInteractionBlockList;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import static net.voxfun.vox.recon.manager.allowedInteractionBlockList.allowedBlockInteractions;

public class PlayerInteractListener implements Listener {
    private GameManager gameManager;

    public PlayerInteractListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Material block = event.getClickedBlock().getType();
        Player player = event.getPlayer();
            if (allowedInteractionBlockList.get().contains(block) || player.getGameMode().equals(GameMode.CREATIVE)) {
                event.setCancelled(false);

            } else {
                event.setCancelled(true);
            }
    }
}
