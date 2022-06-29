package net.voxfun.vox.recon.commands;

import net.voxfun.vox.recon.manager.GameManager;
import net.voxfun.vox.recon.manager.GameState;
import net.voxfun.vox.recon.tasks.GameMatchCountdownTask;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Locale;

public class GameCommand implements CommandExecutor {
    private GameManager gameManager;

    public GameCommand(GameManager gameManager) { this.gameManager = gameManager; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "start":
                if (!sender.hasPermission("recon.game.start")){
                    sender.sendMessage(ChatColor.RED + "You do not have permissions to start a game!");
                    return true;
                }
                if (gameManager.getGameState() == GameState.STARTING) {
                    sender.sendMessage(ChatColor.RED + "Game is starting.");
                    return true;
                }
                if (gameManager.getGameState() == GameState.ACTIVE) {
                    sender.sendMessage(ChatColor.RED + "Game is already active.");
                    return true;
                }
                GameManager.cleanup();
                if (args[1] != null) {
                    if (args[1].toLowerCase().endsWith("m")) {
                        args[1] = String.valueOf(Integer.parseInt(args[1].replace("m", "")) * 60);
                        if (Integer.parseInt(args[1]) > 3599) {
                            sender.sendMessage(ChatColor.RED + "A game cannot be longer than an hour!");
                            return true;
                        } else {
                            GameMatchCountdownTask.matchTime = Integer.parseInt(args[1]);
                        }
                    } else if (args[1].toLowerCase().endsWith("s")) {
                        if (Integer.parseInt(args[1].replace("s", "")) > 3599) {
                            sender.sendMessage(ChatColor.RED + "A game cannot be longer than an hour!");
                            return true;
                        } else {
                            GameMatchCountdownTask.matchTime = Integer.parseInt(args[1].replace("s", ""));
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Invalid arguments, you must specify minutes or seconds.");
                    }
                }
                gameManager.setGameState(GameState.STARTING);
                break;
            case "restart":
                // do code
                break;
            case "end":
                if (sender.hasPermission("recon.game.end")) {
                    if (gameManager.getGameState() != GameState.ACTIVE && gameManager.getGameState() != GameState.STARTING) {
                        sender.sendMessage(ChatColor.RED + "There is no game to end.");
                        return true;
                    }
                    gameManager.setGameState(GameState.WON);
                }
                break;
            case "forcestart":
                if (sender.hasPermission("recon.game.forcestart")) {
                    GameManager.cleanup();
                    gameManager.setGameState(GameState.FORCED_START);
                }
                break;
            case "forceend":
                if (sender.hasPermission("recon.game.forceend")) {
                    gameManager.setGameState(GameState.FORCED_END);
                }
                break;
        }
        return true;
    }
}
