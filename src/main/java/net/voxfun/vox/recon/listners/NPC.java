package net.voxfun.vox.recon.listners;

import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public class NPC {

    private ServerPlayer npc;
    private UUID Player;

    public NPC() {

    }

    public NPC(ServerPlayer npc, UUID player) {
        this.npc = npc;
        Player = player;
    }

    public ServerPlayer getNpc() {
        return npc;
    }

    public void setNpc(ServerPlayer npc) {
        this.npc = npc;
    }

    public UUID getPlayer() {
        return Player;
    }

    public void setPlayer(UUID player) {
        Player = player;
    }
}
