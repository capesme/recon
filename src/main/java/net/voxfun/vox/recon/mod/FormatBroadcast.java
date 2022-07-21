package net.voxfun.vox.recon.mod;

import org.bukkit.ChatColor;

public class FormatBroadcast {
    public static String format(String message) {
        return ChatColor.AQUA + "Recon" + ChatColor.GOLD + "" + ChatColor.BOLD + " >> " + ChatColor.WHITE + message;
    }

    public static String specFormat(String message) {
        return ChatColor.GRAY + "Recon" + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + " >> " + ChatColor.GRAY + message;
    }

    public static String inGameFormat(String message) {
        return ChatColor.AQUA + "Recon" + ChatColor.RED + "" + ChatColor.BOLD + " >> " + ChatColor.WHITE + message;
    }
}
