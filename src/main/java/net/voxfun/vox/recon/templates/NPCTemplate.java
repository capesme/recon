package net.voxfun.vox.recon.templates;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;
import net.voxfun.vox.recon.index;
import net.voxfun.vox.recon.listners.NPC;
import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.NPCManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Timer;
import java.util.UUID;

public class NPCTemplate {

    private final GameManager gameManager;


    public NPCTemplate(GameManager gameManager, index plugin) {
        this.gameManager = gameManager;
    }

    private static int Timer = 20;

    public static ServerPlayer createNPC(Player Player, boolean showAllPlayers, Location Position) {

        Timer = 20;

        CraftPlayer craftPlayer = (CraftPlayer) Player;
        MinecraftServer Server = craftPlayer.getHandle().getServer();
        ServerLevel Level = craftPlayer.getHandle().getLevel();

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "");
        GameProfile playerProfile = craftPlayer.getHandle().getGameProfile();

        Property skinProperty = (Property) playerProfile.getProperties().get("textures").toArray()[0];
        String skinSignature = skinProperty.getSignature();
        String skinTexture = skinProperty.getValue();

        gameProfile.getProperties().put("textures", new Property("textures", skinTexture, skinSignature));

        ServerPlayer npc = new ServerPlayer(Server, Level, gameProfile, null);

        PlayerTeam hideNameTeam = new PlayerTeam(new Scoreboard(), "hideNameTeam");
        hideNameTeam.getPlayers().add("");
        hideNameTeam.setNameTagVisibility(Team.Visibility.NEVER);

        SynchedEntityData dataWatcher = npc.getEntityData();
        dataWatcher.set(new EntityDataAccessor<>(17, EntityDataSerializers.BYTE), (byte) 126);


        npc.setPos(Position.getX(), (Position.getY()), Position.getZ());

        if (showAllPlayers) {
            Bukkit.getOnlinePlayers().forEach(allPlayers -> {
                ServerGamePacketListenerImpl packetServer = ((CraftPlayer) allPlayers).getHandle().connection;
                packetServer.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, npc));
                packetServer.send(new ClientboundAddPlayerPacket(npc));

                packetServer.send(ClientboundSetPlayerTeamPacket.createRemovePacket(hideNameTeam));
                packetServer.send(ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(hideNameTeam, true));
                
                packetServer.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, npc));
                
                packetServer.send(new ClientboundSetEntityDataPacket(npc.getId(), npc.getEntityData(), true));
                packetServer.send(new ClientboundSetEntityDataPacket(npc.getId(), dataWatcher, true));
            });

        } else {
            ServerGamePacketListenerImpl packetServer = ((CraftPlayer) Player).getHandle().connection;
            packetServer.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, npc));
            packetServer.send(new ClientboundAddPlayerPacket(npc));

            packetServer.send(new ClientboundSetEntityDataPacket(npc.getId(), npc.getEntityData(), true));
            packetServer.send(new ClientboundSetEntityDataPacket(npc.getId(), dataWatcher, true));

            packetServer.send(ClientboundSetPlayerTeamPacket.createRemovePacket(hideNameTeam));
            packetServer.send(ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(hideNameTeam, true));

            while (Timer > 0) {
                Timer--;
            }
            if (Timer == 0) {
                packetServer.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, npc));
            }
        }

        NPC NPC = new NPC();
        NPC.setNpc(npc);
        NPC.setPlayer((Player).getUniqueId());
        GameManager.npcManager.getNPCs().add(NPC);

        return npc;

    }


    public static void deleteNPC(ServerPlayer npc, boolean deleteNPC) {
        if (deleteNPC) {
            Bukkit.getOnlinePlayers().forEach(allPlayers -> {
                ServerGamePacketListenerImpl packetServer = ((CraftPlayer) allPlayers).getHandle().connection;
                packetServer.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, npc));
                packetServer.send(new ClientboundRemoveEntitiesPacket(npc.getId()));
            });

            NPC NPC = new NPC();
            NPC.getNpc();
            NPC.getPlayer();

            GameManager.npcManager.getNPCs().remove(NPC);

        } else {

        }
    }

    public static void playDead(ServerPlayer npc, Location Location) {
        npc.setPose(Pose.SLEEPING);
        npc.setPos(Location.getX(), (Location.getY()  + 1), Location.getZ());
        npc.setYRot(Location.getYaw());
        npc.setXRot(Location.getPitch());

            Bukkit.getOnlinePlayers().forEach(allPlayers -> {
                ServerGamePacketListenerImpl packetServer = ((CraftPlayer) allPlayers).getHandle().connection;
                packetServer.send(new ClientboundSetEntityDataPacket(npc.getId(), npc.getEntityData(), true));
            });
    }
}
