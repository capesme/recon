package net.voxfun.vox.recon.mod;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class IsCosmeticButton {
    public static Boolean check(Block block) {
        Location blockBehind = block.getLocation();
        blockBehind.setZ(blockBehind.getZ() - 1);
        return block.getType().equals(Material.STONE_BUTTON) && blockBehind.getBlock().getType().equals(Material.RED_TERRACOTTA);
    }
}
