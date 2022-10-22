package com.tuenkle.earthintimeplugin.gui.war;

import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.War;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import com.tuenkle.earthintimeplugin.gui.buttons.NationButtons;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class WarDefendNationsGui extends WarGui implements InventoryHolder {
    private final Inventory inventory;
    public WarDefendNationsGui(War war) {
        super(war);
        Inventory inventory = Bukkit.createInventory(this, 54, "전쟁 수비국 목록");
        for (int i = 0; i < 54; i++) {
            inventory.setItem(i, GeneralButtons.getDummyButton());
        }
        inventory.setItem(4, getWarTitleButton());
        inventory.setItem(48, GeneralButtons.getBackButton());
        inventory.setItem(49, GeneralButtons.getCloseButton());
        int i = 0;
        for (Nation nation : war.getDefendNations()) {
            inventory.setItem(i + 9, NationButtons.getNationNameOnlyButton(nation.getName()));
        }
        this.inventory = inventory;
    }
    public War getWar() {
        return war;
    }
    @Override
    public Inventory getInventory() {
        return inventory;
    }
}