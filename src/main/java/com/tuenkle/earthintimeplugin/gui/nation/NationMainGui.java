package com.tuenkle.earthintimeplugin.gui.nation;

import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import com.tuenkle.earthintimeplugin.gui.buttons.NationButtons;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class NationMainGui implements InventoryHolder {
    public static final int INFO_SLOT = 20;
    public static final int LIST_SLOT = 24;
    public static final int CLOSE_SLOT = 49;
    public static final int BACK_SLOT = 48;

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, "나라");
        for (int i = 0; i < 54; i++) {
            inventory.setItem(i, GeneralButtons.getDummyButton());
        }
        inventory.setItem(INFO_SLOT, NationButtons.getNationMyInfoButton());
        inventory.setItem(LIST_SLOT, NationButtons.getNationListButton());
        inventory.setItem(CLOSE_SLOT, GeneralButtons.getCloseButton());

        return inventory;
    }
}