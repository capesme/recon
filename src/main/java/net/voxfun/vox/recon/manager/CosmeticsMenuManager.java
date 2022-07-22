package net.voxfun.vox.recon.manager;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
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

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Material.ENDER_CHEST;

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
        if (event.getClickedInventory().getSize() > 27) return;
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta())
            return;

        if (event.getCurrentItem().getType() == Material.LEATHER_HELMET && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "Equipment")) {
            EquipmentMenu(player);
        }
    }

    public static void CosmeticsMenu(Player player) {
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 27, ChatColor.YELLOW + "Cosmetics menu");

        List<String> EquipmentLore = new ArrayList<String>();
        EquipmentLore.add(ChatColor.WHITE + " ");
        EquipmentLore.add(ChatColor.WHITE + "> Equipment");
        EquipmentLore.add(ChatColor.WHITE + "> Trails");
        EquipmentLore.add(ChatColor.WHITE + "> Particles");

        List<String> GameEventsLore = new ArrayList<String>();
        GameEventsLore.add(ChatColor.WHITE + " ");
        GameEventsLore.add(ChatColor.WHITE + "> Wins");
        GameEventsLore.add(ChatColor.WHITE + "> Kills");
        GameEventsLore.add(ChatColor.WHITE + "> Deaths");

        //Equipment Button
        ItemStack Equipment = new ItemStack(Material.LEATHER_HELMET);
        ItemMeta EquipmentMeta = Equipment.getItemMeta();
        EquipmentMeta.setLore(EquipmentLore);
        EquipmentMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        EquipmentMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier("generic.armor", -40, AttributeModifier.Operation.ADD_NUMBER));
        EquipmentMeta.setDisplayName(ChatColor.YELLOW + "Equipment");
        Equipment.setItemMeta(EquipmentMeta);

        inventory.setItem(12, Equipment);

        //Game Events Button
        ItemStack GameEvents = new ItemStack(Material.GOLDEN_CARROT);
        ItemMeta GameEventsMeta = GameEvents.getItemMeta();
        GameEventsMeta.setLore(GameEventsLore);
        GameEventsMeta.setDisplayName(ChatColor.YELLOW + "Game Events");
        GameEvents.setItemMeta(GameEventsMeta);

        inventory.setItem(14, GameEvents);


        player.openInventory(inventory);
    }

    public static void EquipmentMenu(Player player) {
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 27, ChatColor.YELLOW + "Equipment menu");

        //Hats Button
        ItemStack Hats = new ItemStack(Material.TURTLE_HELMET);
        ItemMeta HatsMeta = Hats.getItemMeta();
        HatsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        HatsMeta.setDisplayName(ChatColor.YELLOW + "Hats");
        Hats.setItemMeta(HatsMeta);

        inventory.setItem(10, Hats);

        //Suits Button
        ItemStack Suits = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        ItemMeta SuitsMeta = Hats.getItemMeta();
        SuitsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        SuitsMeta.setDisplayName(ChatColor.YELLOW + "Suits");
        Suits.setItemMeta(SuitsMeta);

        inventory.setItem(12, Suits);

        //Trails Button
        ItemStack Trails = new ItemStack(Material.ARROW);
        ItemMeta TrailsMeta = Hats.getItemMeta();
        TrailsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        TrailsMeta.setDisplayName(ChatColor.YELLOW + "Trails");
        Trails.setItemMeta(SuitsMeta);

        inventory.setItem(14, Trails);

        //Particles Button
        ItemStack Particles = new ItemStack(Material.BREWING_STAND);
        ItemMeta ParticlesMeta = Hats.getItemMeta();
        ParticlesMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ParticlesMeta.setDisplayName(ChatColor.YELLOW + "Particles");
        Particles.setItemMeta(ParticlesMeta);

        inventory.setItem(16, Particles);

        player.openInventory(inventory);
    }


}
