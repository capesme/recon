package net.voxfun.vox.recon.manager;

import net.voxfun.vox.recon.listners.NPC;

import java.util.ArrayList;
import java.util.List;

public class NPCManager {
    private final GameManager gameManager;
    private final List<NPC> NPCs;

    public NPCManager(GameManager gameManager) {
        this.NPCs = new ArrayList<>();
        this.gameManager = gameManager;
    }

    public List<NPC> getNPCs() {
        return NPCs;
    }
}
