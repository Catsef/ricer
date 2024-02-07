package com.caltr.ricer.helpers;

import com.caltr.ricer.utilclass.armorCompensation;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class utilities {
    public static double absoluteDamage (Player player, double damage, boolean ignoreArmor) {

        Inventory inv = player.getInventory();
        ItemStack helmet = inv.getItem(103);
        ItemStack chest = inv.getItem(102);
        ItemStack leg = inv.getItem(101);
        ItemStack boot = inv.getItem(100);
        double newDamage = damage;

        for (Map.Entry<Material, Double> entry : armorCompensation.helmets.entrySet()) {
            Material key = entry.getKey();
            Double value = entry.getValue();
            assert helmet != null;
            if (newDamage < 0) {
                newDamage = 0;
                break;
            }
            if (helmet.getType().equals(key)) {
                newDamage -= value;
            }
        }
        for (Map.Entry<Material, Double> entry : armorCompensation.chestplates.entrySet()) {
            Material key = entry.getKey();
            Double value = entry.getValue();
            assert chest != null;
            if (newDamage < 0) {
                newDamage = 0;
                break;
            }
            if (chest.getType().equals(key)) {
                newDamage -= value;
            }
        }
        for (Map.Entry<Material, Double> entry : armorCompensation.leggings.entrySet()) {
            Material key = entry.getKey();
            Double value = entry.getValue();
            assert leg != null;
            if (newDamage < 0) {
                newDamage = 0;
                break;
            }
            if (leg.getType().equals(key)) {
                newDamage -= value;
            }
        }
        for (Map.Entry<Material, Double> entry : armorCompensation.boots.entrySet()) {
            Material key = entry.getKey();
            Double value = entry.getValue();
            assert boot != null;
            if (newDamage < 0) {
                newDamage = 0;
                break;
            }
            if (boot.getType().equals(key)) {
                newDamage -= value;
            }
        }


        return newDamage;
    }

    public static net.md_5.bungee.api.ChatColor asCol(String a) {
        return net.md_5.bungee.api.ChatColor.of(a);
    }
}
