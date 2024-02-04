package com.caltr.ricer.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class autoComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> autocompleteOptions = new ArrayList<>();
        if (strings.length == 1) {
            autocompleteOptions.add("lifter");
            autocompleteOptions.add("luigi-flashlight");
            autocompleteOptions.add("punch-combo");
            autocompleteOptions.add("punch");
            autocompleteOptions.add("totem-of-dying");
            autocompleteOptions.add("cursed-helmet");
            return autocompleteOptions;
        }

        return autocompleteOptions;
    }
}
