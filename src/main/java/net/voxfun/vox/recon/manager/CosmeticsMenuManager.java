package net.voxfun.vox.recon.manager;

import net.voxfun.vox.recon.templates.MenuItemTemplate;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import static org.bukkit.Material.*;

public class CosmeticsMenuManager implements Listener {

    private final GameManager gameManager;

    public CosmeticsMenuManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public static void onClick(PlayerInteractEvent event) {
        Action a = event.getAction();
        ItemStack item = event.getItem();
        if (a == Action.PHYSICAL || item == null || item.getType() == Material.AIR) return;
        if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
            if (item.getType() == ENDER_CHEST && item.getItemMeta().getDisplayName().equalsIgnoreCase("Cosmetic Menu")) {
                CosmeticsMenu(event.getPlayer());
            }
        }
    }

    @EventHandler
    public static void selectedButton(InventoryClickEvent event) {
        if (!(event.getClickedInventory().getSize() == 54) && !(event.getClickedInventory().getSize() == 27)) return;
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta())
            return;

        if (event.getCurrentItem().getType() == Material.BARRIER && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "Back")) {
            String SelectedInventory = player.getOpenInventory().getTitle();
            if (SelectedInventory.equals(ChatColor.YELLOW + "Hats Menu") || SelectedInventory.equals(ChatColor.YELLOW + "Suits Menu") || SelectedInventory.equals(ChatColor.YELLOW + "Trails Menu") || SelectedInventory.equals(ChatColor.YELLOW + "Particles Menu") || SelectedInventory.equals(ChatColor.YELLOW + "Wins Menu") || SelectedInventory.equals(ChatColor.YELLOW + "Kills Menu") || SelectedInventory.equals(ChatColor.YELLOW + "Deaths Menu") ) {
                CosmeticsMenu(player);
            } else if (SelectedInventory.equals(ChatColor.YELLOW + "Red Suit") || SelectedInventory.equals(ChatColor.YELLOW + "Orange Suit"));
            SuitsMenu(player);
        }



        if (event.getCurrentItem().getType() == Material.TURTLE_HELMET && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "Hats")) {
            HatsMenu(player);
        }

        if (event.getCurrentItem().getType() == Material.CHAINMAIL_LEGGINGS && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "Suits")) {
            SuitsMenu(player);
        }

        if (event.getCurrentItem().getType() == Material.LEATHER_HELMET && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Red Suit")) {
            RedSuitMenu(player);
        }
        if (event.getCurrentItem().getType() == Material.LEATHER_HELMET && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Orange Suit")) {
            OrangeSuitMenu(player);
        }

        if (event.getCurrentItem().getType() == Material.ARROW && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "Trails")) {
            TrailsMenu(player);
        }

        if (event.getCurrentItem().getType() == Material.BREWING_STAND && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "Particles")) {
            ParticlesMenu(player);
        }

        if (event.getCurrentItem().getType() == Material.CAKE && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "Wins")) {
            WinsMenu(player);
        }

        if (event.getCurrentItem().getType() == Material.IRON_SWORD && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "Kills")) {
            KillsMenu(player);
        }

        if (event.getCurrentItem().getType() == Material.SKELETON_SKULL && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "Deaths")) {
            DeathsMenu(player);
        }

    }

    public static void CosmeticsMenu(Player player) {
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.YELLOW + "Cosmetics menu");

        ItemStack Hats = MenuItemTemplate.makeItem(TURTLE_HELMET, ChatColor.YELLOW, "Hats");
        inventory.setItem(MenuEssentials.clacMenuPos(2,2), Hats);

        ItemStack Suits = MenuItemTemplate.makeItem(CHAINMAIL_LEGGINGS, ChatColor.YELLOW, "Suits");
        inventory.setItem(MenuEssentials.clacMenuPos(4,2), Suits);

        ItemStack Trails = MenuItemTemplate.makeItem(ARROW, ChatColor.YELLOW, "Trails");
        inventory.setItem(MenuEssentials.clacMenuPos(6,2), Trails);

        ItemStack Particles = MenuItemTemplate.makeItem(BREWING_STAND, ChatColor.YELLOW, "Particles");
        inventory.setItem(MenuEssentials.clacMenuPos(8,2), Particles);

        /**
         * Equipment Menu ^
         *
         * Game Events Menu v
         **/

        ItemStack Wins = MenuItemTemplate.makeItem(CAKE, ChatColor.YELLOW, "Wins");
        inventory.setItem(MenuEssentials.clacMenuPos(3,4), Wins);

        ItemStack Kills = MenuItemTemplate.makeItem(IRON_SWORD, ChatColor.YELLOW, "Kills");
        inventory.setItem(MenuEssentials.clacMenuPos(5,4), Kills);

        ItemStack Deaths = MenuItemTemplate.makeItem(SKELETON_SKULL, ChatColor.YELLOW, "Deaths");
        inventory.setItem(MenuEssentials.clacMenuPos(7,4), Deaths);


        // Essentials

        ItemStack Coins = MenuItemTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);

        player.openInventory(inventory);
    }


    public static void HatsMenu(Player player) {
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.YELLOW + "Hats Menu");

        //RedSuit Button
        ItemStack RedSuit = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta  RedSuitMeta = (LeatherArmorMeta) RedSuit.getItemMeta();
        RedSuitMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        RedSuitMeta.addItemFlags(ItemFlag.HIDE_DYE);
        RedSuitMeta.setColor(Color.RED);
        RedSuitMeta.setDisplayName(ChatColor.GREEN + "Red Suit");
        RedSuit.setItemMeta(RedSuitMeta);

        inventory.setItem(10, RedSuit);

        // Essentials

        ItemStack BackButton = MenuItemTemplate.backButton();
        inventory.setItem(MenuEssentials.clacMenuPos(4,6), BackButton);

        ItemStack Coins = MenuItemTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);

        player.openInventory(inventory);
    }

    public static void SuitsMenu(Player player) {
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.YELLOW + "Suits Menu");

        ItemStack RedSuit = MenuItemTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Red Suit", Color.RED);
        inventory.setItem(MenuEssentials.clacMenuPos(2,2), RedSuit);

        ItemStack OrangeSuit = MenuItemTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Orange Suit", Color.ORANGE);
        inventory.setItem(MenuEssentials.clacMenuPos(3,2), OrangeSuit);

        ItemStack YellowSuit = MenuItemTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Yellow Suit", Color.YELLOW);
        inventory.setItem(MenuEssentials.clacMenuPos(4,2), YellowSuit);

        ItemStack GreenSuit = MenuItemTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Green Suit", Color.LIME);
        inventory.setItem(MenuEssentials.clacMenuPos(5,2), GreenSuit);

        ItemStack BlueSuit = MenuItemTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Blue Suit", Color.BLUE);
        inventory.setItem(MenuEssentials.clacMenuPos(6,2), BlueSuit);

        ItemStack PurpleSuit = MenuItemTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Purple Suit", Color.PURPLE);
        inventory.setItem(MenuEssentials.clacMenuPos(7,2), PurpleSuit);

        ItemStack MagentaSuit = MenuItemTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Magenta Suit", Color.FUCHSIA);
        inventory.setItem(MenuEssentials.clacMenuPos(8,2), MagentaSuit);

        // Essentials

        ItemStack BackButton = MenuItemTemplate.backButton();
        inventory.setItem(MenuEssentials.clacMenuPos(4,6), BackButton);

        ItemStack Coins = MenuItemTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);

        player.openInventory(inventory);
    }

    public static void RedSuitMenu(Player player) {
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.YELLOW + "Red Suit");

        ItemStack RedHelmet = MenuItemTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Red Helmet", Color.RED);
        inventory.setItem(MenuEssentials.clacMenuPos(3,2), RedHelmet);

        ItemStack RedTunic = MenuItemTemplate.makeLeatherArmour(LEATHER_CHESTPLATE, ChatColor.GREEN, "Red Chestplate", Color.RED);
        inventory.setItem(MenuEssentials.clacMenuPos(4,2), RedTunic);

        ItemStack RedLeggins = MenuItemTemplate.makeLeatherArmour(LEATHER_LEGGINGS, ChatColor.GREEN, "Red Leggings", Color.RED);
        inventory.setItem(MenuEssentials.clacMenuPos(6,2), RedLeggins);

        ItemStack RedBoots = MenuItemTemplate.makeLeatherArmour(LEATHER_BOOTS, ChatColor.GREEN, "Red Boots", Color.RED);
        inventory.setItem(MenuEssentials.clacMenuPos(7,2), RedBoots);

        // Essentials

        ItemStack BackButton = MenuItemTemplate.backButton();
        inventory.setItem(MenuEssentials.clacMenuPos(4,6), BackButton);

        ItemStack Coins = MenuItemTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);

        player.openInventory(inventory);
    }

    public static void OrangeSuitMenu(Player player) {
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.YELLOW + "Orange Suit");

        ItemStack OrangeHelmet = MenuItemTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Red Helmet", Color.ORANGE);
        inventory.setItem(MenuEssentials.clacMenuPos(3,2), OrangeHelmet);

        ItemStack OrangeTunic = MenuItemTemplate.makeLeatherArmour(LEATHER_CHESTPLATE, ChatColor.GREEN, "Red Chestplate", Color.ORANGE);
        inventory.setItem(MenuEssentials.clacMenuPos(4,2), OrangeTunic);

        ItemStack OrangeLeggins = MenuItemTemplate.makeLeatherArmour(LEATHER_LEGGINGS, ChatColor.GREEN, "Red Leggings", Color.ORANGE);
        inventory.setItem(MenuEssentials.clacMenuPos(6,2), OrangeLeggins);

        ItemStack OrangeBoots = MenuItemTemplate.makeLeatherArmour(LEATHER_BOOTS, ChatColor.GREEN, "Red Boots", Color.ORANGE);
        inventory.setItem(MenuEssentials.clacMenuPos(7,2), OrangeBoots);

        // Essentials

        ItemStack BackButton = MenuItemTemplate.backButton();
        inventory.setItem(MenuEssentials.clacMenuPos(4,6), BackButton);

        ItemStack Coins = MenuItemTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);

        player.openInventory(inventory);
    }

    public static void TrailsMenu(Player player) {
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.YELLOW + "Trails Menu");

        ItemStack RedSuit = MenuItemTemplate.makeLeatherArmour(LEATHER_LEGGINGS, ChatColor.GREEN, "Red Suit", Color.RED);

        inventory.setItem(10, RedSuit);

        // Essentials

        ItemStack BackButton = MenuItemTemplate.backButton();
        inventory.setItem(MenuEssentials.clacMenuPos(4,6), BackButton);

        ItemStack Coins = MenuItemTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);

        player.openInventory(inventory);
    }

    public static void ParticlesMenu(Player player) {
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.YELLOW + "Particles Menu");

        //RedSuit Button
        ItemStack RedSuit = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta  RedSuitMeta = (LeatherArmorMeta) RedSuit.getItemMeta();
        RedSuitMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        RedSuitMeta.addItemFlags(ItemFlag.HIDE_DYE);
        RedSuitMeta.setColor(Color.RED);
        RedSuitMeta.setDisplayName(ChatColor.GREEN + "Red Suit");
        RedSuit.setItemMeta(RedSuitMeta);

        inventory.setItem(10, RedSuit);

        // Essentials

        ItemStack BackButton = MenuItemTemplate.backButton();
        inventory.setItem(MenuEssentials.clacMenuPos(4,6), BackButton);

        ItemStack Coins = MenuItemTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);

        inventory.setItem(MenuEssentials.clacMenuPos(5, 6), Coins);


        player.openInventory(inventory);
    }

    public static void WinsMenu(Player player) {
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.YELLOW + "Wins Menu");

        //RedSuit Button
        ItemStack RedSuit = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta  RedSuitMeta = (LeatherArmorMeta) RedSuit.getItemMeta();
        RedSuitMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        RedSuitMeta.addItemFlags(ItemFlag.HIDE_DYE);
        RedSuitMeta.setColor(Color.RED);
        RedSuitMeta.setDisplayName(ChatColor.GREEN + "Red Suit");
        RedSuit.setItemMeta(RedSuitMeta);

        inventory.setItem(10, RedSuit);

        // Essentials

        ItemStack BackButton = MenuItemTemplate.backButton();
        inventory.setItem(MenuEssentials.clacMenuPos(4,6), BackButton);

        ItemStack Coins = MenuItemTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);

        player.openInventory(inventory);
    }

    public static void KillsMenu(Player player) {
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.YELLOW + "Wins Menu");

        //RedSuit Button
        ItemStack RedSuit = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta  RedSuitMeta = (LeatherArmorMeta) RedSuit.getItemMeta();
        RedSuitMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        RedSuitMeta.addItemFlags(ItemFlag.HIDE_DYE);
        RedSuitMeta.setColor(Color.RED);
        RedSuitMeta.setDisplayName(ChatColor.GREEN + "Red Suit");
        RedSuit.setItemMeta(RedSuitMeta);

        inventory.setItem(10, RedSuit);

        // Essentials

        ItemStack BackButton = MenuItemTemplate.backButton();
        inventory.setItem(MenuEssentials.clacMenuPos(4,6), BackButton);

        ItemStack Coins = MenuItemTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);


        player.openInventory(inventory);
    }

    public static void DeathsMenu(Player player) {
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 54, ChatColor.YELLOW + "Deaths Menu");

        ItemStack DeathEffects = MenuItemTemplate.makeItem(TOTEM_OF_UNDYING, ChatColor.GREEN, "Death Effects");
        inventory.setItem(MenuEssentials.clacMenuPos(3,3), DeathEffects);

        ItemStack DeathMessages = MenuItemTemplate.makeItem(OAK_SIGN, ChatColor.GREEN, "Death Messages");
        inventory.setItem(MenuEssentials.clacMenuPos(5,3), DeathMessages);

        ItemStack DeathCries = MenuItemTemplate.makeItem(GHAST_TEAR, ChatColor.GREEN, "Death Cries");
        inventory.setItem(MenuEssentials.clacMenuPos(7,3), DeathCries);


        // Essentials

        ItemStack BackButton = MenuItemTemplate.backButton();
        inventory.setItem(MenuEssentials.clacMenuPos(4,6), BackButton);

        ItemStack Coins = MenuItemTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);


        player.openInventory(inventory);
    }

}
