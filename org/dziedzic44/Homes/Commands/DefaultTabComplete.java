package org.dziedzic44.Homes.Commands;

import java.util.List;
import java.util.ArrayList;
import org.bukkit.command.Command;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.CommandSender;

public class DefaultTabComplete implements TabCompleter {
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}