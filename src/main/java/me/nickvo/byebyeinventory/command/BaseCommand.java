package me.nickvo.byebyeinventory.command;

import me.nickvo.byebyeinventory.ByeByeInventory;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BaseCommand implements CommandExecutor, TabCompleter {
    private final ByeByeInventory plugin;

    public BaseCommand(ByeByeInventory plugin) {
        this.plugin = plugin;

        plugin.getCommand("byebyeinv").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command c, @NotNull String a, @NotNull String[] args) {
        if (!sender.hasPermission("byebyeinventory.admin")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to do that.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(plugin.PLUGIN_TAG + "Running version " + ChatColor.GRAY + plugin.VERSION);
            sender.sendMessage(plugin.PLUGIN_TAG + "Support: " + ChatColor.GRAY + "https://discord.gg/fGzb73sPmV");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.config().rebuildConfig();
            sender.sendMessage(plugin.PLUGIN_TAG + "Successfully reloaded the configuration.");
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command c, @NotNull String a, @NotNull String[] args) {
        if (!sender.hasPermission("byebyeinventory.admin")) {
            return List.of(); // Return empty list; Will tab-complete nothing
        }

        /*
        Only one command for now
         */
        List<String> results = new ArrayList<>();
        for (String s : List.of("reload")) {
            if (s.toLowerCase().startsWith(args[0].toLowerCase()))
                results.add(s);
        }
        return results;
    }
}
