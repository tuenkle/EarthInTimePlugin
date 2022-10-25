package com.tuenkle.earthintimeplugin.gui;

import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import com.tuenkle.earthintimeplugin.gui.buttons.NationButtons;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Gui implements InventoryHolder {
    protected User user;
    protected static final int CLOSE_SLOT = 49;
    protected static final int BACK_SLOT = 48;
    protected static final int MAINMENU_SLOT = 45;
    public final int size = 54;
    public int getSize() {
        return size;
    }
    public Gui(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
    protected ItemStack getButton(Material material, String name) {
        ItemStack button = new ItemStack(material);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(name);
        button.setItemMeta(buttonMeta);
        return button;
    }
    protected ItemStack getButtonWithLores(Material material, String name, String ...lores) {
        ItemStack button = new ItemStack(material);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(name);
        buttonMeta.setLore(Arrays.stream(lores).toList());
        button.setItemMeta(buttonMeta);
        return button;
    }
    protected void setDefaultInventory (Inventory inventory) {
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, GeneralButtons.getDummyButton());
        }
        inventory.setItem(CLOSE_SLOT, GeneralButtons.getCloseButton());
        inventory.setItem(MAINMENU_SLOT, GeneralButtons.mainMenuButton);
        if (user.hasLastGui()) {
            inventory.setItem(BACK_SLOT, GeneralButtons.getBackButton());
        }
    }
    protected void setDefaultInventoryOneLine (Inventory inventory) {
        for (int i = 0; i <= 8; i++) {
            inventory.setItem(i, GeneralButtons.getDummyButton());
        }
        for (int i = 18; i < size; i++) {
            inventory.setItem(i, GeneralButtons.getDummyButton());
        }
        inventory.setItem(CLOSE_SLOT, GeneralButtons.getCloseButton());
        inventory.setItem(BACK_SLOT, GeneralButtons.getBackButton());
    }
    @Override
    public Inventory getInventory() {
        return null;
    }
}
