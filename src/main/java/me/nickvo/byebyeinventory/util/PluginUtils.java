package me.nickvo.byebyeinventory.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        List<ItemStack> hotbar = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            ItemStack theItem = p.getInventory().getItem(i);
            if (theItem != null) {
                hotbar.add(theItem);
            }
        }

        return hotbar.contains(item);
    }

    // Iterate through all armor slots
    public boolean itemInArmorSlot(Player p, ItemStack item) {
        List<ItemStack> armorSlot = new ArrayList<>();

        for (int i = 36; i <= 39; i++) {
            ItemStack theItem = p.getInventory().getItem(i);

            if (theItem != null) {
                armorSlot.add(p.getInventory().getItem(i));
            }
        }

        return armorSlot.contains(item);
    }

}
