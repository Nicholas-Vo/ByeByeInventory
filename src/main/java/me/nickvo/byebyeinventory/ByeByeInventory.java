package me.nickvo.byebyeinventory;

import me.nickvo.byebyeinventory.command.BaseCommand;
import me.nickvo.byebyeinventory.config.ConfigHandler;
import me.nickvo.byebyeinventory.config.Messages;
import me.nickvo.byebyeinventory.util.PluginUtils;

import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * ByeByeInventory version 1.0.0
 * Created by _NickV (GitHub: Nicholas-Vo) on 4/9/2022
 */
public final class ByeByeInventory extends JavaPlugin {
    private ConfigHandler config;
    private Messages messages;
    private PluginUtils utils;

    public final String VERSION = "1.0.0";
    public final String PLUGIN_TAG = ChatColor.AQUA + "ByeByeInventory " + ChatColor.GRAY + ">> " + ChatColor.RESET;

    @Override
    public void onEnable() {
        config = new ConfigHandler(this);
        messages = new Messages();
        utils = new PluginUtils(this);

        new BaseCommand(this); // This is the /byebyeinv command
        new DeathListener(this);
        //new Metrics(this, 14891); // bStats metrics
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
