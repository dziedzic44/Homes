package org.dziedzic44.Homes;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class DataManager {
    private Main plugin;
    private File configFile;
    private FileConfiguration dataConfig;

    public DataManager(Main plugin) {
        this.plugin = plugin;
        this.configFile = new File(this.plugin.getDataFolder(), "homes.yml");
        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);
    }

    public FileConfiguration getConfig() {
        return dataConfig;
    }

    public void saveConfig() {
        try {
            this.dataConfig.save(this.configFile);
        } catch(Exception e) {
            plugin.getLogger().warning("Something happened!");
        }
    }
}