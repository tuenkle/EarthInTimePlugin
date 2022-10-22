package com.tuenkle.earthintimeplugin.listeners;

import com.tuenkle.earthintimeplugin.gui.nation.NationMainGui;
import com.tuenkle.earthintimeplugin.gui.war.WarAttackNationsGui;
import com.tuenkle.earthintimeplugin.gui.war.WarDefendNationsGui;
import com.tuenkle.earthintimeplugin.gui.war.WarGui;
import com.tuenkle.earthintimeplugin.gui.war.WarInfoGui;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WarGuiListener implements Listener {

    @EventHandler
    public void onWarGuiClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        if (inventory == null) {
            return;
        }
        if (!(inventory.getHolder() instanceof WarGui)) {
            return;
        }
        WarGui warGui = (WarGui) inventory.getHolder();
        event.setCancelled(true);
        final ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null) {
            return;
        }
//        final ItemMeta clickedItemMeta = clickedItem.getItemMeta();
//        if (clickedItemMeta == null) {
//            return;
//        }
        HumanEntity player = event.getWhoClicked();
        if (inventory.getHolder() instanceof WarInfoGui warInfoGui) {
//            String clickedItemDisplayName = clickedItemMeta.getDisplayName();
            if (clickedItem.equals(warInfoGui.getAttackNationButton())) {
                return;
            }
            if (clickedItem.equals(warInfoGui.getAttackNationsButton())) {
                player.openInventory(new WarAttackNationsGui(warInfoGui.getWar()).getInventory());
                return;
            }
            if (clickedItem.equals(warInfoGui.getDefendNationButton())) {
                return;
            }
            if (clickedItem.equals(warInfoGui.getDefendNationsButton())) {
                player.openInventory(new WarDefendNationsGui(warInfoGui.getWar()).getInventory());
                return;
            }
            return;
        }
    }
}
