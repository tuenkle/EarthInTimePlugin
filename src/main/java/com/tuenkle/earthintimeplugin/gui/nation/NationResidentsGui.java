package com.tuenkle.earthintimeplugin.gui.nation;

import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import com.tuenkle.earthintimeplugin.gui.buttons.NationButtons;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.time.LocalDateTime;
import java.util.Map;

public class NationResidentsGui implements InventoryHolder {
    public static final int CLOSE_SLOT = 49;
    public static final int BACK_SLOT = 48;

    private final Nation nation;
    public Nation getNation() {
        return nation;
    }
    public NationResidentsGui(Nation nation) {
        this.nation = nation;
    }
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, "나라 구성원 정보");
        for (int i = 0; i < 54; i++) {
            inventory.setItem(i, GeneralButtons.getDummyButton());
        }
        inventory.setItem(4, NationButtons.getNationNameOnlyButton(nation.getName()));
        int i = 0;
        for (Map.Entry<User, LocalDateTime> user : nation.getResidents().entrySet()) {
            inventory.setItem(i + 9, NationButtons.getUserButton(user.getKey(), user.getValue(), nation.getKing().equals(user.getKey())));
            i++;
        }
        inventory.setItem(CLOSE_SLOT, GeneralButtons.getCloseButton());
        inventory.setItem(BACK_SLOT, GeneralButtons.getBackButton());

        return inventory;
    }
}