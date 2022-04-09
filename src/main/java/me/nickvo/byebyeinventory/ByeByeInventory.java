package me.nickvo.byebyeinventory;

import me.nickvo.byebyeinventory.command.BaseCommand;
import me.nickvo.byebyeinventory.config.ConfigHandler;
import me.nickvo.byebyeinventory.config.Messages;
import me.nickvo.byebyeinventory.util.PluginUtils;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * ByeByeInventory version 1.0.0
 * Created by _NickV (GitHub: Nicholas-Vo) on 4/9/2022
 *
 * todo:
 *
 * - add keepinventory over-ride?
 * - add ability to exclude hotbar
 *
 */
public final class ByeByeInventory extends JavaPlugin {
    private ByeByeInventory plugin;
    private ConfigHandler config;
    private Messages messages;
    private PluginUtils utils;

    public final String VERSION = "1.0.0";
    public final String PLUGIN_TAG = ChatColor.AQUA + "ByeByeInventory " + ChatColor.GRAY + ">> " + ChatColor.RESET;

    @Override
    public void onEnable() {
        plugin = this;

        config = new ConfigHandler(this);
        utils = new PluginUtils();
        messages = new Messages(config.getConfigurationFile());

        new BaseCommand(this); // This is the /byebyeinv command
        new DeathListener(this);
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

    public Messages getMessages() {
        return messages;
    }
}
