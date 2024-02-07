package com.caltr.ricer.helpers;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import static com.caltr.ricer.hashmaps.superInfestedHashMap.blocks;

public class checkers {
    public static boolean isPlayerStandingOnUltraInfestedBlock(Player player) {
        Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        return blocks.contains(block);
    }
}
