package me.nickvo.byebyeinventory;

import me.nickvo.byebyeinventory.config.ConfigHandler;
import me.nickvo.byebyeinventory.util.PluginUtils;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
    private final ConfigHandler config;
    private final PluginUtils utils;

    public DeathListener(ByeByeInventory plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        config = plugin.config();
        utils = plugin.getUtils();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getPlayer();
        if (!player.hasPermission("byebyeinventory.lose")) {
            return;
        }
        if (player.hasPermission("byebyeinventory.exempt")) {
            return;
        }

        World.Environment playerWorld = player.getWorld().getEnvironment();
        if (config.getDimensions().contains(playerWorld)) { // Check if the player is in a world where the plugin is active
            utils.voidInventory(e);
        }
    }

}
