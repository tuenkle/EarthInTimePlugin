package com.tuenkle.earthintimeplugin.utils;

import com.tuenkle.earthintimeplugin.database.User;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

public class GuiUtils {
    public static void moveGui(InventoryHolder previousGui, InventoryHolder newGui, User user, Player player) {
        user.addLastGui(previousGui);
        player.openInventory(newGui.getInventory());
    }
}
