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

public class NationAlliesGui extends NationGui {
    public static final int CLOSE_SLOT = 49;
    public static final int BACK_SLOT = 48;
    public NationAlliesGui(Nation nation, User user) {
        super(nation, user);
    }
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, "나라 동맹 정보");
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
        for (Map.Entry<Nation, LocalDateTime> targetNation : nation.getAllies().entrySet()) {
            inventory.setItem(i + 9, NationButtons.getAllyInfoButton(targetNation.getKey(), targetNation.getValue()));
            i++;
        }


        return inventory;
    }
}
