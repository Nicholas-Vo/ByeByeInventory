package me.nickvo.byebyeinventory.util;

import me.nickvo.byebyeinventory.ByeByeInventory;
import me.nickvo.byebyeinventory.config.ConfigHandler;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PluginUtils {
    private final ConfigHandler config;

    public PluginUtils(ByeByeInventory plugin) {
        config = plugin.config();
    }

    public List<ItemStack> getKeptItems(Player p) {
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
