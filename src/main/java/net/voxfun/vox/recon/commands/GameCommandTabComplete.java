package net.voxfun.vox.recon.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class GameCommandTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("game") && sender.hasPermission("recon.game.tabcomplete")) {
            if (args.length > 2) return new ArrayList<>();
            if (args.length > 1) {
                List<String> completions = new ArrayList<>();
                completions.add("60s");
                completions.add("1m");
                return completions;
            } else {
                List<String> completions = new ArrayList<>();
                completions.add("start");
                completions.add("restart");
                completions.add("end");
                completions.add("forceStart");
                completions.add("forceEnd");
                return completions;
            }
        }
        return null;
    }
}
