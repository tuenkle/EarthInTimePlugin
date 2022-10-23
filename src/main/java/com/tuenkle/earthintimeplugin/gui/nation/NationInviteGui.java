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

public class NationInviteGui extends NationGui {
    public static final int CLOSE_SLOT = 49;
    public static final int BACK_SLOT = 48;
    public NationInviteGui(Nation nation, User user) {
        super(nation, user);
    }
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, "나라 초대 정보");
        for (int i = 0; i <= 8; i++) {
            inventory.setItem(i, GeneralButtons.getDummyButton());
        }
        for (int i = 18; i <= 53; i++) {
            inventory.setItem(i, GeneralButtons.getDummyButton());
        }
        inventory.setItem(48, GeneralButtons.getBackButton());
        inventory.setItem(49, GeneralButtons.getCloseButton());
        if (nation == null) {
            inventory.setItem(22, getNationNullButton());
            return inventory;
        }
        inventory.setItem(4, NationButtons.getNationNameOnlyButton(nation.getName()));
        if (nation.isRemoved) {
            inventory.setItem(22, getNationRemovedButton());
            return inventory;
        }
        inventory.setItem(31, NationButtons.getInviteRequestButton());
        int i = 0;
        for (Map.Entry<User, LocalDateTime> user : nation.getInvites().entrySet()) {
            inventory.setItem(i + 9, NationButtons.getInvitedUserButton(user.getKey(), user.getValue()));
            i++;
        }

        return inventory;
    }
}
