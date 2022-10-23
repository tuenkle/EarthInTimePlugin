package com.tuenkle.earthintimeplugin.gui.nation;

import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.database.War;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class NationGui implements InventoryHolder {
    protected final Nation nation;
    protected final User user;
    protected int size = 54;
    public int getSize() {
        return size;
    }
    public User getUser() {
        return user;
    }
    public Nation getNation() {
        return nation;
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
    public ItemStack getNationRemovedButton() {
        return getButton(Material.BARRIER, "삭제된 나라입니다.");
    }
    public ItemStack getNationNullButton() {
        return getButtonWithLores(Material.BARRIER, "나라가 존재하지 않습니다.", "/나라 만들기 <나라이름>");
    }
    public NationGui(Nation nation, User user) {
        this.nation = nation;
        this.user = user;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
