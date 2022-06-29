package net.voxfun.vox.recon.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameCommandTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("game") && sender.hasPermission("recon.game.tabcomplete")) {
            if (args.length > 2) return new ArrayList<>();
            if (args.length == 2) {
                List<String> completions = new ArrayList<>();
                String[] completionsArray = { "30s", "45s", "60s", "120s", "1m", "2m", "3m", "5m" };
                StringUtil.copyPartialMatches(args[1], Arrays.asList(completionsArray), completions);
                return completions;
            } else {
                List<String> completions = new ArrayList<>();
                String[] completionsArray = { "start", "restart", "end", "forceStart", "forceEnd" };
                StringUtil.copyPartialMatches(args[0], Arrays.asList(completionsArray), completions);
                return completions;
            }
        }
        return null;
    }
}
