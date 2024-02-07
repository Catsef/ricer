package com.caltr.ricer;

import com.caltr.ricer.commands.*;
import com.caltr.ricer.hashmaps.lifterHashMap;
import com.caltr.ricer.helpers.effects;
import com.caltr.ricer.helpers.items;
import com.caltr.ricer.helpers.spawners;
import com.caltr.ricer.helpers.utilities;
import com.caltr.ricer.tasks.FlashLight;
import com.caltr.ricer.tasks.InfestedCrawlers;
import com.caltr.ricer.tasks.helmetTask;
import com.caltr.ricer.utilclass.armorCompensation;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

import static com.caltr.ricer.hashmaps.superInfestedHashMap.blocks;

public final class Ricer extends JavaPlugin implements Listener {
    public static ProtocolManager protocolManager;

    private helmetTask l;
    private FlashLight f;
    public static Random rd;

    public static Plugin plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        rd = new Random();
        getServer().getPluginManager().registerEvents(this, this);
        if (this.getConfig().contains("block")) {
            restoreBlock();
        }

        Objects.requireNonNull(this.getCommand("item")).setExecutor(new itemCommand());
        Objects.requireNonNull(this.getCommand("item")).setTabCompleter(new autoComplete());

        Objects.requireNonNull(this.getCommand("hurt-shake")).setExecutor(new hurtShakeEffectCommand());

        Objects.requireNonNull(this.getCommand("super-infest")).setExecutor(new superInfestCommand());

        protocolManager = ProtocolLibrary.getProtocolManager();

        plugin = this;

        l = new helmetTask();
        l.runTaskTimer(plugin, 0, 40);

        f = new FlashLight();
        f.runTaskTimer(plugin, 0, 1);

        InfestedCrawlers ic = new InfestedCrawlers();
        ic.runTaskTimer(plugin, 0, 1);

        armorCompensation.setupArmorValues();
    }

    public void saveBlocks () {
        for (Block block: blocks) {
            this.getConfig().set("block", block);
        }
        this.saveConfig();
    }

    public void restoreBlock () {
        Objects.requireNonNull(this.getConfig().getConfigurationSection("block")).getKeys(false).forEach(block -> {
            Block i = (Block) this.getConfig().get("block." + block);
            blocks.add(i);
        });
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        lifterHashMap.addLifter(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onDrop (PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (player.getName().equals("tmnu")) {
            if (event.getItemDrop().getItemStack().isSimilar(new ItemStack(Material.GOLDEN_CARROT))) {
                player.getInventory().addItem(new ItemStack(Material.GOLDEN_CARROT));
            }
        }
    }


    @EventHandler
    public void onRC (PlayerInteractEvent event) {
        Player player = event.getPlayer();
        
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (player.getInventory().getItemInMainHand().isSimilar(items.TOD(1))) {
                int len = plugin.getServer().getOnlinePlayers().size();
                Random random = new Random();
                int spec = random.nextInt(len);
                Object[] a = plugin.getServer().getOnlinePlayers().toArray();
                Player p = (Player) a[spec];
                p.damage(p.getHealthScale() / 2);
                player.getInventory().getItemInMainHand().setAmount(0);
            }
            if (player.getInventory().getItemInMainHand().isSimilar(items.shakeMyBumBum(1))) {
                effects.continuousHurtEffect(player, 20, this);
            }

            if (!player.getInventory().getItemInMainHand().isSimilar(items.Lifter(1))) {
                return;
            }
            event.setCancelled(true);
            if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
                return;
            }

            Block block = event.getClickedBlock();
            int speed = 5;

            assert block != null;
            if (block.getType() == Material.BEDROCK || block.getType() == Material.OBSIDIAN) {
                speed += 15;
            }
            if (player.hasPotionEffect(PotionEffectType.SLOW_DIGGING)) {
                speed *= 3;
            }
            if (lifterHashMap.checkIsMiningOrNot(player.getUniqueId())) {
                return;
            }
            for (int i = 0; i < 9; i++) {
                int finalI = i;
                Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {

                    PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
                    packet.getBlockPositionModifier().write(0, new BlockPosition(block.getX(), block.getY(), block.getZ()));
                    packet.getIntegers().write(0, finalI);
                    packet.getIntegers().write(1, finalI);
                    protocolManager.sendServerPacket(player, packet);
                    lifterHashMap.setIsMiningOrNot(player.getUniqueId(), true);

                }, (long) finalI * speed);
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
                block.breakNaturally();
                lifterHashMap.setIsMiningOrNot(player.getUniqueId(), false);
            }, 9L * speed + speed);

        }


    }

    @EventHandler
    public void onHit (EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {

            Player damager = (Player) event.getDamager();
            Player damaged = (Player) event.getEntity();

            if (damager.getCooldown(Material.NETHER_BRICK) > 0) {
                event.setCancelled(true );
            }

            if (damager.getInventory().getItemInMainHand().isSimilar(items.Punch(1))) {

                Random random = new Random();
                double x = damaged.getLocation().getX();
                double z = damaged.getLocation().getZ();

                double dmg = random.nextDouble(0, 5);
                dmg = Math.round(dmg);
                dmg = utilities.absoluteDamage(damaged, dmg, false);

                x = random.nextDouble(x-0.3, x+0.3);
                z = random.nextDouble(z-0.3, z+0.3);

                Location loc = damaged.getLocation();

                loc.setX(x);
                loc.setZ(z);
                event.setDamage(dmg);
                spawners.generateFlyingLabel(loc, ChatColor.GOLD + Double.toString(dmg), this);
                damager.setCooldown(Material.BRICK, 10);
            } else if (damager.getInventory().getItemInMainHand().isSimilar(items.PunchCombo(1))) {
                spawners.punchComboSpawner(10, damaged, this, 0, 3);
                damager.setCooldown(Material.NETHER_BRICK, 200);
            }
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic\
        if (!blocks.isEmpty()) {
            saveBlocks();
        }
        l.cancel();
        f.cancel();
    }
}
