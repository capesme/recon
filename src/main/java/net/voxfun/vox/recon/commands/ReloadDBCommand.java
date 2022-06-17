package net.voxfun.vox.recon.commands;

import net.voxfun.vox.recon.index;
import net.voxfun.vox.recon.manager.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadDBCommand implements CommandExecutor {
    private GameManager gameManager;

    public ReloadDBCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        index.reloadDb();
        commandSender.sendMessage(ChatColor.GREEN + "Reloaded the database!");
        return true;
    }
}