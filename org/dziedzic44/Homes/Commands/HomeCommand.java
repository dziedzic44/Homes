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
        } if (dataManager.getConfig().getString(((Player) sender).getUniqueId().toString()) != null) {
            addEffects(((Player) sender));
            BukkitTask task =  new BukkitRunnable() {
                int ticks = 0;
                int timeLeft = plugin.getConfig().getInt("warp.delay");
                Location playerLocation = ((Player) sender).getLocation();
                @Override
                public void run() {
                    if (((plugin.getConfig().getInt("warp.delay") * 20) - ticks) / 20 == timeLeft -1) {
                        ((Player) sender).sendTitle(ChatColor.GREEN + plugin.getConfig().getString("messages.warp.title"), plugin.getConfig().getString("messages.warp.subtitle") + timeLeft, 4, 12, 4);
                        timeLeft--;
                    } if (playerLocation.getBlockX() == ((Player) sender).getLocation().getBlockX() && playerLocation.getBlockY() == ((Player) sender).getLocation().getBlockY() && playerLocation.getBlockZ() == ((Player) sender).getLocation().getBlockZ() && ticks == plugin.getConfig().getInt("warp.delay") * 20) {
                        playSound(((Player) sender));
                        removeEffects(((Player) sender));
                        sender.sendMessage(ChatColor.GREEN + plugin.getConfig().getString("messages.warp.success"));
                        ((Player) sender).teleport(dataManager.getConfig().getLocation(((Player) sender).getUniqueId().toString()));
                        this.cancel();
                    } else if ((playerLocation.getBlockX() != ((Player) sender).getLocation().getBlockX() || playerLocation.getBlockY() != ((Player) sender).getLocation().getBlockY() && playerLocation.getBlockZ() != ((Player) sender).getLocation().getBlockZ()) && ticks != plugin.getConfig().getInt("warp.delay") * 20) {
                        ((Player) sender).resetTitle();
                        removeEffects(((Player) sender));
                        sender.sendMessage(ChatColor.YELLOW + plugin.getConfig().getString("messages.warp.cancel"));
                        this.cancel();
                    }
                    ticks++;
                }
            }.runTaskTimer(plugin, 0, 1);
        } else {
            sender.sendMessage(ChatColor.YELLOW + plugin.getConfig().getString("messages.warp.fail"));
        }
        return true;
    }

    public void playSound(Player selectedPlayer) {
        if (plugin.getConfig().getBoolean("warp.sound")) {
            selectedPlayer.playSound(selectedPlayer.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 1);
        }
    }

    public void addEffects(Player selectedPlayer) {
        if (plugin.getConfig().getBoolean("warp.effects")) {
            selectedPlayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Integer.MAX_VALUE, 0));
            selectedPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0));
        }
    }

    public void removeEffects(Player selectedPlayer) {
        if (plugin.getConfig().getBoolean("warp.effects")) {
            selectedPlayer.removePotionEffect(PotionEffectType.CONFUSION);
            selectedPlayer.removePotionEffect(PotionEffectType.BLINDNESS);
        }
    }
}