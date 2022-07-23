package net.voxfun.vox.recon.templates;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class MenuItemTemplate {

    public static ItemStack makeLeatherArmour(Material ItemType, ChatColor NameColour, String ItemName, Color ItemColour) {

        ItemStack Item = new ItemStack(ItemType);

        LeatherArmorMeta ItemMeta = (LeatherArmorMeta) Item.getItemMeta();
        ItemMeta.setDisplayName(NameColour + ItemName);
        ItemMeta.setUnbreakable(true);
        ItemMeta.setColor(ItemColour);

        ItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ItemMeta.addItemFlags(ItemFlag.HIDE_DYE);
        ItemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        Item.setItemMeta(ItemMeta);

        return Item;
    }

    public static ItemStack makeItem(Material ItemType, ChatColor NameColour, String ItemName) {

        ItemStack Item = new ItemStack(ItemType);

        ItemMeta ItemMeta = Item.getItemMeta();
        ItemMeta.setDisplayName(NameColour + ItemName);
        ItemMeta.setUnbreakable(true);
        ItemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier("NoArmourToughness", -40, AttributeModifier.Operation.ADD_NUMBER));

        ItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ItemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        Item.setItemMeta(ItemMeta);

        return Item;
    }

    public static ItemStack coinItem() {

        ItemStack Item = new ItemStack(Material.SUNFLOWER);
        ItemMeta ItemMeta = Item.getItemMeta();
        ItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ItemMeta.setDisplayName(ChatColor.YELLOW + "Coins");
        Item.setItemMeta(ItemMeta);

        Item.setItemMeta(ItemMeta);

        return Item;
    }

    public static ItemStack backButton() {

        ItemStack Item = new ItemStack(Material.BARRIER);
        ItemMeta ItemMeta = Item.getItemMeta();
        ItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ItemMeta.setDisplayName(ChatColor.RED + "Back");
        Item.setItemMeta(ItemMeta);

        Item.setItemMeta(ItemMeta);

        return Item;
    }

}
