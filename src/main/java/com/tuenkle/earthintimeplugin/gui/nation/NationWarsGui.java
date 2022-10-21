package com.tuenkle.earthintimeplugin.gui.nation;

import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.War;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import com.tuenkle.earthintimeplugin.gui.buttons.NationButtons;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.time.LocalDateTime;
import java.util.Map;

public class NationWarsGui implements InventoryHolder {
    public static final int CLOSE_SLOT = 49;
    public static final int BACK_SLOT = 48;

    private final Nation nation;
    public Nation getNation() {
        return nation;
    }
    public NationWarsGui(Nation nation) {
        this.nation = nation;
    }
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, "나라 전쟁 정보");
        for (int i = 0; i < 54; i++) {
            inventory.setItem(i, GeneralButtons.getDummyButton());
        }
        inventory.setItem(4, NationButtons.getNationNameOnlyButton(nation.getName()));
        int i = 0;
        for (War war : Database.getWarRelated(nation)) {
            inventory.setItem(i + 9, NationButtons.getWarInfoButton(war));
            i++;
        }
        inventory.setItem(CLOSE_SLOT, GeneralButtons.getCloseButton());
        inventory.setItem(BACK_SLOT, GeneralButtons.getBackButton());

        return inventory;
    }
}
