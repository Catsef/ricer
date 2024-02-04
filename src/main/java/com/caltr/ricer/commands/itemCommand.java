package com.caltr.ricer.commands;

import com.caltr.ricer.Ricer;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class itemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            ItemStack toSend;

            switch (strings[0]) {
                case "lifter": {
                    toSend = Ricer.Lifter(1);
                }
                case "luigi-flashlight": {
                    toSend = Ricer.LuigiLight(1);
                }
                case "punch-combo": {
                    toSend = Ricer.PunchCombo(1);
                }
                case "punch": {
                    toSend = Ricer.Punch(1);
                }
                case "totem-of-dying": {
                    toSend = Ricer.TOD(1);
                }
                case "cursed-helmet": {
                    toSend = Ricer.ZeusHelmet(1);
                }
                default: {
                    toSend = new ItemStack(Material.AIR);
                }

                player.getInventory().addItem(toSend);
            }

            return true;
        } else {
            return false;
        }
    }
}
