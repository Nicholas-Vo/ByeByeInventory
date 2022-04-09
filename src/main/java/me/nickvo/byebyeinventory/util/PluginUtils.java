package me.nickvo.byebyeinventory.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PluginUtils {

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
