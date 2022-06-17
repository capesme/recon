package net.voxfun.vox.recon.manager;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class BlockManager {
    private GameManager gameManager;
    private Set<Material> allowedToBreak = new HashSet<>();

    public BlockManager(GameManager gameManager) {
        this.gameManager = gameManager;
        allowedToBreak.add(Material.OAK_LEAVES);
    }

    public boolean canBreak(Block block, Player player) {
        if (gameManager.getGameState() == GameState.LOBBY) {
            if (player.getGameMode() == GameMode.CREATIVE) {
                return true;
            }
        }
        return allowedToBreak.contains(block.getType());
    }
}
