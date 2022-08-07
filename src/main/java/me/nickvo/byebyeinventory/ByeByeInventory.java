package me.nickvo.byebyeinventory;

import me.nickvo.byebyeinventory.command.BaseCommand;
import me.nickvo.byebyeinventory.config.ConfigHandler;
import me.nickvo.byebyeinventory.config.Messages;
import me.nickvo.byebyeinventory.util.PluginUtils;

import me.nickvo.byebyeinventory.util.Updater;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * ByeByeInventory Minecraft plugin
 * Created by Nicholas-Vo on 4/9/2022
 */
public final class ByeByeInventory extends JavaPlugin {
    private ConfigHandler config;
    private Messages messages;
    private PluginUtils utils;
    public final String PLUGIN_TAG = ChatColor.RED + "ByeByeInventory " + ChatColor.GRAY + ">> " + ChatColor.RESET;

    @Override
    public void onEnable() {
        config = new ConfigHandler(this);
        messages = new Messages();
        utils = new PluginUtils(this);
        new BaseCommand(this); // This is the /byebyeinv command
        new DeathListener(this);

        if (config.getBoolean("metrics-enabled")) {
            Bukkit.getScheduler().runTaskAsynchronously(this, () -> new Metrics(this, 14891));
        }
        if (config.getBoolean("updater-enabled")) {
            new Updater(getLogger(), getDescription().getVersion());
        }
    }

    public ConfigHandler config() {
        return config;
    }

    public PluginUtils getUtils() {
        return utils;
    }

    public Messages getMessages() {
        return messages;
    }
}
