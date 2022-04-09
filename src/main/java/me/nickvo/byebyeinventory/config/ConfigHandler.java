package me.nickvo.byebyeinventory.config;

import me.nickvo.byebyeinventory.ByeByeInventory;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigHandler {
    private final ByeByeInventory plugin;
    private YamlConfiguration config;

    public ConfigHandler(ByeByeInventory plugin) {
        this.plugin = plugin;

        rebuildConfig();
    }

    /*
    List of dimensions where the plugin will void inventories
     */
    private final List<World.Environment> dimensions = new ArrayList<>();

    public void rebuildConfig() {
        File conf = new File(plugin.getDataFolder() + File.separator + "config.yml");

        if (!conf.exists()) {
            plugin.saveResource("config.yml", true);
        }

        config = YamlConfiguration.loadConfiguration(conf);

        dimensions.clear();

        // Get list from configuration
        if (config.getBoolean("enabled-in-overworld")) {
            dimensions.add(World.Environment.NORMAL);
        }

        if (config.getBoolean("enabled-in-nether")) {
            dimensions.add(World.Environment.NETHER);
        }

        if (config.getBoolean("enabled-in-end")) {
            dimensions.add(World.Environment.THE_END);
        }
    }

    public boolean getBoolean(String key) {
        return config.getBoolean(key);
    }

    public List<World.Environment> getDimensions() {
        return dimensions;
    }

}












