package com.caltr.ricer.hashmaps;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.UUID;

public class lifterHashMap {
    public static HashMap<UUID, Material> liftingBlock = new HashMap<>();
    public static HashMap<UUID, Boolean> Lifters = new HashMap<>();

    public static HashMap<UUID, Boolean> hasAlreadySpawned = new HashMap<>();
    public static HashMap<UUID, Boolean> isMining = new HashMap<>();


    public static HashMap<UUID, Material> getLiftingBlock() {
        return liftingBlock;
    }

    public static HashMap<UUID, Boolean> nowLifting (UUID uuid, Material block) {
        Lifters.put(uuid, true);
        liftingBlock.put(uuid, block);
        return Lifters;
    }

    public static HashMap<UUID, Boolean> notLiftingAnymore (UUID uuid, Material block) {
        Lifters.put(uuid, false);
        liftingBlock.remove(uuid);
        return Lifters;
    }

    public static void addLifter (UUID uuid) {
        Lifters.put(uuid, false);
        hasAlreadySpawned.put(uuid, true);
        isMining.put(uuid, false);
    }

    public static void setIsMiningOrNot (UUID uuid, boolean mining) {
        isMining.put(uuid, mining);
    }

    public static boolean checkIsMiningOrNot (UUID uuid) {
        return isMining.get(uuid);
    }

    public static void setIsSpawningOrNot (UUID uuid, boolean spawning) {
        hasAlreadySpawned.put(uuid, spawning);
    }
    public static boolean checkIfSpawningOrNot (UUID uuid) {
        return hasAlreadySpawned.get(uuid);
    }

    public static boolean isLifting (UUID uuid) {
        return Lifters.get(uuid);
    }

    public static Material liftingWhat (UUID uuid) {
        if (!isLifting(uuid)) {
            return Material.AIR;
        }

        return liftingBlock.get(uuid);
    }
}
