package org.dziedzic44.Homes.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.dziedzic44.Homes.Main;
import org.bukkit.command.Command;
import org.dziedzic44.Homes.DataManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class SethomeCommand implements CommandExecutor {
    public Main plugin;
    public DataManager dataManager;

    public SethomeCommand(Main plugin, DataManager dataManager) {
        this.plugin = plugin;
        this.dataManager = dataManager;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        } if (dataManager.getConfig().getString(((Player) sender).getUniqueId().toString()) == null) {
            dataManager.getConfig().set(((Player) sender).getUniqueId().toString(), ((Player) sender).getLocation());
            sender.sendMessage(ChatColor.GREEN + plugin.getConfig().getString("messages.set.success"));
            dataManager.saveConfig();
        } else {
            sender.sendMessage(ChatColor.YELLOW + plugin.getConfig().getString("messages.set.fail"));
        }
        return true;
    }
}