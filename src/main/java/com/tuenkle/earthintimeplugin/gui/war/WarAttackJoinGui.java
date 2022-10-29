package com.tuenkle.earthintimeplugin.gui.war;

import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.database.War;
import com.tuenkle.earthintimeplugin.gui.buttons.NationButtons;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class WarAttackJoinGui extends WarGui {
    public WarAttackJoinGui(War war, User user) {
        super(war, user);
    }
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, "전쟁 공격국 신청 내역");
        setDefaultInventory(inventory);
        inventory.setItem(4, getWarTitleButton());
        if (war.attackJoinApplicationNations.contains(user.getNation())) {
            inventory.setItem(22, getButtonWithLores(Material.GREEN_WOOL, ChatColor.GREEN + user.getNation().getName(), ChatColor.GRAY + "클릭하여 취소하세요!"));
        }
        return inventory;
    }
}
