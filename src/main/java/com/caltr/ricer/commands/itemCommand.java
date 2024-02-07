package com.caltr.ricer.commands;

import com.caltr.ricer.hashmaps.superInfestedHashMap;
import com.caltr.ricer.helpers.items;
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

            ItemStack toSend = new ItemStack(Material.AIR);

            switch (strings[0]) {
                case "lifter": {
                    toSend = items.Lifter(1);
                    break;
                }
                case "luigi-flashlight": {
                    toSend = items.LuigiLight(1);
                    break;
                }
                case "punch-combo": {
                    toSend = items.PunchCombo(1);
                    break;
                }
                case "punch": {
                    toSend = items.Punch(1);
                    break;
                }
                case "totem-of-dying": {
                    toSend = items.TOD(1);
                    break;
                }
                case "cursed-helmet": {
                    toSend = items.ZeusHelmet(1);
                    break;
                }
                case "shake.test.item0": {
                    toSend = items.shakeMyBumBum(1);
                    break;
                }
                case "demo-shaker": {
                    player.sendMessage(String.valueOf(superInfestedHashMap.blocks));
                }
            }
            player.getInventory().addItem(toSend);

            return true;
        } else {
            return false;
        }
    }
}
