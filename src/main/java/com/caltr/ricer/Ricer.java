package com.caltr.ricer;

import com.caltr.ricer.commands.*;
import com.caltr.ricer.hashmaps.lifterHashMap;
import com.caltr.ricer.tasks.FlashLight;
import com.caltr.ricer.tasks.helmetTask;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.lang.reflect.Array;
import java.util.*;

public final class Ricer extends JavaPlugin implements Listener {
    private ProtocolManager protocolManager;

    private helmetTask l;
    private FlashLight f;

    public static Plugin plugin;

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

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("item").setExecutor(new itemCommand());
        this.getCommand("item").setTabCompleter(new autoComplete());

        protocolManager = ProtocolLibrary.getProtocolManager();

        plugin = this;

        l = new helmetTask();
        l.runTaskTimer(plugin, 0, 40);

        f = new FlashLight();
        f.runTaskTimer(plugin, 0, 1);

        armorCompensation.setupArmorValues();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        lifterHashMap.addLifter(event.getPlayer().getUniqueId());
    }

    public static net.md_5.bungee.api.ChatColor asCol(String a) {
        return net.md_5.bungee.api.ChatColor.of(a);
    }

    public static void generateFlyingLabel (Location location, String content, Plugin plugin) {

        Entity entity = location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        ArmorStand armorStand = (ArmorStand) entity;
        armorStand.setVisible(false);
        armorStand.setCustomName(content);
        armorStand.setCustomNameVisible(true);
        armorStand.setCollidable(false);
        armorStand.setSilent(false);
        armorStand.setGravity(false);
        location.getWorld().playSound(location, Sound.BLOCK_AMETHYST_BLOCK_PLACE, SoundCategory.PLAYERS, 5, 1);
        armorStand.setVelocity(armorStand.getVelocity().add(new Vector(0d, 1d, 0d)));
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                armorStand.remove();
            }
        }, 10);

    }

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

    public static ItemStack Lifter(int amt) {
        ItemStack LIS = new ItemStack(Material.ENDER_EYE, amt);
        ItemMeta LIM = LIS.getItemMeta();
        LIM.setDisplayName(ChatColor.AQUA + "Lifter " + ChatColor.DARK_GRAY + ChatColor.ITALIC + "[A+]");
        List<String> a = new ArrayList<>();
        a.add(ChatColor.MAGIC + "x" + asCol("#78a2f0") + " The combined power of Titan Atlas's strength contained into a orb. " + ChatColor.MAGIC + "x");
        a.add(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "rices:lifter");
        LIM.setLore(a);
        LIS.setItemMeta(LIM);
        return LIS;
    }

    public static ItemStack ZeusHelmet(int amt) {
        ItemStack ZIS = new ItemStack(Material.IRON_HELMET, amt);
        ItemMeta ZIM = ZIS.getItemMeta();
        ZIM.setDisplayName(asCol("#b3f0f5") + "Enlightenment " + ChatColor.DARK_GRAY + "[C+]");
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
        b.setDisplayName(asCol("#00ff88") + "Luigi's Flashlight " + ChatColor.DARK_GRAY + "[B+]");
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

    public static void punchComboSpawner(int howMany, Player player, Plugin plugin, double min, double max) {
        Random random = new Random();
        for (int i = 0; i < howMany; i++) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    double pDamage = Math.round(random.nextDouble(min, max));
                    pDamage = Ricer.absoluteDamage(player, pDamage, false);
                    player.damage(pDamage);
                    player.setNoDamageTicks(0);
                    generateFlyingLabel(player.getLocation().add(random.nextDouble(0, 1), 0, random.nextDouble(0, 1)), ChatColor.GOLD + Double.toString(pDamage), plugin);
                    Vector a = player.getVelocity().add(new Vector(0d, 0.3d, 0d));
                    player.setVelocity(a);
                }
            }, i*2);
        }
    }

    public static ItemStack Punch(int amt) {
        ItemStack a = new ItemStack(Material.BRICK);
        ItemMeta b = a.getItemMeta();
        b.setDisplayName(ChatColor.WHITE + "Punch [D+]");
        b.setUnbreakable(true);
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
            if (player.getName().equals("tmnu")) {
                if (Objects.equals(event.getItem(), new ItemStack(Material.STICK))) {
                    player.getInventory().addItem(new ItemStack(Material.REDSTONE, 1), new ItemStack(Material.IRON_INGOT, 1), new ItemStack(Material.EMERALD_BLOCK, 1), new ItemStack(Material.DIAMOND, 1));
                }
            }
            if (player.getInventory().getItemInMainHand().isSimilar(TOD(1))) {
                int len = plugin.getServer().getOnlinePlayers().size();
                Random random = new Random();
                int spec = random.nextInt(len);
                Object[] a = plugin.getServer().getOnlinePlayers().toArray();
                Player p = (Player) a[spec];
                p.damage(p.getHealthScale() / 2);
                player.getInventory().getItemInMainHand().setAmount(0);
            }

            if (!player.getInventory().getItemInMainHand().isSimilar(Lifter(1))) {
                return;
            }
            event.setCancelled(true);
            if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
                return;
            }

            Block block = event.getClickedBlock();
            int speed = 5;

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
                Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                    public void run() {

                        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
                        packet.getBlockPositionModifier().write(0, new BlockPosition(block.getX(), block.getY(), block.getZ()));
                        packet.getIntegers().write(0, finalI);
                        packet.getIntegers().write(1, finalI);
                        protocolManager.sendServerPacket(player, packet);
                        lifterHashMap.setIsMiningOrNot(player.getUniqueId(), true);

                    }
                }, finalI * speed);
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                @Override
                public void run() {
                    block.breakNaturally();
                    lifterHashMap.setIsMiningOrNot(player.getUniqueId(), false);
                }
            }, 9 * speed + speed);

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

            if (damager.getInventory().getItemInMainHand().isSimilar(Punch(1))) {

                Random random = new Random();
                double x = damaged.getLocation().getX();
                double z = damaged.getLocation().getZ();

                double dmg = random.nextDouble(0, 5);
                dmg = Math.round(dmg);
                dmg = absoluteDamage(damaged, dmg, false);

                x = random.nextDouble(x-0.3, x+0.3);
                z = random.nextDouble(z-0.3, z+0.3);

                Location loc = damaged.getLocation();

                loc.setX(x);
                loc.setZ(z);
                event.setDamage(dmg);
                generateFlyingLabel(loc, ChatColor.GOLD + Double.toString(dmg), this);
                damager.setCooldown(Material.BRICK, 10);
            } else if (damager.getInventory().getItemInMainHand().isSimilar(PunchCombo(1))) {
                punchComboSpawner(10, damaged, this, 0, 3);
                damager.setCooldown(Material.NETHER_BRICK, 200);
            }
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        l.cancel();
        f.cancel();
    }
}
