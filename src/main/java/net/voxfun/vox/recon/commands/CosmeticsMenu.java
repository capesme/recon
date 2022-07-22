package net.voxfun.vox.recon.commands;

import net.voxfun.vox.recon.manager.CosmeticsMenuManager;
import net.voxfun.vox.recon.manager.GameManager;
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
        return true;
    }
}
