package com.caltr.ricer.commands;

import com.caltr.ricer.Ricer;
import com.caltr.ricer.hashmaps.superInfestedHashMap;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class superInfestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {return false;}

        Player player = (Player) commandSender;
        Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        superInfestedHashMap.blocks.add(block);


        return true;
    }
}
