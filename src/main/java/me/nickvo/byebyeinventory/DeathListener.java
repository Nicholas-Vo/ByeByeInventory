package me.nickvo.byebyeinventory;

import me.nickvo.byebyeinventory.config.ConfigHandler;
import me.nickvo.byebyeinventory.config.Messages;
import me.nickvo.byebyeinventory.util.PluginUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class DeathListener implements Listener {
    private final ByeByeInventory plugin;
    private final ConfigHandler config;
    private final PluginUtils utils;
    private final Messages msg;

    public DeathListener(ByeByeInventory plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        config = plugin.config();
        utils = plugin.getUtils();
        msg = plugin.getMessages();

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
        int count = plugin.getUtils().countInventory(player);

        if (count <= 0) {
            return;
        }

        /*
        Set of the items we don't want deleted. Using set to avoid duplicates
        in the case where an item to keep is the exact same data as an item
        in the inventory.
         */
        Set<ItemStack> keep = new HashSet<>();

        e.getDrops().forEach(item -> {
            if (config.getBoolean("exclude-armor")) {
                player.sendMessage(item.getType().name() + " -> " + ChatColor.RED + utils.itemInArmorSlot(player, item));
                if (utils.itemInArmorSlot(player, item) || player.getInventory().getItemInOffHand().equals(item)) {
                    keep.add(item);
                }

            }
            if (config.getBoolean("exclude-hotbar") && utils.itemInHotbar(player, item)) {
                keep.add(item);
            }
        });

        e.getDrops().clear();
        keep.forEach(itemStack -> e.getDrops().add(itemStack));
        count -= keep.size();

        if (config.getBoolean("log-death-to-console")) {
            Bukkit.getLogger().info("[ByeByeInventory] "
                    + player.getName() + "'s inventory was cleared upon death! " + count + " items were lost.");
        }

        if (config.getBoolean("display-message-to-server")) {
            String theMsg = config.getMessage("message-to-server");
            Component component = Component.text(msg.translate(player, count, theMsg));

            Bukkit.broadcast(component, Server.BROADCAST_CHANNEL_USERS);
        }

        if (config.getBoolean("display-message-to-player")) {
            String theMsg = config.getMessage("message-to-player");

            player.sendMessage(msg.translate(player, count, theMsg));
        }
    }
}
