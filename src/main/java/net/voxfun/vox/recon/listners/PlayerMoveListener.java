package net.voxfun.vox.recon.listners;

import net.voxfun.vox.recon.index;
import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.GameState;
import net.voxfun.vox.recon.tasks.AimPraticeMinigame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static net.voxfun.vox.recon.manager.GameManager.aimPraticeMinigame;

public class PlayerMoveListener implements Listener {
    private GameManager gameManager;
    private final index plugin;
    public static Map<UUID, Boolean> userCache = new HashMap<>();
    public static boolean hasPlayed = false;
    public PlayerMoveListener(GameManager gameManager, index plugin) {
        this.gameManager = gameManager;
        this.plugin = plugin;
    }
    @EventHandler
    private void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location blockUnder = player.getLocation();
        Location blockUnder2 = player.getLocation();
        blockUnder.setY(blockUnder.getY() - 1);
        blockUnder2.setY(blockUnder2.getY() - 2);

        if (blockUnder.getBlock().getType().equals(Material.BLACKSTONE_SLAB)  || blockUnder2.getBlock().getType().equals(Material.BLACKSTONE_SLAB) && !(gameManager.getGameState().equals(GameState.ACTIVE))) {
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

                userCache.put(player.getUniqueId(), true);
                hasPlayed = true;
                if (gameManager.getGameState() == GameState.LOBBY || gameManager.getGameState() == GameState.WAITING); {
                    aimPraticeMinigame = new AimPraticeMinigame(gameManager);
                    aimPraticeMinigame.runTaskTimer(plugin, 0, 0);
                }
        } else if (gameManager.getGameState().equals(GameState.LOBBY) || gameManager.getGameState().equals(GameState.WAITING)) {
            for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                player.getPlayer().showPlayer(allPlayers);
            }

            if (player.getInventory().contains(Material.BOW) && !(gameManager.getGameState().equals(GameState.ACTIVE))) {
                player.getInventory().clear();
            }

            userCache.remove(player.getUniqueId());
            if (hasPlayed && userCache.size() == 0) {
                hasPlayed = false;
                aimPraticeMinigame.end();
                aimPraticeMinigame.cancel();
            }
            return;
        }
    }

}
