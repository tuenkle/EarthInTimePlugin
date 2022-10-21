package com.tuenkle.earthintimeplugin.gui;
import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import com.tuenkle.earthintimeplugin.gui.buttons.NationButtons;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.time.LocalDateTime;
import java.util.*;

public class NationGui {
    public static Inventory nationsList;
    public static Inventory start;
    public static Inventory getNationsList() {
        return nationsList;
    }
    public static Inventory getStart() {
        return start;
    }
    public static void makeAll() {
        makeStart();
        makeNationsList();
    }
    public static void makeStart() {
        start = getDefaultInventory("나라", false);
        start.setItem(20, NationButtons.getNationMyInfoButton());
        start.setItem(24, NationButtons.getNationListButton());
    }
    public static void makeNationsList() {
        nationsList = getDefaultInventory("나라 목록", true);
        int j = 0;

        for (Map.Entry<String, Nation> nationEntry : Database.nations.entrySet()) {
            if (j == 45) {
                nationsList.setItem(53, GeneralButtons.getNextPageButton());
            }
            String nationName = nationEntry.getValue().getName();
            nationsList.setItem(j, NationButtons.getNationNameButton(nationName));
            j++;
        }
    }
    private static Inventory getDefaultInventory(String inventoryTitle, boolean containBackButton){
        Inventory inventory = Bukkit.createInventory(null, 54, inventoryTitle);
        for (int i = 0; i < 54; i++) {
            inventory.setItem(i, GeneralButtons.getDummyButton());
        }
        if (containBackButton) {
            inventory.setItem(48, GeneralButtons.getBackButton());
        }
        inventory.setItem(49, GeneralButtons.getCloseButton());
        return inventory;
    }
    public static Inventory getNationInfo(Nation nation, User user) {
        Inventory inventory;
        if (nation.getResidents().containsKey(user)) {
            if (nation.getKing().equals(user)) {
                inventory = getDefaultInventory("나라 정보(본국)-왕", true);
                inventory.setItem(37, NationButtons.getDisbandButton());
                inventory.setItem(38, NationButtons.getInviteButton());
                inventory.setItem(39, NationButtons.getSetSpawnButton());
                inventory.setItem(40, NationButtons.getExpandButton());
                inventory.setItem(41, NationButtons.getShrinkButton());
                inventory.setItem(42, NationButtons.getWithdrawButton());
            } else {
                inventory =getDefaultInventory("나라 정보(본국)-시민", true);
                inventory.setItem(38, NationButtons.getLeaveButton());
            }
            inventory.setItem(29, NationButtons.getSpawnButton());
            inventory.setItem(30, NationButtons.getDepositButton());
            inventory.setItem(31, NationButtons.getBorderVisualizationButton());
        } else {
            Nation userNation = user.getNation();
            if (userNation == null || !userNation.getKing().equals(user)) {
                inventory = getDefaultInventory("나라 정보(타국)-시민", true);
            } else {
                inventory = getDefaultInventory("나라 정보(타국)-왕", true);
                inventory.setItem(29, NationButtons.getAttackButton(userNation, nation, Database.getRelation(userNation, nation)));
                inventory.setItem(30, NationButtons.getAllyButton(userNation, nation, Database.getRelation(userNation, nation)));
            }
        }
        inventory.setItem(4, NationButtons.getNationNameButton(nation.getName()));
        inventory.setItem(20, NationButtons.getResidentsButton(nation));
        inventory.setItem(21, NationButtons.getMoneyButton(nation));
        inventory.setItem(22, NationButtons.getAlliesButton());
        inventory.setItem(23, NationButtons.getWarsButton());
        return inventory;
    }
}
