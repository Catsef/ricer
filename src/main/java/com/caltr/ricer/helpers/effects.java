package com.caltr.ricer.helpers;

import com.caltr.ricer.Ricer;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class effects {
    public static void Flash (Player playerSpecified) {
        PotionEffect nb = new PotionEffect(PotionEffectType.NIGHT_VISION, 220, 0, false, false, true);
        playerSpecified.addPotionEffect(nb);

        for (Entity ent : playerSpecified.getNearbyEntities(16.0D, 16.0D, 16.0D)) {
            if (ent instanceof Player) {
                Player target = (Player) ent;
                if (playerSpecified.hasLineOfSight(ent)) {
                    if ((playerSpecified == target)) {
                        return;
                    }
                    PotionEffect glowing = new PotionEffect(PotionEffectType.GLOWING, 21, 1, false, false, false);
                    target.addPotionEffect(glowing);
                }
            }
        }
    }

    public static void hurtEffect(Player player, float yaw) {
        PacketContainer packet = Ricer.protocolManager.createPacket(PacketType.Play.Server.HURT_ANIMATION);
        packet.getIntegers().write(0, player.getEntityId());
        packet.getFloat().write(0, yaw);
        Ricer.protocolManager.sendServerPacket(player, packet);
    }
}
