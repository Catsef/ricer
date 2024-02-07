package com.caltr.ricer.helpers;

import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.Objects;
import java.util.Random;

public class spawners {
    public static void generateFlyingLabel (Location location, String content, Plugin plugin) {

        Entity entity = Objects.requireNonNull(location.getWorld()).spawnEntity(location, EntityType.ARMOR_STAND);
        ArmorStand armorStand = (ArmorStand) entity;
        armorStand.setVisible(false);
        armorStand.setCustomName(content);
        armorStand.setCustomNameVisible(true);
        armorStand.setCollidable(false);
        armorStand.setSilent(false);
        armorStand.setGravity(false);
        location.getWorld().playSound(location, Sound.BLOCK_AMETHYST_BLOCK_PLACE, SoundCategory.PLAYERS, 5, 1);
        armorStand.setVelocity(armorStand.getVelocity().add(new Vector(0d, 1d, 0d)));
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, armorStand::remove, 10);

    }

    public static void punchComboSpawner(int howMany, Player player, Plugin plugin, double min, double max) {
        Random random = new Random();
        for (int i = 0; i < howMany; i++) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                double pDamage = Math.round(random.nextDouble(min, max));
                pDamage = utilities.absoluteDamage(player, pDamage, false);
                player.damage(pDamage);
                player.setNoDamageTicks(0);
                generateFlyingLabel(player.getLocation().add(random.nextDouble(0, 1), 0, random.nextDouble(0, 1)), ChatColor.GOLD + Double.toString(pDamage), plugin);
                Vector a = player.getVelocity().add(new Vector(0d, 0.3d, 0d));
                player.setVelocity(a);
            }, i * 2L);
        }
    }
}
