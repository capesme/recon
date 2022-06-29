package net.voxfun.vox.recon.listners;

import net.voxfun.vox.recon.manager.GameManager;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class HandSwapListener implements Listener {

    private GameManager gameManager;

    public HandSwapListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onSwap (PlayerSwapHandItemsEvent event) {
        if (!(event.getPlayer().getGameMode().equals(GameMode.CREATIVE))) {
            event.setCancelled(true);
        }
    }

}
