package com.tuenkle.earthintimeplugin.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.tuenkle.earthintimeplugin.EarthInTimePlugin.getSpawnLocation;

public class CommandSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player player) {
            player.teleport(getSpawnLocation());
        }
        return false;
    }
}
