package net.voxfun.vox.recon.manager;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class allowedInteractionBlockList {
    public static List<Material> allowedBlockInteractions = new ArrayList<>();

    public allowedInteractionBlockList() {
        allowedBlockInteractions.add(Material.OAK_DOOR);
    }

    public static List get() { return allowedBlockInteractions; }
}
