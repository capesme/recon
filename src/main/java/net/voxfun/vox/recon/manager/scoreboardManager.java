package net.voxfun.vox.recon.manager;

import net.voxfun.vox.recon.tasks.PreGameStartCountdownTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class scoreboardManager {
    private static Scoreboard scoreboard = null;
    private static Objective objective = null;

    // In Game Scoreboard

    public static Scoreboard inGameCreate() {
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        if (scoreboard != null && scoreboard.getObjective("recon_stats") != null) { return scoreboard; }
        objective = scoreboard.registerNewObjective("recon_stats", "recon_stats", ChatColor.AQUA + "" + ChatColor.BOLD + "Recon " + ChatColor.GOLD + "" + ChatColor.BOLD + "MOST KILLS");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Bukkit.getOnlinePlayers().forEach(player -> {
            Score kills = objective.getScore(player.getName());
            kills.setScore(0);
        });
        return scoreboard;
    }

    public static void inGameAddPlayer(Player player) {
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

    //In Lobby Scoreboard

    public static Scoreboard inLobbyScoreboardCreate() {
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        if (scoreboard != null && scoreboard.getObjective("recon_lobby") != null) {
            return scoreboard;
        }

        objective = scoreboard.registerNewObjective("recon_lobby", "recon_lobby", ChatColor.GOLD + "" + ChatColor.BOLD + "Recon ");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        if (Bukkit.getOnlinePlayers().size() < MinimumPlayerAmount.get()) {
            Score s9 = objective.getScore("" );
            s9.setScore(9);
        }
        if (Bukkit.getOnlinePlayers().size() >= MinimumPlayerAmount.get()) {
            Score s10 = objective.getScore("");
            s10.setScore(10);
        }

        if (Bukkit.getOnlinePlayers().size() < MinimumPlayerAmount.get()) {
            Score s8 = objective.getScore("Players: " + ChatColor.GREEN + "" + Bukkit.getOnlinePlayers().size() + "/" + MinimumPlayerAmount.get() );
            s8.setScore(8);
        }
        if (Bukkit.getOnlinePlayers().size() >= MinimumPlayerAmount.get()) {
            Score s9 = objective.getScore("Players: " + ChatColor.GREEN + "" + Bukkit.getOnlinePlayers().size() + "/" + MinimumPlayerAmount.get());
            s9.setScore(9);
        }

        if (Bukkit.getOnlinePlayers().size() < MinimumPlayerAmount.get()) {
            Score s7 = objective.getScore("" );
            s7.setScore(7);
        }
        if (Bukkit.getOnlinePlayers().size() >= MinimumPlayerAmount.get()) {
            Score s8 = objective.getScore("" );
            s8.setScore(9);
        }

        if (Bukkit.getOnlinePlayers().size() < MinimumPlayerAmount.get()) {
            Score s7 = objective.getScore("Starting in " + ChatColor.GREEN + "00:" + (PreGameStartCountdownTask.preGameTime  - 1) + ChatColor.WHITE + " if" );
            s7.setScore(7);
        }
        if (Bukkit.getOnlinePlayers().size() >= MinimumPlayerAmount.get()) {
            Score s8 = objective.getScore("Starting in " + ChatColor.GREEN + "00:" + (PreGameStartCountdownTask.preGameTime  - 1) + ChatColor.WHITE + " to" );
            s8.setScore(8);
        }

        if (Bukkit.getOnlinePlayers().size() < MinimumPlayerAmount.get()) {
            Score s6 = objective.getScore( ChatColor.GREEN + "" + (MinimumPlayerAmount.get() - Bukkit.getOnlinePlayers().size()) + ChatColor.WHITE + " more players join." );
            s6.setScore(6);
        }
        if (Bukkit.getOnlinePlayers().size() >= MinimumPlayerAmount.get()) {
            Score s7 = objective.getScore( "allow time for" );
            s7.setScore(7);
        }

        if (Bukkit.getOnlinePlayers().size() < MinimumPlayerAmount.get()) {
            Score s5 = objective.getScore(ChatColor.WHITE + "" );
            s5.setScore(5);
        }
        if (Bukkit.getOnlinePlayers().size() >= MinimumPlayerAmount.get()) {
            Score s6 = objective.getScore( "additional players." );
            s6.setScore(6);
        }

        if (Bukkit.getOnlinePlayers().size() < MinimumPlayerAmount.get()) {
            Score s4 = objective.getScore("Game: " + ChatColor.GREEN + "Recon" );
            s4.setScore(4);
        }
        if (Bukkit.getOnlinePlayers().size() >= MinimumPlayerAmount.get()) {
            Score s5 = objective.getScore( "" );
            s5.setScore(5);
        }

        if (Bukkit.getOnlinePlayers().size() < MinimumPlayerAmount.get()) {
            Score s3 = objective.getScore("" );
            s3.setScore(3);
        }
        if (Bukkit.getOnlinePlayers().size() >= MinimumPlayerAmount.get()) {
            Score s4 = objective.getScore("Game: " + ChatColor.GREEN + "Recon" );
            s4.setScore(4);
        }

        if (Bukkit.getOnlinePlayers().size() < MinimumPlayerAmount.get()) {
            Score s2 = objective.getScore("" );
            s2.setScore(2);
        }
        if (Bukkit.getOnlinePlayers().size() >= MinimumPlayerAmount.get()) {
            Score s3 = objective.getScore("Game: " + ChatColor.GREEN + "Recon" );
            s3.setScore(3);
        }

        if (Bukkit.getOnlinePlayers().size() < MinimumPlayerAmount.get()) {
            Score s1 = objective.getScore(ChatColor.YELLOW + "voxfun.net" );
            s1.setScore(1);
        }
        if (Bukkit.getOnlinePlayers().size() >= MinimumPlayerAmount.get()) {
            Score s2 = objective.getScore("" );
            s2.setScore(2);
        }
        if (Bukkit.getOnlinePlayers().size() >= MinimumPlayerAmount.get()) {
            Score s1 = objective.getScore(ChatColor.YELLOW + "voxfun.net" );
            s1.setScore(1);
        }
        return scoreboard;
    }

    public static void inLobbyAddPlayer(Player player) {
        if (scoreboard == null || scoreboard.getObjective("recon_lobby") == null) return;
        player.setScoreboard(scoreboardManager.inLobbyScoreboardCreate());
    }


    //General Scoreboard Stuff

    public static void clear() {
        if (scoreboard == null || scoreboard.getObjective("recon_stats") == null || scoreboard.getObjective("recon_lobby") == null) {
            return;
        }
        scoreboard.getObjective("recon_stats").unregister();
        scoreboard.getObjective("recon_lobby").unregister();
    }
}