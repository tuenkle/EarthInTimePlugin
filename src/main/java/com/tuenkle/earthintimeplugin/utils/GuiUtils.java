package com.tuenkle.earthintimeplugin.utils;

import com.tuenkle.earthintimeplugin.database.User;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

public class GuiUtils {
    public static void moveGui(InventoryHolder previousGui, InventoryHolder newGui, User user, Player player) {
        user.addLastGui(previousGui);
        user.isGuiMoving = true;
        player.openInventory(newGui.getInventory());
        user.isGuiMoving = false;
    }
    public static void backGui(User user, Player player) {
        InventoryHolder lastGui = user.popLastGui();
        if (lastGui == null) {
            player.closeInventory();
            return;
        }
        user.isGuiMoving = true;
        player.openInventory(lastGui.getInventory());
        user.isGuiMoving = false;
    }
    public static void moveToMainGui(InventoryHolder newGui, User user, Player player) {
        user.isGuiMoving = true;
        user.resetGuiList();
        player.openInventory(newGui.getInventory());
        user.isGuiMoving = false;
    }
}
