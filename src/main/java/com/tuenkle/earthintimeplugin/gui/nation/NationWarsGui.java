package com.tuenkle.earthintimeplugin.gui.nation;

import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.database.War;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import com.tuenkle.earthintimeplugin.gui.buttons.NationButtons;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.time.LocalDateTime;
import java.util.Map;

public class NationWarsGui extends NationGui {
    public static final int CLOSE_SLOT = 49;
    public static final int BACK_SLOT = 48;
    public NationWarsGui(Nation nation, User user) {
        super(nation, user);
    }
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, "나라 전쟁 정보");
        for (int i = 0; i < 54; i++) {
            inventory.setItem(i, GeneralButtons.getDummyButton());
        }
        inventory.setItem(CLOSE_SLOT, GeneralButtons.getCloseButton());
        inventory.setItem(BACK_SLOT, GeneralButtons.getBackButton());
        if (nation == null) {
            inventory.setItem(22, getNationNullButton());
            return inventory;
        }
        inventory.setItem(4, NationButtons.getNationNameOnlyButton(nation.getName()));
        if (nation.isRemoved) {
            inventory.setItem(22, getNationRemovedButton());
            return inventory;
        }
        int i = 0;
        for (War war : Database.getWarRelated(nation)) {
            inventory.setItem(i + 9, NationButtons.getWarInfoButton(war));
            i++;
        }
        return inventory;
    }
}
