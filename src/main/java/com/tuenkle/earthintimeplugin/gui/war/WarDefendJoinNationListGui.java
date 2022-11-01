package com.tuenkle.earthintimeplugin.gui.war;

import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.database.War;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class WarDefendJoinNationListGui extends WarGui{
    public WarDefendJoinNationListGui(War war, User user) {
        super(war, user);
    }
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, "전쟁 수비 신청국 목록");
        setDefaultInventory(inventory);
        inventory.setItem(4, getWarTitleButton());
        int i = 9; //TODO-다음페이지
        for (Nation nation : war.defendJoinApplicationNations) {
            inventory.setItem(i + 9, getButtonWithLores(Material.GREEN_WOOL, ChatColor.GREEN + nation.getName(), ChatColor.GRAY + "클릭하여 수락하세요!"));
            i++;
        }
        return inventory;
    }
}
