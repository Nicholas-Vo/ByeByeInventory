package me.nickvo.byebyeinventory;

import com.sun.source.util.Plugin;
import me.nickvo.byebyeinventory.command.BaseCommand;
import me.nickvo.byebyeinventory.config.ConfigHandler;
import me.nickvo.byebyeinventory.util.PluginUtils;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class ByeByeInventory extends JavaPlugin {
    private ByeByeInventory plugin;
    private ConfigHandler config;
    private PluginUtils utils;

    public final String VERSION = "1.0.0";
    public final String PLUGIN_TAG = ChatColor.AQUA + "ByeByeInventory " + ChatColor.GRAY + ">> " + ChatColor.RESET;

    @Override
    public void onEnable() {
        plugin = this;

        config = new ConfigHandler(this);
        utils = new PluginUtils();

        new BaseCommand(this);
        new DeathListener(this);

        getLogger().info("ByeByeInventory has loaded!");
    }

    public ByeByeInventory getPlugin() {
        return plugin;
    }

    public ConfigHandler config() {
        return config;
    }

    public PluginUtils getUtils() {
        return utils;
    }
}
