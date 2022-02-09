package org.dziedzic44.Homes.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.dziedzic44.Homes.Main;
import org.bukkit.command.Command;
import org.dziedzic44.Homes.DataManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class DelhomeCommand implements CommandExecutor {
    public Main plugin;
    public DataManager dataManager;

    public DelhomeCommand(Main plugin, DataManager dataManager) {
        this.plugin = plugin;
        this.dataManager = dataManager;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        } else if (dataManager.getConfig().getString(((Player) sender).getUniqueId().toString()) != null) {
            dataManager.getConfig().set(((Player) sender).getUniqueId().toString(), null);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("delhome.messages.success")));
            dataManager.saveConfig();
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("delhome.messages.fail")));
        }
        return true;
    }
}
