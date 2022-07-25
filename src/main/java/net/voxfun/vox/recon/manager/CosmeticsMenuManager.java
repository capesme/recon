package net.voxfun.vox.recon.manager;

import net.voxfun.vox.recon.templates.ItemstackTemplate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.net.MalformedURLException;

import static org.bukkit.Material.*;

public class CosmeticsMenuManager implements Listener {
    private final GameManager gameManager;

    public CosmeticsMenuManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

//    @EventHandler
//    public void onClick(PlayerInteractEvent event) {
//        Action Action = event.getAction();
//        Material Block = event.getMaterial();
//        ItemStack Item = event.getItem();
//        if (Action == org.bukkit.event.block.Action.PHYSICAL || Item == null || Item.getType() == Material.AIR) return;
//        if (Action == org.bukkit.event.block.Action.RIGHT_CLICK_AIR || Action == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) {
//            if (Item.getType() == ENDER_CHEST && Item.getItemMeta().getDisplayName().equalsIgnoreCase("Cosmetic Menu")) {
//                CosmeticsMenu(event.getPlayer());
//            } else if (gameManager.getGameState().equals(GameState.LOBBY) && Block.equals(STONE_BUTTON)) {
//                CosmeticsMenu(event.getPlayer());
//            } else if (gameManager.getGameState().equals(GameState.WAITING) && Block.equals(STONE_BUTTON)) {
//                CosmeticsMenu(event.getPlayer());
//            }
//        }
//    }

    @EventHandler
    public static void selectedButton(InventoryClickEvent event) throws MalformedURLException {
        if (!(event.getClickedInventory().getSize() == 54) && !(event.getClickedInventory().getSize() == 27)) return;
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta()) return;

