package com.tuenkle.earthintimeplugin.gui.war;

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
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDateTime;
import java.util.Map;

public class WarInfoGui extends WarGui implements InventoryHolder {
    public ItemStack getWarTitleButton() {
        return getButton(Material.GREEN_WOOL,ChatColor.GREEN + war.getAttackNation().getName() + " -> " + war.getDefendNation().getName());
    }
    public ItemStack getAttackNationsButton() {
        return getButtonWithLores(Material.GREEN_WOOL, "공격국 목록"
        , ChatColor.GRAY + "공격국 목록을 보려면 클릭하세요!");
    }
    public ItemStack getAttackNationButton() {
        return getButtonWithLores(Material.GREEN_WOOL, "공격국"
                , ChatColor.GREEN + war.getAttackNation().getName());
    }
    public ItemStack getDefendNationButton() {
        return getButtonWithLores(Material.GREEN_WOOL, "수비국"
                , ChatColor.GREEN + war.getDefendNation().getName());
    }
    public ItemStack getDefendNationsButton() {
        return getButtonWithLores(Material.GREEN_WOOL, "수비국 목록"
                , ChatColor.GRAY + "공격국 목록을 보려면 클릭하세요!");
    }
    public WarInfoGui(War war) {
        super(war);
    }
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, "전쟁 정보");
        for (int i = 0; i < 54; i++) {
            inventory.setItem(i, GeneralButtons.getDummyButton());
        }
        inventory.setItem(4, getWarTitleButton());
        inventory.setItem(20, getAttackNationsButton());
        inventory.setItem(21, getAttackNationButton());
        inventory.setItem(23, getDefendNationButton());
        inventory.setItem(24, getDefendNationsButton());
        inventory.setItem(48, GeneralButtons.getBackButton());
        inventory.setItem(45, GeneralButtons.getCloseButton());
        return inventory;
    }
}
