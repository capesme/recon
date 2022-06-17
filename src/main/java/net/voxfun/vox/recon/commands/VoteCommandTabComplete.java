package net.voxfun.vox.recon.commands;

import net.voxfun.vox.recon.manager.MapManager;
import org.bson.Document;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VoteCommandTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("vote")) {
            if (args.length > 1) return new ArrayList<>();
            List<String> completions = new ArrayList<>();
            Map<String, Document> maps = MapManager.getMaps();
            maps.forEach((mapName, doc) -> completions.add(mapName));
            return completions;
        }
        return null;
    }
}