        if (event.getCurrentItem().getType() == Material.BARRIER && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "Back")) {
            String SelectedInventory = player.getOpenInventory().getTitle();
            if (SelectedInventory.equals(ChatColor.YELLOW + "Hats Menu") || SelectedInventory.equals(ChatColor.YELLOW + "Suits Menu") || SelectedInventory.equals(ChatColor.YELLOW + "Trails Menu") || SelectedInventory.equals(ChatColor.YELLOW + "Particles Menu") || SelectedInventory.equals(ChatColor.YELLOW + "Wins Menu") || SelectedInventory.equals(ChatColor.YELLOW + "Kills Menu") || SelectedInventory.equals(ChatColor.YELLOW + "Deaths Menu")) {
                CosmeticsMenu(player);
            } else if (SelectedInventory.equals(ChatColor.YELLOW + "Red Suit") || SelectedInventory.equals(ChatColor.YELLOW + "Orange Suit") || SelectedInventory.equals(ChatColor.YELLOW + "Yellow Suit") || SelectedInventory.equals(ChatColor.YELLOW + "Green Suit") || SelectedInventory.equals(ChatColor.YELLOW + "Blue Suit") || SelectedInventory.equals(ChatColor.YELLOW + "Purple Suit") || SelectedInventory.equals(ChatColor.YELLOW + "Magenta Suit")){
                SuitsMenu(player);
            }
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

        if (event.getCurrentItem().getType() == Material.LEATHER_HELMET && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Yellow Suit")) {
            YellowSuitMenu(player);
        }

        if (event.getCurrentItem().getType() == Material.LEATHER_HELMET && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Green Suit")) {
            GreenSuitMenu(player);
        }

        if (event.getCurrentItem().getType() == Material.LEATHER_HELMET && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Blue Suit")) {
            BlueSuitMenu(player);
        }

        if (event.getCurrentItem().getType() == Material.LEATHER_HELMET && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Purple Suit")) {
            PurpleSuitMenu(player);
        }

        if (event.getCurrentItem().getType() == Material.LEATHER_HELMET && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Magenta Suit")) {
            MagentaSuitMenu(player);
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
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.YELLOW + "Cosmetics menu");

        ItemStack Hats = ItemstackTemplate.makeItem(TURTLE_HELMET, ChatColor.YELLOW, "Hats");
        inventory.setItem(MenuEssentials.clacMenuPos(2,2), Hats);

        ItemStack Suits = ItemstackTemplate.makeItem(CHAINMAIL_LEGGINGS, ChatColor.YELLOW, "Suits");
        inventory.setItem(MenuEssentials.clacMenuPos(4,2), Suits);

        ItemStack Trails = ItemstackTemplate.makeItem(ARROW, ChatColor.YELLOW, "Trails");
        inventory.setItem(MenuEssentials.clacMenuPos(6,2), Trails);

        ItemStack Particles = ItemstackTemplate.makeItem(BREWING_STAND, ChatColor.YELLOW, "Particles");
        inventory.setItem(MenuEssentials.clacMenuPos(8,2), Particles);

        /**
         * Equipment Menu ^
         *
         * Game Events Menu v
         **/

        ItemStack Wins = ItemstackTemplate.makeItem(CAKE, ChatColor.YELLOW, "Wins");
        inventory.setItem(MenuEssentials.clacMenuPos(3,4), Wins);

        ItemStack Kills = ItemstackTemplate.makeItem(IRON_SWORD, ChatColor.YELLOW, "Kills");
        inventory.setItem(MenuEssentials.clacMenuPos(5,4), Kills);

        ItemStack Deaths = ItemstackTemplate.makeItem(SKELETON_SKULL, ChatColor.YELLOW, "Deaths");
        inventory.setItem(MenuEssentials.clacMenuPos(7,4), Deaths);

        // Essentials

        ItemStack Coins = ItemstackTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);

        player.openInventory(inventory);
    }

    public static void HatsMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.YELLOW + "Hats Menu");

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

        ItemStack BackButton = ItemstackTemplate.backButton();
        inventory.setItem(MenuEssentials.clacMenuPos(4,6), BackButton);

        ItemStack Coins = ItemstackTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);

        player.openInventory(inventory);
    }

    public static void SuitsMenu(Player player) throws MalformedURLException {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.YELLOW + "Suits Menu");

        ItemStack RedSuit = ItemstackTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Red Suit", Color.RED);
        inventory.setItem(MenuEssentials.clacMenuPos(2,2), RedSuit);

        ItemStack OrangeSuit = ItemstackTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Orange Suit", Color.ORANGE);
        inventory.setItem(MenuEssentials.clacMenuPos(3,2), OrangeSuit);

        ItemStack YellowSuit = ItemstackTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Yellow Suit", Color.YELLOW);
        inventory.setItem(MenuEssentials.clacMenuPos(4,2), YellowSuit);

        ItemStack GreenSuit = ItemstackTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Green Suit", Color.LIME);
        inventory.setItem(MenuEssentials.clacMenuPos(5,2), GreenSuit);

        ItemStack BlueSuit = ItemstackTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Blue Suit", Color.BLUE);
        inventory.setItem(MenuEssentials.clacMenuPos(6,2), BlueSuit);

        ItemStack PurpleSuit = ItemstackTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Purple Suit", Color.PURPLE);
        inventory.setItem(MenuEssentials.clacMenuPos(7,2), PurpleSuit);

        ItemStack MagentaSuit = ItemstackTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Magenta Suit", Color.FUCHSIA);
        inventory.setItem(MenuEssentials.clacMenuPos(8,2), MagentaSuit);

        ItemStack RainbowSuit = ItemstackTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Rainbow Suit", Color.RED);
        inventory.setItem(MenuEssentials.clacMenuPos(2,3), RainbowSuit);

        ItemStack CalicoCatSuit = ItemstackTemplate.makePlayerHead("https://textures.minecraft.net/texture/53aceac5b24739ab6d890c962ef3569492510b5c248659c5a95c3a43645b1707", ChatColor.GREEN, "Calico Cat Suit");
        inventory.setItem(MenuEssentials.clacMenuPos(3,3), CalicoCatSuit);

        // Essentials

        ItemStack BackButton = ItemstackTemplate.backButton();
        inventory.setItem(MenuEssentials.clacMenuPos(4,6), BackButton);

        ItemStack Coins = ItemstackTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);

        player.openInventory(inventory);
    }

    public static void RedSuitMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.YELLOW + "Red Suit");

        ItemStack RedHelmet = ItemstackTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Red Helmet", Color.RED);
        inventory.setItem(MenuEssentials.clacMenuPos(3,2), RedHelmet);

        ItemStack RedTunic = ItemstackTemplate.makeLeatherArmour(LEATHER_CHESTPLATE, ChatColor.GREEN, "Red Chestplate", Color.RED);
        inventory.setItem(MenuEssentials.clacMenuPos(4,2), RedTunic);

        ItemStack RedLeggins = ItemstackTemplate.makeLeatherArmour(LEATHER_LEGGINGS, ChatColor.GREEN, "Red Leggings", Color.RED);
        inventory.setItem(MenuEssentials.clacMenuPos(6,2), RedLeggins);

        ItemStack RedBoots = ItemstackTemplate.makeLeatherArmour(LEATHER_BOOTS, ChatColor.GREEN, "Red Boots", Color.RED);
        inventory.setItem(MenuEssentials.clacMenuPos(7,2), RedBoots);

        ItemStack ResetSuitButton = ItemstackTemplate.makeItem(LAVA_BUCKET, ChatColor.RED, "Reset Suit");
        inventory.setItem(MenuEssentials.clacMenuPos(4,4), ResetSuitButton);

        ItemStack EquipFullSuitButton = ItemstackTemplate.makeItem(LEATHER, ChatColor.YELLOW, "Equip Full Suit");
        inventory.setItem(MenuEssentials.clacMenuPos(6,4), EquipFullSuitButton);

        // Essentials

        ItemStack BackButton = ItemstackTemplate.backButton();
        inventory.setItem(MenuEssentials.clacMenuPos(4,6), BackButton);

        ItemStack Coins = ItemstackTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);

        player.openInventory(inventory);
    }

    public static void OrangeSuitMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.YELLOW + "Orange Suit");

        ItemStack OrangeHelmet = ItemstackTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Orange Helmet", Color.ORANGE);
        inventory.setItem(MenuEssentials.clacMenuPos(3,2), OrangeHelmet);

        ItemStack OrangeTunic = ItemstackTemplate.makeLeatherArmour(LEATHER_CHESTPLATE, ChatColor.GREEN, "Orange Chestplate", Color.ORANGE);
        inventory.setItem(MenuEssentials.clacMenuPos(4,2), OrangeTunic);

        ItemStack OrangeLeggins = ItemstackTemplate.makeLeatherArmour(LEATHER_LEGGINGS, ChatColor.GREEN, "Orange Leggings", Color.ORANGE);
        inventory.setItem(MenuEssentials.clacMenuPos(6,2), OrangeLeggins);

        ItemStack OrangeBoots = ItemstackTemplate.makeLeatherArmour(LEATHER_BOOTS, ChatColor.GREEN, "Orange Boots", Color.ORANGE);
        inventory.setItem(MenuEssentials.clacMenuPos(7,2), OrangeBoots);

        ItemStack ResetSuitButton = ItemstackTemplate.makeItem(LAVA_BUCKET, ChatColor.RED, "Reset Suit");
        inventory.setItem(MenuEssentials.clacMenuPos(4,4), ResetSuitButton);

        ItemStack EquipFullSuitButton = ItemstackTemplate.makeItem(LEATHER, ChatColor.YELLOW, "Equip Full Suit");
        inventory.setItem(MenuEssentials.clacMenuPos(6,4), EquipFullSuitButton);

        // Essentials

        ItemStack BackButton = ItemstackTemplate.backButton();
        inventory.setItem(MenuEssentials.clacMenuPos(4,6), BackButton);

        ItemStack Coins = ItemstackTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);

        player.openInventory(inventory);
    }

    public static void YellowSuitMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.YELLOW + "Yellow Suit");

        ItemStack YellowHelmet = ItemstackTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Yellow Helmet", Color.YELLOW);
        inventory.setItem(MenuEssentials.clacMenuPos(3,2), YellowHelmet);

        ItemStack YellowTunic = ItemstackTemplate.makeLeatherArmour(LEATHER_CHESTPLATE, ChatColor.GREEN, "Yellow Chestplate", Color.YELLOW);
        inventory.setItem(MenuEssentials.clacMenuPos(4,2), YellowTunic);

        ItemStack YellowLeggings = ItemstackTemplate.makeLeatherArmour(LEATHER_LEGGINGS, ChatColor.GREEN, "Yellow Leggings", Color.YELLOW);
        inventory.setItem(MenuEssentials.clacMenuPos(6,2), YellowLeggings);

        ItemStack YellowBoots = ItemstackTemplate.makeLeatherArmour(LEATHER_BOOTS, ChatColor.GREEN, "Yellow Boots", Color.YELLOW);
        inventory.setItem(MenuEssentials.clacMenuPos(7,2), YellowBoots);

        ItemStack ResetSuitButton = ItemstackTemplate.makeItem(LAVA_BUCKET, ChatColor.RED, "Reset Suit");
        inventory.setItem(MenuEssentials.clacMenuPos(4,4), ResetSuitButton);

        ItemStack EquipFullSuitButton = ItemstackTemplate.makeItem(LEATHER, ChatColor.YELLOW, "Equip Full Suit");
        inventory.setItem(MenuEssentials.clacMenuPos(6,4), EquipFullSuitButton);

        // Essentials

        ItemStack BackButton = ItemstackTemplate.backButton();
        inventory.setItem(MenuEssentials.clacMenuPos(4,6), BackButton);

        ItemStack Coins = ItemstackTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);

        player.openInventory(inventory);
    }
    public static void GreenSuitMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.YELLOW + "Green Suit");

        ItemStack GreenHelmet = ItemstackTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Green Helmet", Color.LIME);
        inventory.setItem(MenuEssentials.clacMenuPos(3,2), GreenHelmet);

        ItemStack GreenTunic = ItemstackTemplate.makeLeatherArmour(LEATHER_CHESTPLATE, ChatColor.GREEN, "Green Chestplate", Color.LIME);
        inventory.setItem(MenuEssentials.clacMenuPos(4,2), GreenTunic);

        ItemStack GreenLeggings = ItemstackTemplate.makeLeatherArmour(LEATHER_LEGGINGS, ChatColor.GREEN, "Green Leggings", Color.LIME);
        inventory.setItem(MenuEssentials.clacMenuPos(6,2), GreenLeggings);

        ItemStack GreenBoots = ItemstackTemplate.makeLeatherArmour(LEATHER_BOOTS, ChatColor.GREEN, "Green Boots", Color.LIME);
        inventory.setItem(MenuEssentials.clacMenuPos(7,2), GreenBoots);

        ItemStack ResetSuitButton = ItemstackTemplate.makeItem(LAVA_BUCKET, ChatColor.RED, "Reset Suit");
        inventory.setItem(MenuEssentials.clacMenuPos(4,4), ResetSuitButton);

        ItemStack EquipFullSuitButton = ItemstackTemplate.makeItem(LEATHER, ChatColor.YELLOW, "Equip Full Suit");
        inventory.setItem(MenuEssentials.clacMenuPos(6,4), EquipFullSuitButton);

        // Essentials

        ItemStack BackButton = ItemstackTemplate.backButton();
        inventory.setItem(MenuEssentials.clacMenuPos(4,6), BackButton);

        ItemStack Coins = ItemstackTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);

        player.openInventory(inventory);
    }

    public static void BlueSuitMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.YELLOW + "Blue Suit");

        ItemStack BlueHelmet = ItemstackTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Blue Helmet", Color.BLUE);
        inventory.setItem(MenuEssentials.clacMenuPos(3,2), BlueHelmet);

        ItemStack BlueTunic = ItemstackTemplate.makeLeatherArmour(LEATHER_CHESTPLATE, ChatColor.GREEN, "Blue Chestplate", Color.BLUE);
        inventory.setItem(MenuEssentials.clacMenuPos(4,2), BlueTunic);

        ItemStack BlueLeggings = ItemstackTemplate.makeLeatherArmour(LEATHER_LEGGINGS, ChatColor.GREEN, "Blue Leggings", Color.BLUE);
        inventory.setItem(MenuEssentials.clacMenuPos(6,2), BlueLeggings);

        ItemStack BlueBoots = ItemstackTemplate.makeLeatherArmour(LEATHER_BOOTS, ChatColor.GREEN, "Blue Boots", Color.BLUE);
        inventory.setItem(MenuEssentials.clacMenuPos(7,2), BlueBoots);

        ItemStack ResetSuitButton = ItemstackTemplate.makeItem(LAVA_BUCKET, ChatColor.RED, "Reset Suit");
        inventory.setItem(MenuEssentials.clacMenuPos(4,4), ResetSuitButton);

        ItemStack EquipFullSuitButton = ItemstackTemplate.makeItem(LEATHER, ChatColor.YELLOW, "Equip Full Suit");
        inventory.setItem(MenuEssentials.clacMenuPos(6,4), EquipFullSuitButton);

        // Essentials

        ItemStack BackButton = ItemstackTemplate.backButton();
        inventory.setItem(MenuEssentials.clacMenuPos(4,6), BackButton);

        ItemStack Coins = ItemstackTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);

        player.openInventory(inventory);
    }

    public static void PurpleSuitMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.YELLOW + "Purple Suit");

        ItemStack PurpleHelmet = ItemstackTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Purple Helmet", Color.PURPLE);
        inventory.setItem(MenuEssentials.clacMenuPos(3,2), PurpleHelmet);

        ItemStack PurpleTunic = ItemstackTemplate.makeLeatherArmour(LEATHER_CHESTPLATE, ChatColor.GREEN, "Purple Chestplate", Color.PURPLE);
        inventory.setItem(MenuEssentials.clacMenuPos(4,2), PurpleTunic);

        ItemStack PurpleLeggings = ItemstackTemplate.makeLeatherArmour(LEATHER_LEGGINGS, ChatColor.GREEN, "Purple Leggings", Color.PURPLE);
        inventory.setItem(MenuEssentials.clacMenuPos(6,2), PurpleLeggings);

        ItemStack PurpleBoots = ItemstackTemplate.makeLeatherArmour(LEATHER_BOOTS, ChatColor.GREEN, "Purple Boots", Color.PURPLE);
        inventory.setItem(MenuEssentials.clacMenuPos(7,2), PurpleBoots);

        ItemStack ResetSuitButton = ItemstackTemplate.makeItem(LAVA_BUCKET, ChatColor.RED, "Reset Suit");
        inventory.setItem(MenuEssentials.clacMenuPos(4,4), ResetSuitButton);

        ItemStack EquipFullSuitButton = ItemstackTemplate.makeItem(LEATHER, ChatColor.YELLOW, "Equip Full Suit");
        inventory.setItem(MenuEssentials.clacMenuPos(6,4), EquipFullSuitButton);

        // Essentials

        ItemStack BackButton = ItemstackTemplate.backButton();
        inventory.setItem(MenuEssentials.clacMenuPos(4,6), BackButton);

        ItemStack Coins = ItemstackTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);

        player.openInventory(inventory);
    }

    public static void MagentaSuitMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.YELLOW + "Magenta Suit");

        ItemStack MagentaHelmet = ItemstackTemplate.makeLeatherArmour(LEATHER_HELMET, ChatColor.GREEN, "Magenta Helmet", Color.FUCHSIA);
        inventory.setItem(MenuEssentials.clacMenuPos(3,2), MagentaHelmet);

        ItemStack MagentaTunic = ItemstackTemplate.makeLeatherArmour(LEATHER_CHESTPLATE, ChatColor.GREEN, "Magenta Chestplate", Color.FUCHSIA);
        inventory.setItem(MenuEssentials.clacMenuPos(4,2), MagentaTunic);

        ItemStack MagentaLeggings = ItemstackTemplate.makeLeatherArmour(LEATHER_LEGGINGS, ChatColor.GREEN, "Magenta Leggings", Color.FUCHSIA);
        inventory.setItem(MenuEssentials.clacMenuPos(6,2), MagentaLeggings);

        ItemStack MagentaBoots = ItemstackTemplate.makeLeatherArmour(LEATHER_BOOTS, ChatColor.GREEN, "Magenta Boots", Color.FUCHSIA);
        inventory.setItem(MenuEssentials.clacMenuPos(7,2), MagentaBoots);

        ItemStack ResetSuitButton = ItemstackTemplate.makeItem(LAVA_BUCKET, ChatColor.RED, "Reset Suit");
        inventory.setItem(MenuEssentials.clacMenuPos(4,4), ResetSuitButton);

        ItemStack EquipFullSuitButton = ItemstackTemplate.makeItem(LEATHER, ChatColor.YELLOW, "Equip Full Suit");
        inventory.setItem(MenuEssentials.clacMenuPos(6,4), EquipFullSuitButton);

        // Essentials

        ItemStack BackButton = ItemstackTemplate.backButton();
        inventory.setItem(MenuEssentials.clacMenuPos(4,6), BackButton);

        ItemStack Coins = ItemstackTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);

        player.openInventory(inventory);
    }


    public static void TrailsMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.YELLOW + "Trails Menu");

        ItemStack RedSuit = ItemstackTemplate.makeLeatherArmour(LEATHER_LEGGINGS, ChatColor.GREEN, "Red Suit", Color.RED);

        inventory.setItem(10, RedSuit);

        // Essentials

        ItemStack BackButton = ItemstackTemplate.backButton();
        inventory.setItem(MenuEssentials.clacMenuPos(4,6), BackButton);

        ItemStack Coins = ItemstackTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);

        player.openInventory(inventory);
    }

    public static void ParticlesMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.YELLOW + "Particles Menu");

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

        ItemStack BackButton = ItemstackTemplate.backButton();
        inventory.setItem(MenuEssentials.clacMenuPos(4,6), BackButton);

        ItemStack Coins = ItemstackTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);

        inventory.setItem(MenuEssentials.clacMenuPos(5, 6), Coins);


        player.openInventory(inventory);
    }

    public static void WinsMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.YELLOW + "Wins Menu");

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

        ItemStack BackButton = ItemstackTemplate.backButton();
        inventory.setItem(MenuEssentials.clacMenuPos(4,6), BackButton);

        ItemStack Coins = ItemstackTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);

        player.openInventory(inventory);
    }

    public static void KillsMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.YELLOW + "Wins Menu");

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

        ItemStack BackButton = ItemstackTemplate.backButton();
        inventory.setItem(MenuEssentials.clacMenuPos(4,6), BackButton);

        ItemStack Coins = ItemstackTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);


        player.openInventory(inventory);
    }

    public static void DeathsMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.YELLOW + "Deaths Menu");

        ItemStack DeathEffects = ItemstackTemplate.makeItem(TOTEM_OF_UNDYING, ChatColor.GREEN, "Death Effects");
        inventory.setItem(MenuEssentials.clacMenuPos(3,3), DeathEffects);

        ItemStack DeathMessages = ItemstackTemplate.makeItem(OAK_SIGN, ChatColor.GREEN, "Death Messages");
        inventory.setItem(MenuEssentials.clacMenuPos(5,3), DeathMessages);

        ItemStack DeathCries = ItemstackTemplate.makeItem(GHAST_TEAR, ChatColor.GREEN, "Death Cries");
        inventory.setItem(MenuEssentials.clacMenuPos(7,3), DeathCries);


        // Essentials

        ItemStack BackButton = ItemstackTemplate.backButton();
        inventory.setItem(MenuEssentials.clacMenuPos(4,6), BackButton);

        ItemStack Coins = ItemstackTemplate.coinItem();
        inventory.setItem(MenuEssentials.clacMenuPos(5,6), Coins);


        player.openInventory(inventory);
    }

}
