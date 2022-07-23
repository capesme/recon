package net.voxfun.vox.recon.listners;

import net.voxfun.vox.recon.commands.CosmeticsMenu;
import net.voxfun.vox.recon.manager.CosmeticsMenuManager;
import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.GameState;
import net.voxfun.vox.recon.manager.allowedInteractionBlockList;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {
    private GameManager gameManager;

    public PlayerInteractListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        Material block = event.getClickedBlock().getType();
        Player player = event.getPlayer();

        if (gameManager.getGameState().equals(GameState.WAITING) && block.equals(Material.STONE_BUTTON)) {
            CosmeticsMenuManager.CosmeticsMenu(player);
            event.setCancelled(true);
        } else if (gameManager.getGameState().equals(GameState.LOBBY) && block.equals(Material.STONE_BUTTON)) {
            CosmeticsMenuManager.CosmeticsMenu(player);
        } else {
            if (allowedInteractionBlockList.get().contains(block) || player.getGameMode().equals(GameMode.CREATIVE)) {
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }
        }
    }
}
