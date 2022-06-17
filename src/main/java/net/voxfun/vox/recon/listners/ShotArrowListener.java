package net.voxfun.vox.recon.listners;

import net.voxfun.vox.recon.manager.DontHurtEntity;
import net.voxfun.vox.recon.manager.DontInteractBlockArrow;
import net.voxfun.vox.recon.manager.GameManager;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ShotArrowListener implements Listener {
    private final GameManager gameManager;
    public ShotArrowListener(GameManager g) { this.gameManager = g; }

    @EventHandler
    public static void onShot(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow) e.getEntity();
            Entity shooter = (Entity) arrow.getShooter();
            if (e.getHitEntity() != null) {
                if (DontHurtEntity.get().contains(e.getHitEntity().getType())) { e.setCancelled(true); return; }
            }
            if (e.getHitBlock() != null) {
                if (DontInteractBlockArrow.get().contains(e.getHitBlock().getType())) { e.setCancelled(true); return; }
            }
            if (shooter instanceof Player) { arrow.setCustomName("ReconArrow"); }
        }
    }
}
