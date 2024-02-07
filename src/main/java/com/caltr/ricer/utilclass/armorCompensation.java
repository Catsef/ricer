package com.caltr.ricer.utilclass;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class armorCompensation {

    public static ArrayList<HashMap<Material, Double>> ArmorCompensationValues = new ArrayList<>();

    public static HashMap<Material, Double> helmets = new HashMap<>();
    public static HashMap<Material, Double> chestplates = new HashMap<>();
    public static HashMap<Material, Double> leggings = new HashMap<>();
    public static HashMap<Material, Double> boots = new HashMap<>();


    public static void setupArmorValues() {

        helmets.put(Material.LEATHER_HELMET, 1d);
        helmets.put(Material.CHAINMAIL_HELMET, -1d);
        helmets.put(Material.IRON_HELMET, 3d);
        helmets.put(Material.GOLDEN_HELMET, 2d);
        helmets.put(Material.DIAMOND_HELMET, 10d);
        helmets.put(Material.NETHERITE_HELMET, 30d);

        chestplates.put(Material.LEATHER_CHESTPLATE, 2d);
        chestplates.put(Material.CHAINMAIL_CHESTPLATE, -1d);
        chestplates.put(Material.IRON_CHESTPLATE, 5d);
        chestplates.put(Material.GOLDEN_CHESTPLATE, 2d);
        chestplates.put(Material.DIAMOND_CHESTPLATE, 10d);
        chestplates.put(Material.NETHERITE_CHESTPLATE, 60d);

        leggings.put(Material.LEATHER_LEGGINGS, 0.5d);
        leggings.put(Material.CHAINMAIL_LEGGINGS, -1d);
        leggings.put(Material.IRON_LEGGINGS, 2d);
        leggings.put(Material.GOLDEN_LEGGINGS, 1d);
        leggings.put(Material.DIAMOND_LEGGINGS, 8d);
        leggings.put(Material.NETHERITE_LEGGINGS, 16d);

        boots.put(Material.LEATHER_BOOTS, 0d);
        boots.put(Material.CHAINMAIL_BOOTS, 0d);
        boots.put(Material.IRON_BOOTS, 1d);
        boots.put(Material.GOLDEN_BOOTS, 0d);
        boots.put(Material.DIAMOND_BOOTS, 3d);
        boots.put(Material.NETHERITE_BOOTS, 10d);

        ArmorCompensationValues.add(helmets);
        ArmorCompensationValues.add(chestplates);
        ArmorCompensationValues.add(leggings);
        ArmorCompensationValues.add(boots);

    }


}
