package org.dziedzic44.Homes.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.List;

public class DefaultTabComplete implements TabCompleter {
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<>();
    }
}
