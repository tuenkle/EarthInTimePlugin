package com.tuenkle.earthintimeplugin.gui.war;

import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.database.War;
import com.tuenkle.earthintimeplugin.gui.Gui;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import com.tuenkle.earthintimeplugin.gui.buttons.NationButtons;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;



public class WarListGui extends Gui {
    public static final int CLOSE_SLOT = 49;
    public static final int BACK_SLOT = 48;
    public WarListGui(User user) {
        super(user);
    }
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, "전쟁");
        setDefaultInventory(inventory);
        int i = 0; //TODO-다음페이지
        for (War war : Database.wars) {
            inventory.setItem(i + 9, NationButtons.getWarInfoButton(war));
            i++;
        }
        return inventory;
    }
}
