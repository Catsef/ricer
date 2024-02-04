package com.caltr.ricer.tasks;

import com.caltr.ricer.Ricer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class helmetTask extends BukkitRunnable {

    @Override
    public void run() {
        Plugin plugin = Ricer.plugin;
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (p.getInventory().getItem(EquipmentSlot.HEAD) == null) {return;}
            if (!p.getInventory().getItem(EquipmentSlot.HEAD).isSimilar(Ricer.ZeusHelmet(1))) {
                return;
            } else {
                Location loc = p.getLocation();
                World world = p.getWorld();
                world.spawnEntity(loc, EntityType.LIGHTNING);
            }
        }
    }
}
