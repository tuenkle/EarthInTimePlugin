package com.tuenkle.earthintimeplugin.gui;

import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MainGui extends Gui {
    private static final int NATIONMENU_SLOT = 20;
    private static final int WARMENU_SLOT = 24;
    public MainGui(User user) {
        super(user);
    }
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, "메인 메뉴");
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, GeneralButtons.getDummyButton());
        }
        if (user.hasLastGui()) {
            inventory.setItem(BACK_SLOT, GeneralButtons.getBackButton());
        }
        inventory.setItem(CLOSE_SLOT, GeneralButtons.getCloseButton());
        inventory.setItem(NATIONMENU_SLOT, GeneralButtons.nationMenuButton);
        inventory.setItem(WARMENU_SLOT, GeneralButtons.warMenuButton);
        return inventory;
    }
}
