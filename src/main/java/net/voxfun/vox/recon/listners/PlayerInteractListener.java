package net.voxfun.vox.recon.listners;

import net.voxfun.vox.recon.manager.CosmeticsMenuManager;
import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.GameState;
import net.voxfun.vox.recon.manager.allowedInteractionBlockList;
import net.voxfun.vox.recon.mod.IsCosmeticButton;
import org.bukkit.GameMode;
import org.bukkit.Location;
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
        Location blockBehind = event.getClickedBlock().getLocation();
        blockBehind.setZ(blockBehind.getZ() - 1);

        Player player = event.getPlayer();
        
        if (gameManager.getGameState().equals(GameState.LOBBY) || gameManager.getGameState().equals(GameState.WAITING)) {
            if (player.getGameMode().equals(GameMode.SPECTATOR)) event.setCancelled(false);
            if (IsCosmeticButton.check(event.getClickedBlock())) {
                CosmeticsMenuManager.CosmeticsMenu(player);
                event.setCancelled(true);
            }
        } else {
            if (allowedInteractionBlockList.get().contains(block)) {
                event.setCancelled(false);
            } else {
                event.setCancelled(!player.getGameMode().equals(GameMode.CREATIVE));
            }
        }
    }
}
