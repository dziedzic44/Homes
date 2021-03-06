package org.dziedzic44.Homes;

import org.bukkit.plugin.java.JavaPlugin;
import org.dziedzic44.Homes.Commands.DefaultTabComplete;
import org.dziedzic44.Homes.Commands.DelhomeCommand;
import org.dziedzic44.Homes.Commands.HomeCommand;
import org.dziedzic44.Homes.Commands.SethomeCommand;

public class Main extends JavaPlugin {
    public void onEnable() {
        DataManager dataManager = new DataManager(this);
        getCommand("delhome").setTabCompleter(new DefaultTabComplete());
        getCommand("home").setTabCompleter(new DefaultTabComplete());
        getCommand("sethome").setTabCompleter(new DefaultTabComplete());
        getCommand("delhome").setExecutor(new DelhomeCommand(this, dataManager));
        getCommand("home").setExecutor(new HomeCommand(this, dataManager));
        getCommand("sethome").setExecutor(new SethomeCommand(this, dataManager));
        saveDefaultConfig();
    }
}
