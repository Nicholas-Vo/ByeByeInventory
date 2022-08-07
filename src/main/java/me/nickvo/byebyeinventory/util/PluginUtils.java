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
import java.util.List;

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
        if (Boolean.TRUE.equals(player.getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY))) {
            return;
        }
        int count = plugin.getUtils().countInventory(player);
        if (count <= 0) {
            return;
        }

        List<ItemStack> keep = getKeptItems(player);
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

    private List<ItemStack> getKeptItems(Player p) {
        List<ItemStack> items = new ArrayList<>();
        boolean excludeArmor = config.getBoolean("exclude-armor");
        boolean excludeHotbar = config.getBoolean("exclude-hotbar");
        boolean excludeOffhand = config.getBoolean("exclude-offhand");

        for (int i = 0; i < p.getInventory().getSize(); i++) {
            ItemStack theItem = p.getInventory().getItem(i);
            if (theItem == null) {
                continue;
            }
            if (config.getExcludedItems().contains(theItem.getType()))
                items.add(theItem);
            else if ((i > 35 && i <= 39) && excludeArmor)
                items.add(theItem);
            else if (i <= 8 && excludeHotbar)
                items.add(theItem);
            else if (i == 40 && excludeOffhand)
                items.add(theItem);
        }
        return items;
    }

    /**
     * Count how many items are in an inventory
     */
    public int countInventory(Player p) {
        int count = 0;
        for (ItemStack item : p.getInventory().getContents()) {
            if (item != null) {
                count += item.getAmount();
            }
        }
        return count;
    }

}
