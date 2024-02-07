package com.caltr.ricer.helpers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class items {
    public static ItemStack Lifter(int amt) {
        ItemStack LIS = new ItemStack(Material.ENDER_EYE, amt);
        ItemMeta LIM = LIS.getItemMeta();
        LIM.setDisplayName(ChatColor.AQUA + "Lifter " + ChatColor.DARK_GRAY + ChatColor.ITALIC + "[A+]");
        List<String> a = new ArrayList<>();
        a.add(ChatColor.MAGIC + "x" + utilities.asCol("#78a2f0") + " The combined power of Titan Atlas's strength contained into a orb. " + ChatColor.MAGIC + "x");
        a.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "rices:lifter");
        LIM.setLore(a);
        LIS.setItemMeta(LIM);
        return LIS;
    }

    public static ItemStack ZeusHelmet(int amt) {
        ItemStack ZIS = new ItemStack(Material.IRON_HELMET, amt);
        ItemMeta ZIM = ZIS.getItemMeta();
        ZIM.setDisplayName(utilities.asCol("#b3f0f5") + "Enlightenment " + ChatColor.DARK_GRAY + "[C+]");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "Once owned by Prometheus, this helmet is cursed by the one and the only Zeus.");
        lore.add(ChatColor.AQUA + "Lightning will strike the player which wears this once every two seconds");
        lore.add(ChatColor.LIGHT_PURPLE + "Zeus' Lightning Curse II");
        lore.add(ChatColor.DARK_GRAY + "rices:lightning_helmet");
        ZIM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ZIM.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        ZIM.setUnbreakable(true);
        ZIM.setLore(lore);
        ZIS.setItemMeta(ZIM);
        return ZIS;
    }

    public static ItemStack TOD (int amt) {
        ItemStack a = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta b = a.getItemMeta();
        b.setDisplayName(ChatColor.WHITE + "totem of dying [prototype]");
        a.setItemMeta(b);
        return a;
    }

    public static ItemStack LuigiLight(int amt) {
        ItemStack a = new ItemStack(Material.SPYGLASS, amt);
        ItemMeta b = a.getItemMeta();
        b.setDisplayName(utilities.asCol("#00ff88") + "Luigi's Flashlight " + ChatColor.DARK_GRAY + "[B+]");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.AQUA + "Found on the floor after an exhausting journey, ");
        lore.add(ChatColor.AQUA + "This item will reveal ghosts.");
        lore.add(ChatColor.DARK_AQUA + "Invisibility Remover V");
        lore.add(ChatColor.DARK_AQUA + "Burn II");
        lore.add(ChatColor.DARK_AQUA + "Night Vision I");
        lore.add(ChatColor.DARK_AQUA + "Player Glower I");
        lore.add(ChatColor.DARK_GRAY + "rices:luigi_flashlight");
        b.setLore(lore);
        b.setUnbreakable(true);
        b.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
        b.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        b.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        a.setItemMeta(b);

        return a;
    }

    public static ItemStack Punch(int amt) {
        ItemStack a = new ItemStack(Material.BRICK);
        ItemMeta b = a.getItemMeta();
        b.setDisplayName(ChatColor.WHITE + "Punch [D+]");
        b.setUnbreakable(true);
        a.setItemMeta(b);
        return a;
    }

    public static ItemStack shakeMyBumBum(int amt) {
        ItemStack a = new ItemStack(Material.STICK);
        ItemMeta b = a.getItemMeta();
        b.setDisplayName(ChatColor.WHITE + "Magical Shaking Wand");
        a.setItemMeta(b);
        return a;
    }

    public static ItemStack PunchCombo(int amt) {
        ItemStack a = new ItemStack(Material.NETHER_BRICK);
        ItemMeta b = a.getItemMeta();
        b.setDisplayName(ChatColor.WHITE + "Combo Punch [B+]");
        b.setUnbreakable(true);
        a.setItemMeta(b);
        return a;
    }
}
