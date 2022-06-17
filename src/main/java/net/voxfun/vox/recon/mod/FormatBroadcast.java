package net.voxfun.vox.recon.mod;

import org.bukkit.ChatColor;

public class FormatBroadcast {
    public static String format(String message) {
        return ChatColor.AQUA + "Recon" + ChatColor.GOLD + "" + ChatColor.BOLD + " >> " + ChatColor.WHITE + message;
    }
}
