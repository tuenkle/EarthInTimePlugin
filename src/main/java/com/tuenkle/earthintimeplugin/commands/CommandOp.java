package com.tuenkle.earthintimeplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandOp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args[0].equals("chunk")) {
                player.sendMessage("x: " + player.getLocation().getChunk().getX() + "z: " + player.getLocation().getChunk().getZ());
            }
        }
        return true;
    }
}
