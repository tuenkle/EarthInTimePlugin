package com.tuenkle.earthintimeplugin.gui.nation;

import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.gui.Gui;
import com.tuenkle.earthintimeplugin.gui.buttons.NationButtons;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class NationMainGui extends Gui {
    private static final int INFO_SLOT = 20;
    private static final int LIST_SLOT = 24;

    public NationMainGui(User user) {
        super(user);
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, "나라");
        setDefaultInventory(inventory);
        inventory.setItem(INFO_SLOT, NationButtons.getNationMyInfoButton());
        inventory.setItem(LIST_SLOT, NationButtons.getNationListButton());
        return inventory;
    }
}
