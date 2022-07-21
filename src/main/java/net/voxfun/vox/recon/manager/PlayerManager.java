package net.voxfun.vox.recon.manager;

import net.voxfun.vox.recon.listners.DamagedListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class PlayerManager {
    private final GameManager gameManager;
    public static final HashMap<Player, GameMode> playerGameModes = new HashMap<>();
    public static final HashMap<Player, ItemStack[]> playerInventory = new HashMap<>();
    public static final HashMap<Player, String> currentGameId = new HashMap<>();

    public PlayerManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void setGameModes(GameMode mode) {
        Bukkit.getOnlinePlayers().forEach(player -> setGameMode(player, mode));
    }
    public void saveInventorys() { Bukkit.getOnlinePlayers().forEach(PlayerManager::saveInventory); }
    public void setPlayerGames(String id) { Bukkit.getOnlinePlayers().forEach(player -> setGame(player, id)); }

    public void giveKits() {
        Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.ADVENTURE).forEach(PlayerManager::giveKit);
    }
    public void clearKits(Boolean ended) {
        Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.ADVENTURE).forEach(PlayerManager::clearKit);
        if (ended) {
            Bukkit.getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.ADVENTURE).forEach(PlayerManager::clearKitEnd);
        }
    }

    public static void giveKit(Player player) {
        Integer deaths = DamagedListener.deaths.get(player);
        if (deaths == null) deaths = 0;
        player.getInventory().clear();

        //Give Stone Sword
        ItemStack StoneSword = new ItemStack(Material.STONE_SWORD);
        ItemMeta StoneSwordMeta = StoneSword.getItemMeta();
        StoneSwordMeta.setUnbreakable(true);
        StoneSword.setItemMeta(StoneSwordMeta);
        player.getInventory().setItem(0, StoneSword);

        //Give Bow
        ItemStack Bow = new ItemStack(Material.BOW);
        ItemMeta BowMeta = Bow.getItemMeta();
        BowMeta.setUnbreakable(true);
        Bow.setItemMeta(BowMeta);
        player.getInventory().setItem(1, Bow);

        //Give Player Arrow
        ItemStack Arrow = new ItemStack(Material.ARROW);
        player.getInventory().setItem(9, Arrow);

        ItemStack LivesCounter = new ItemStack(Material.RED_DYE);
        ItemMeta LivesCounterMeta = LivesCounter.getItemMeta();
        ChatColor ColorBasedOnLives = ChatColor.GREEN;
        if (5 - deaths < 4) { ColorBasedOnLives = ChatColor.YELLOW; }
        if (5 - deaths < 2) { ColorBasedOnLives = ChatColor.RED; }
        LivesCounterMeta.setDisplayName(ChatColor.RESET + "" + ColorBasedOnLives + "Lives");
        LivesCounter.setAmount(deaths > 0 ? 5 - deaths : 5);
        LivesCounter.setItemMeta(LivesCounterMeta);
        player.getInventory().setItem(8, LivesCounter);
    }
    public static void clearKit(Player player) { player.getInventory().clear(); }
    public static void clearKitEnd(Player player) {
        if (playerInventory.containsKey(player)) {
            player.getInventory().setContents(playerInventory.get(player));
        }
    }
    public static void setGameMode(Player player, GameMode mode) { player.setGameMode(mode); }
    public static GameMode getGameMode(Player player) {
        if (playerGameModes.get(player) == null) return GameMode.ADVENTURE;
        return playerGameModes.get(player);
    }
    public static void saveInventory(Player player) {
        playerInventory.put(player, player.getInventory().getContents());
    }
    public static void setGame(Player player, String id) {
        currentGameId.put(player, id);
    }
    public static String getGame(Player player) { return currentGameId.get(player); }
}