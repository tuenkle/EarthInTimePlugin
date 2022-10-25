package com.tuenkle.earthintimeplugin.gui.war;

import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.database.War;
import com.tuenkle.earthintimeplugin.gui.Gui;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import com.tuenkle.earthintimeplugin.gui.buttons.NationButtons;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

public class WarGui extends Gui {
    protected final War war;
    public War getWar() {
        return war;
    }
    public User getUser() {
        return user;
    }
    public ItemStack getWarTitleButton() {
        return getButton(Material.GREEN_WOOL,ChatColor.GREEN + war.getAttackNation().getName() + " -> " + war.getDefendNation().getName());
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
    public WarGui(War war, User user) {
        super(user);
        this.war = war;
    }
}
