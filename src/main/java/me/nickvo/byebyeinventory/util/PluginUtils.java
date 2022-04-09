package me.nickvo.byebyeinventory.util;

import me.nickvo.byebyeinventory.ByeByeInventory;
import me.nickvo.byebyeinventory.config.ConfigHandler;
import me.nickvo.byebyeinventory.config.Messages;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PluginUtils {
    private final ByeByeInventory plugin;
    private final ConfigHandler config;
    private final Messages msg;

    public PluginUtils(ByeByeInventory plugin) {
        this.plugin = plugin;

        config = plugin.config();
        msg = plugin.getMessages();
    }

    public void voidInventory(PlayerDeathEvent e) {
        Player player = e.getPlayer();

        // If keep inventory is on, we don't want to announce or do anything
        if (player.getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY)) {
            return;
        }

        int count = plugin.getUtils().countInventory(player);

        if (count <= 0) {
            return;
        }

        // Set of the items we don't want deleted. Using set to avoid duplicates in the case
        // where an item we want to keep has the exact same data as an item in the inventory
        Set<ItemStack> keep = new HashSet<>();

        e.getDrops().forEach(item -> {
            if (config.getBoolean("exclude-armor")) {
                if (getArmorContents(player).contains(item)) {
                    keep.add(item);
                }
            }
            if (config.getBoolean("exclude-hotbar")) {
                if (getHotbarContents(player).contains(item)) {
                    keep.add(item);
                }
            }
        });

        e.getDrops().clear();
        keep.forEach(itemStack -> e.getDrops().add(itemStack));

        if ((count -= keep.size()) <= 0) {
            return;
        }

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

    public int countInventory(Player p) {
        int count = 0;

        for (ItemStack item : p.getInventory().getContents()) {
            if (item != null) {
                count += item.getAmount();
            }
        }

        return count;
    }

    public List<ItemStack> getHotbarContents(Player p) {
        List<ItemStack> hotbar = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            ItemStack theItem = p.getInventory().getItem(i);

            if (theItem != null) {
                hotbar.add(theItem);
            }
        }

        return hotbar;
    }

    /**
     * Get armor contents + the offhand item
     */
    public List<ItemStack> getArmorContents(Player p) {
        List<ItemStack> armor = new ArrayList<>();

        for (int i = 36; i <= 39; i++) {
            ItemStack theItem = p.getInventory().getItem(i);

            if (theItem != null) {
                armor.add(theItem);
            }
        }

        armor.add(p.getInventory().getItemInOffHand());
        return armor;
    }

}
