package com.caltr.ricer.tasks;

import com.caltr.ricer.Ricer;
import com.caltr.ricer.helpers.checkers;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class InfestedCrawlers extends BukkitRunnable {

    @Override
    public void run() {
       Plugin plugin = Ricer.plugin;
       for (Player p: plugin.getServer().getOnlinePlayers()) {
           if (checkers.isPlayerStandingOnUltraInfestedBlock(p)) {

           }
       }
    }
}
