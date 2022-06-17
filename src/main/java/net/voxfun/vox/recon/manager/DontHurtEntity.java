package net.voxfun.vox.recon.manager;

import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class DontHurtEntity {
    public static List<EntityType> dontHurtMe = new ArrayList<>();

    public DontHurtEntity() {
        dontHurtMe.add(EntityType.PAINTING);
        dontHurtMe.add(EntityType.ITEM_FRAME);
        dontHurtMe.add(EntityType.GLOW_ITEM_FRAME);
        dontHurtMe.add(EntityType.LEASH_HITCH);
    }

    public static List get() { return dontHurtMe; }
}
