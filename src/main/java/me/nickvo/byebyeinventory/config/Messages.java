package me.nickvo.byebyeinventory.config;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
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

        if (world == World.Environment.NORMAL)
            return "overworld";
        if (world == World.Environment.NETHER)
            return "nether";
        if (world == World.Environment.THE_END)
            return "end";

        return world.name(); // If it's a custom dimension, return its name
    }

}
