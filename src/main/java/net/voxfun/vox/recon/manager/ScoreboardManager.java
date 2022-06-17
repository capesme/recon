package net.voxfun.vox.recon.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager {
    private static Scoreboard scoreboard = null;
    private static Objective objective = null;

    public static Scoreboard create() {
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        if (scoreboard != null && scoreboard.getObjective("recon_stats") != null) {
            return scoreboard;
        }
        objective = scoreboard.registerNewObjective("recon_stats", "recon_stats", ChatColor.AQUA + "" + ChatColor.BOLD + "Recon " + ChatColor.GOLD + "" + ChatColor.BOLD + "MOST KILLS");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Bukkit.getOnlinePlayers().forEach(player -> {
            Score kills = objective.getScore(player.getName());
            kills.setScore(0);
        });
        return scoreboard;
    }
    public static void addPlayer(Player player) {
        if (scoreboard == null || scoreboard.getObjective("recon_stats") == null) return;
        player.setScoreboard(scoreboard);
    }
    public static Scoreboard setKills(Player player, Integer amount) {
        if (scoreboard == null || scoreboard.getObjective("recon_stats") == null) return null;
        Score stat = scoreboard.getObjective("recon_stats").getScore(player.getName());
        stat.setScore(amount);
        Bukkit.getOnlinePlayers().forEach(p -> p.setScoreboard(scoreboard));
        return scoreboard;
    }
    public static void clear() {
        if (scoreboard == null || scoreboard.getObjective("recon_stats") == null) { return; }
        scoreboard.getObjective("recon_stats").unregister();
    }
}
