package com.tuenkle.earthintimeplugin.gui.nation;

import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import com.tuenkle.earthintimeplugin.gui.buttons.NationButtons;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class NationInfoGui extends NationGui {
    public static final int NATIONNAME_SLOT = 4;
    public static final int RESIDENTS_SLOT = 20;
    public static final int MONEY_SLOT = 21;
    public static final int ALLIES_SLOT = 22;
    public static final int WARS_SLOT = 23;

    public static final int SPAWN_SLOT = 29;
    public static final int DEPOSIT_SLOT = 30;
    public static final int BORDERVISUALIZATION_SLOT = 31;

    public static final int LEAVE_SLOT = 38;

    public static final int DISBAND_SLOT = 37;
    public static final int WITHDRAW_SLOT = 38;
    public static final int SETSPAWN_SLOT = 39;
    public static final int EXPAND_SLOT = 40;
    public static final int SHRINK_SLOT = 41;
    public static final int INVITE_SLOT = 42;
    public static final int ATTACK_SLOT = 29;
    public static final int ALLY_SLOT = 30;
    public static final int CLOSE_SLOT = 49;
    public static final int BACK_SLOT = 48;

    public NationInfoGui(Nation nation, User user) {
        super(nation, user);
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, "나라 정보");
        for (int i = 0; i < 54; i++) {
            inventory.setItem(i, GeneralButtons.getDummyButton());
        }
        inventory.setItem(CLOSE_SLOT, GeneralButtons.getCloseButton());
        inventory.setItem(BACK_SLOT, GeneralButtons.getBackButton());
        inventory.setItem(NATIONNAME_SLOT, NationButtons.getNationNameOnlyButton(nation.getName()));
        if (nation.isRemoved) {
            inventory.setItem(22, getNationRemovedButton());
            return inventory;
        }
        if (nation.getResidents().containsKey(user)) {
            if (nation.getKing().equals(user)) {
                inventory.setItem(DISBAND_SLOT, NationButtons.getDisbandButton());
                inventory.setItem(WITHDRAW_SLOT, NationButtons.getWithdrawButton());
                inventory.setItem(SETSPAWN_SLOT, NationButtons.getSetSpawnButton());
                inventory.setItem(EXPAND_SLOT, NationButtons.getExpandButton());
                inventory.setItem(SHRINK_SLOT, NationButtons.getShrinkButton());
                inventory.setItem(INVITE_SLOT, NationButtons.getInviteButton());
            } else {
                inventory.setItem(LEAVE_SLOT, NationButtons.getLeaveButton());
            }
            inventory.setItem(SPAWN_SLOT, NationButtons.getSpawnButton());
            inventory.setItem(DEPOSIT_SLOT, NationButtons.getDepositButton());
            inventory.setItem(BORDERVISUALIZATION_SLOT, NationButtons.getBorderVisualizationButton());
        } else {
            Nation userNation = user.getNation();
            if (userNation == null || !userNation.getKing().equals(user)) {
            } else {
                inventory.setItem(ATTACK_SLOT, NationButtons.getAttackButton(userNation, nation, Database.getRelation(userNation, nation)));
                inventory.setItem(ALLY_SLOT, NationButtons.getAllyButton(userNation, nation, Database.getRelation(userNation, nation)));
            }
        }
        inventory.setItem(RESIDENTS_SLOT, NationButtons.getResidentsButton());
        inventory.setItem(MONEY_SLOT, NationButtons.getMoneyButton(nation));
        inventory.setItem(ALLIES_SLOT, NationButtons.getAlliesButton());
        inventory.setItem(WARS_SLOT, NationButtons.getWarsButton());

        return inventory;
    }
}
