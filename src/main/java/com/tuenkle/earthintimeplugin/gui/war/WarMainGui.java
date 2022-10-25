package com.tuenkle.earthintimeplugin.gui.war;

import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.database.War;
import com.tuenkle.earthintimeplugin.gui.Gui;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import com.tuenkle.earthintimeplugin.gui.buttons.NationButtons;
import com.tuenkle.earthintimeplugin.gui.nation.NationGui;
import com.tuenkle.earthintimeplugin.gui.war.WarGui;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Map;



public class WarMainGui extends Gui {
    public static final int CLOSE_SLOT = 49;
    public static final int BACK_SLOT = 48;
    private static final int INFO_SLOT = 20;
    private static final int LIST_SLOT = 24;
    public WarMainGui(User user) {
        super(user);
        warMyInfoButton = getButtonWithLores(Material.CRAFTING_TABLE, ChatColor.GREEN + "전쟁 정보", ChatColor.GRAY + "소속된 나라의 전쟁 정보를 봅니다!");
        warListButton = getButtonWithLores(Material.BOOKSHELF, ChatColor.GREEN + "전쟁 목록", ChatColor.GRAY + "소속된 나라의 전쟁 정보를 봅니다!");
    }
    public ItemStack warMyInfoButton;
    public ItemStack warListButton;
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, "전쟁");
        for (int i = 0; i < 54; i++) {
            inventory.setItem(i, GeneralButtons.getDummyButton());
        }
        inventory.setItem(CLOSE_SLOT, GeneralButtons.getCloseButton());
        inventory.setItem(BACK_SLOT, GeneralButtons.getBackButton());
        inventory.setItem(INFO_SLOT, warMyInfoButton);
        inventory.setItem(LIST_SLOT, warListButton);
        return inventory;
    }
}
