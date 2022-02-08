package org.dziedzic44.Homes.Commands;

import org.bukkit.Sound;
import org.bukkit.Location;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.dziedzic44.Homes.Main;
import org.bukkit.command.Command;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitTask;
import org.dziedzic44.Homes.DataManager;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.command.CommandExecutor;
import org.bukkit.scheduler.BukkitRunnable;

public class HomeCommand implements CommandExecutor {
    public Main plugin;
    public DataManager dataManager;

    public HomeCommand(Main plugin, DataManager dataManager) {
        this.plugin = plugin;
        this.dataManager = dataManager;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        } else if (dataManager.getConfig().getString(((Player) sender).getUniqueId().toString()) != null) {
            if (plugin.getConfig().getBoolean("home.options.confusion")) ((Player) sender).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Integer.MAX_VALUE, 0));
            BukkitTask task = new BukkitRunnable() {
                int ticks = 0;
                int timeLeft = plugin.getConfig().getInt("home.options.delay");
                Location playerLocation = ((Player) sender).getLocation();
                public void run() {
                    if (((plugin.getConfig().getInt("home.options.delay") * 20) - ticks) / 20 == timeLeft -1) {
                        ((Player) sender).sendTitle(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("home.messages.title")), ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("home.messages.subtitle")) + timeLeft, 4, 12, 4);
                        timeLeft--;
                    } if (playerLocation.getBlockX() == ((Player) sender).getLocation().getBlockX() && playerLocation.getBlockY() == ((Player) sender).getLocation().getBlockY() && playerLocation.getBlockZ() == ((Player) sender).getLocation().getBlockZ() && ticks == plugin.getConfig().getInt("home.options.delay") * 20) {
                        ((Player) sender).teleport(dataManager.getConfig().getLocation(((Player) sender).getUniqueId().toString()));
                        for (PotionEffect effect : ((Player) sender).getActivePotionEffects()) ((Player) sender).removePotionEffect(effect.getType());
                        if (plugin.getConfig().getBoolean("home.options.sound")) ((Player) sender).playSound(((Player) sender).getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("home.messages.success")));
                        this.cancel();
                    } else if ((playerLocation.getBlockX() != ((Player) sender).getLocation().getBlockX() || playerLocation.getBlockY() != ((Player) sender).getLocation().getBlockY() || playerLocation.getBlockZ() != ((Player) sender).getLocation().getBlockZ()) && ticks != plugin.getConfig().getInt("home.options.delay") * 20) {
                        ((Player) sender).resetTitle();
                        for (PotionEffect effect : ((Player) sender).getActivePotionEffects()) ((Player) sender).removePotionEffect(effect.getType());
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("home.messages.cancel")));
                        this.cancel();
                    }
                    ticks++;
                }
            }.runTaskTimer(plugin, 0, 1);
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("home.messages.fail")));
        }
        return true;
    }
}
