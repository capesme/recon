package net.voxfun.vox.recon.manager;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class allowedInteractionBlockList {
    public static List<Material> allowedBlockInteractions = new ArrayList<>();

    public allowedInteractionBlockList(List<String> list) {
        list.forEach(block -> allowedBlockInteractions.add(Material.valueOf(block)));
    }

    public static List get() { return allowedBlockInteractions; }
    public static void reload() { allowedBlockInteractions.clear(); }
}
