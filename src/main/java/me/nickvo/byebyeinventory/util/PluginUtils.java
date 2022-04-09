package me.nickvo.byebyeinventory.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

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

    public boolean itemInHotbar(Player p, ItemStack item) {
        for (int i = 0; i < 8; i++) {
            if (p.getInventory().getItem(i) == null) {
                continue;
            }
            if (p.getInventory().getItem(i).equals(item)) {
                return true;
            }
        }

        return false;
    }

    // Iterate through all armor slots
    public boolean itemInArmorSlot(Player p, ItemStack item) {
        for (int i = 36; i <= 39; i++) {
            ItemStack theItem = p.getInventory().getItem(i);

            if (theItem == null) {
                continue;
            }
            if (Objects.deepEquals(theItem, item)) {
                return true;
            }
        }

        return false;
    }

}
