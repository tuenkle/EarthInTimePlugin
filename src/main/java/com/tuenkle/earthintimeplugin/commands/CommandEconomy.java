package com.tuenkle.earthintimeplugin.commands;

import com.tuenkle.earthintimeplugin.EarthInTimePlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.tuenkle.earthintimeplugin.EarthInTimePlugin.getMainInstance;

public class CommandEconomy implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args[0].equals("deposit")) {
                EarthInTimePlugin.economyImplementer.depositPlayer(args[1], 100);
                return true;
            }
            if (args[0].equals("balance")) {
                player.sendMessage(String.valueOf(EarthInTimePlugin.economyImplementer.getBalance(args[1])));
                return true;
            }
        }
        return false;
    }
}
