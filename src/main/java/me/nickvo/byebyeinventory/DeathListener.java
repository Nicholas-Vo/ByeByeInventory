package me.nickvo.byebyeinventory;

import me.nickvo.byebyeinventory.config.ConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
    private final ByeByeInventory plugin;
    private final ConfigHandler config;

    public DeathListener(ByeByeInventory plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        config = plugin.config();

        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getPlayer();

        if (!player.hasPermission("byebyeinventory.lose")) {
            return;
        }

        World.Environment playerWorld = player.getWorld().getEnvironment();

        if (config.getDimensions().contains(playerWorld)) {
            voidInventory(e);
        }
    }

    private void voidInventory(PlayerDeathEvent e) {
        Player player = e.getPlayer();

        e.getDrops().clear();

        if (config.getBoolean("log-death-to-console")) {

            Bukkit.getLogger().info(plugin.PLUGIN_TAG + player.getName() + "'s inventory was cleared! "
                    + plugin.getUtils().countInventory(player) + " items were lost.");
        }
    }
}
