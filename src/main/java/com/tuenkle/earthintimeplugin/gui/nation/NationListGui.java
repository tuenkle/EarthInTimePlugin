package com.tuenkle.earthintimeplugin.gui.nation;

import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import com.tuenkle.earthintimeplugin.gui.buttons.NationButtons;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Map;

public class NationListGui extends NationGui {
    public static final int CLOSE_SLOT = 49;
    public static final int BACK_SLOT = 48;
    public NationListGui(Nation nation, User user) {
        super(nation, user);
    }
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, "나라 목록");
        for (int i = 0; i < 54; i++) {
            inventory.setItem(i, GeneralButtons.getDummyButton());
        }
        int j = 0;
        for (Map.Entry<String, Nation> nationEntry : Database.nations.entrySet()) {
            if (j == 45) {
                inventory.setItem(53, GeneralButtons.getNextPageButton());
            }
            String nationName = nationEntry.getValue().getName();
            inventory.setItem(j, NationButtons.getNationNameButton(nationName));
            j++;
        }
        inventory.setItem(CLOSE_SLOT, GeneralButtons.getCloseButton());
        inventory.setItem(BACK_SLOT, GeneralButtons.getBackButton());

        return inventory;
    }
}
