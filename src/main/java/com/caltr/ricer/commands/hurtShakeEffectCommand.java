package com.caltr.ricer.commands;

import com.caltr.ricer.Ricer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class hurtShakeEffectCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) {return false;}

        Player player = (Player) commandSender;

        Ricer.hurtEffect(player, Ricer.rd.nextFloat(3, 30));

        return true;
    }
}
