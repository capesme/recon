package net.voxfun.vox.recon.commands;

import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.GameState;
import net.voxfun.vox.recon.manager.MapManager;
import net.voxfun.vox.recon.mod.FormatBroadcast;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoteCommand implements CommandExecutor {
    private final GameManager gameManager;
    public VoteCommand(GameManager manager) { this.gameManager = manager; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (gameManager.getGameState() != GameState.LOBBY) return true;
        Document votedMap = MapManager.voteMap(args[0], (Player) sender);
        if (votedMap == null) {
            if (MapManager.voted.contains((Player) sender)) {
                sender.sendMessage(ChatColor.RED + "You already voted!");
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid Map!");
            }
            return true;
        } else {
            sender.sendMessage(FormatBroadcast.format(String.format("Voted for %s.", args[0])));
            return true;
        }
    }
}
