package net.voxfun.vox.recon.manager;

import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class MinimumPlayerAmount {
    public static Integer minPlayerAmount = new Integer(4);

    public MinimumPlayerAmount() {
        minPlayerAmount = 4;
    }

    public static Integer get() { return minPlayerAmount; }
}