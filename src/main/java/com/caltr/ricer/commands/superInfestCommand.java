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
        System.out.println("Wtf i just got sent a command");
        if (!(commandSender instanceof Player)) {
            System.out.println("Wtf they're not a player");
            commandSender.sendMessage("You're not a player?");
            return false;
        }
        System.out.println("They're a player, oh, thank goodness");
        System.out.println("Ok im gonna do whatever ricer tells me to do");
        Player player = (Player) commandSender;
        Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        superInfestedHashMap.blocks.add(block);
        commandSender.sendMessage("wtf i just super infested a block with millions of small creatures, btw here is a list of all of the super infested thingalings");
        commandSender.sendMessage(String.valueOf(superInfestedHashMap.blocks));
        commandSender.sendMessage("ok im done doing whatever now im gonna go back to the shadows");
        System.out.println(superInfestedHashMap.blocks);


        return true;
    }
}
