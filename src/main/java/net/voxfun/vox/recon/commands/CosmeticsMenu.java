package net.voxfun.vox.recon.commands;

import net.minecraft.server.level.ServerPlayer;
import net.voxfun.vox.recon.listners.NPC;
import net.voxfun.vox.recon.manager.CosmeticsMenuManager;
import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.NPCManager;
import net.voxfun.vox.recon.templates.NPCTemplate;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CosmeticsMenu implements CommandExecutor {
    private final GameManager gameManager;

    public CosmeticsMenu(GameManager gameManager) { this.gameManager = gameManager; }
    @Override
    public boolean onCommand(CommandSender Player, Command command, String label, String[] args) {
        CosmeticsMenuManager.CosmeticsMenu((Player) Player);

        Location PlayerLocation = ((Player) Player).getLocation();

        ServerPlayer npc = NPCTemplate.createNPC((org.bukkit.entity.Player) Player, true, PlayerLocation);
        NPCTemplate.playDead(npc, PlayerLocation);

        return true;
    }
}
