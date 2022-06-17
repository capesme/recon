package net.voxfun.vox.recon.manager;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class DontInteractBlockArrow {
    public static List<Material> dontHurtMe = new ArrayList<>();

    public DontInteractBlockArrow() {
        dontHurtMe.add(Material.BIRCH_BUTTON);
        dontHurtMe.add(Material.ACACIA_BUTTON);
        dontHurtMe.add(Material.CRIMSON_BUTTON);
        dontHurtMe.add(Material.DARK_OAK_BUTTON);
        dontHurtMe.add(Material.JUNGLE_BUTTON);
        dontHurtMe.add(Material.OAK_BUTTON);
        dontHurtMe.add(Material.POLISHED_BLACKSTONE_BUTTON);
        dontHurtMe.add(Material.SPRUCE_BUTTON);
        dontHurtMe.add(Material.STONE_BUTTON);
        dontHurtMe.add(Material.WARPED_BUTTON);
        dontHurtMe.add(Material.LEGACY_STONE_BUTTON);
        dontHurtMe.add(Material.LEGACY_WOOD_BUTTON);
    }

    public static List get() { return dontHurtMe; }
}
