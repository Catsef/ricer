package com.caltr.ricer.tasks;

import com.caltr.ricer.Ricer;
import com.caltr.ricer.helpers.items;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class FlashLight extends BukkitRunnable {


    @Override
    public void run() {
        Plugin plugin = Ricer.plugin;
        for (Player p : plugin.getServer().getOnlinePlayers()) {

            if (p.getInventory().getItemInMainHand().isSimilar(items.LuigiLight(1))) {
                Ricer.Flash(p);
            }


        }
    }
}
