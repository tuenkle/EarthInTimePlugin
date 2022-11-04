package com.tuenkle.earthintimeplugin.gui.war;

import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.database.War;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class WarRecoveryGui extends WarGui {
    public int page = 1;
    public WarRecoveryGui(War war, User user) {
        super(war, user);
    }
    @Override
    public Inventory getInventory() {
        Nation userNation = user.getNation();
        Inventory inventory = Bukkit.createInventory(this, 54, "전쟁 복구 메뉴");
        setDefaultInventory(inventory);
        inventory.setItem(4, getButton(Material.GREEN_WOOL , ChatColor.GREEN + userNation.getName()));
        if (war.getDefendNation() == userNation) {
            int i = 0;
            int currentItemPage = 1;
            for (Map.Entry<Material, long[] > recoveryRequireMaterial : war.defendRecoveryRequireMaterials.entrySet()) {
                if (i <= 45) {
                    if (currentItemPage == page) {
                        long[] amount = recoveryRequireMaterial.getValue();
                        inventory.setItem(i + 9, getButtonWithLoresWithoutName(new ItemStack(recoveryRequireMaterial.getKey()), ChatColor.GREEN + Long.toString(amount[0]) + "/" + amount[1]));
                    }
                    i++;
                } else if (currentItemPage != page) {
                    currentItemPage++;
                    i = 0;
                } else {
                    break;
                }
            }
            if (war.defendRecoveryRequireMaterials.size() / 45 > page - 1) {
                inventory.setItem(53, GeneralButtons.getNextPageButton());
            }
            if (page > 1) {
                inventory.setItem(46, GeneralButtons.previousPageButton);
            }
        } else if (war.getAttackNations().contains(userNation)) {
            int i = 0;
            int currentItemPage = 1;
            HashMap<Material, long[]> attackRecoveryRequireMaterial = war.attacksRecoveryRequireMaterials.get(userNation);
            for (Map.Entry<Material, long[] > recoveryRequireMaterial : attackRecoveryRequireMaterial.entrySet()) {
                if (i <= 45) {
                    if (currentItemPage == page) {
                        long[] amount = recoveryRequireMaterial.getValue();
                        inventory.setItem(i + 9, getButtonWithLoresWithoutName(new ItemStack(recoveryRequireMaterial.getKey()), ChatColor.GREEN + Long.toString(amount[0]) + "/" + amount[1]));
                    }
                    i++;
                } else if (currentItemPage != page) {
                    currentItemPage++;
                    i = 0;
                } else {
                    break;
                }
            }
            if (attackRecoveryRequireMaterial.size() / 45 > page - 1) {
                inventory.setItem(46, GeneralButtons.getNextPageButton());
            }
            if (page > 1) {
                inventory.setItem(53, GeneralButtons.previousPageButton);
            }
        }
        return inventory;
    }
}
