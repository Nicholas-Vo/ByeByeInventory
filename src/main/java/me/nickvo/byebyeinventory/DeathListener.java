package me.nickvo.byebyeinventory;

import me.nickvo.byebyeinventory.config.ConfigHandler;
import me.nickvo.byebyeinventory.config.Messages;
import me.nickvo.byebyeinventory.util.PluginUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DeathListener implements Listener {
    private final ByeByeInventory plugin;
    private final ConfigHandler config;
    private final PluginUtils utils;
    private final Messages msg;

    public DeathListener(ByeByeInventory plugin, Messages msg) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        config = plugin.config();
        utils = plugin.getUtils();

        this.plugin = plugin;
        this.msg = msg;
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
            voidInventory(e);
        }
    }

    public void voidInventory(PlayerDeathEvent e) {
        Player player = e.getPlayer();
        // If keep inventory is on, we don't want to announce or do anything
        if (Boolean.TRUE.equals(player.getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY))) {
            return;
        }
        int count = plugin.getUtils().countInventory(player);
        if (count <= 0) {
            return;
        }

        List<ItemStack> keep = utils.getKeptItems(player);
        e.getDrops().clear(); // Clear out all items dropped upon death and add back the ones we want to keep.
        keep.forEach(itemStack -> e.getDrops().add(itemStack));

        // Don't announce anything if the player didn't lose any items
        if ((count -= keep.size()) <= 0) {
            return;
        }

        if (config.getBoolean("log-death-to-console")) {
            Bukkit.getLogger().info("[ByeByeInventory] "
                    + player.getName() + "'s inventory was cleared upon death! " + count + " items were lost.");
        }

        if (config.getBoolean("display-message-to-server")) {
            String theMsg = config.getMessage("message-to-server");
            Bukkit.broadcast(Component.text(msg.translate(player, count, theMsg)), Server.BROADCAST_CHANNEL_USERS);
        }

        if (config.getBoolean("display-message-to-player")) {
            String theMsg = config.getMessage("message-to-player");
            player.sendMessage(msg.translate(player, count, theMsg));
        }
    }

}
