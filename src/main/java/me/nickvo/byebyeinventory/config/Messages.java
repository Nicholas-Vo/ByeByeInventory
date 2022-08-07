package me.nickvo.byebyeinventory.config;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class Messages {
    /**
     * Translate string from config into string to display
     */
    public String translate(Player player, int itemsLost, String message) {
        return getColor(message
                .replace("%playername%", player.getName())
                .replace("%newline%", "\n")
                .replace("%count%", formatNumber(itemsLost)))
                .replace("%dimension%", getDimension(player));
    }

    private String getColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private String formatNumber(int itemsLost) {
        DecimalFormat nFormat = new DecimalFormat("#,###");
        return nFormat.format(itemsLost);
    }

    private String getDimension(Player player) {
        World.Environment world = player.getWorld().getEnvironment();
        String dimension;
        switch (world) {
            case NORMAL -> dimension = "overworld";
            case NETHER -> dimension = "nether";
            case THE_END -> dimension = "end";
            default -> dimension = world.name(); // If it's a custom dimension, just return the name
        }
        return dimension;
    }

}
