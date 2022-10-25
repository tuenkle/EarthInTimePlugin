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

public class NationResidentsGui extends NationGui {
    public static final int CLOSE_SLOT = 49;
    public static final int BACK_SLOT = 48;

    public NationResidentsGui(Nation nation, User user) {
        super(nation, user);
    }
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, "나라 구성원 정보");
        setDefaultInventory(inventory);
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
        for (Map.Entry<User, LocalDateTime> user : nation.getResidents().entrySet()) {
            inventory.setItem(i + 9, NationButtons.getUserButton(user.getKey(), user.getValue(), nation.getKing().equals(user.getKey())));
            i++;
        }

        return inventory;
    }
}
