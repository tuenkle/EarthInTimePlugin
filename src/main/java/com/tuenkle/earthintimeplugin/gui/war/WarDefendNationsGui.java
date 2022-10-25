package com.tuenkle.earthintimeplugin.gui.war;

import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.database.War;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import com.tuenkle.earthintimeplugin.gui.buttons.NationButtons;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class WarDefendNationsGui extends WarGui {
    public WarDefendNationsGui(War war, User user) {
        super(war, user);
    }
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, "전쟁 수비국 목록");
        setDefaultInventory(inventory);
        inventory.setItem(4, getWarTitleButton());
        int i = 0;
        for (Nation nation : war.getDefendNations()) {
            inventory.setItem(i + 9, NationButtons.getNationNameButton(nation.getName()));
        }
        return inventory;
    }
}