package com.tuenkle.earthintimeplugin.commands;

import com.sun.tools.javac.Main;
import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.gui.MainGui;
import com.tuenkle.earthintimeplugin.utils.GuiUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGui implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }
        User user = Database.users.get(player.getUniqueId());
        GuiUtils.openGui(new MainGui(user), user, player);
        return false;
    }
}
