package com.tuenkle.earthintimeplugin.listeners;

import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.database.War;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import com.tuenkle.earthintimeplugin.gui.nation.NationInfoGui;
import com.tuenkle.earthintimeplugin.gui.nation.NationMainGui;
import com.tuenkle.earthintimeplugin.gui.war.WarAttackNationsGui;
import com.tuenkle.earthintimeplugin.gui.war.WarDefendNationsGui;
import com.tuenkle.earthintimeplugin.gui.war.WarGui;
import com.tuenkle.earthintimeplugin.gui.war.WarInfoGui;
import com.tuenkle.earthintimeplugin.utils.GuiUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        if (!(inventory.getHolder() instanceof WarGui warGui)) {
            return;
        }
        event.setCancelled(true);
        final ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        User user = warGui.getUser();
        if (clickedItem.equals(GeneralButtons.getBackButton())) {
            GuiUtils.backGui(user, player);
            return;
        }
        War war = warGui.getWar();
        if (war == null || war.isRemoved) {
            player.closeInventory();
            return;
        }
        if (inventory.getHolder() instanceof WarInfoGui warInfoGui) {
            if (clickedItem.equals(warInfoGui.getAttackNationButton())) {
                return;
            }
            if (clickedItem.equals(warInfoGui.getAttackNationsButton())) {
                GuiUtils.moveGui(warInfoGui, new WarAttackNationsGui(war, user), user, player);
                return;
            }
            if (clickedItem.equals(warInfoGui.getDefendNationButton())) {
                return;
            }
            if (clickedItem.equals(warInfoGui.getDefendNationsButton())) {
                GuiUtils.moveGui(warInfoGui, new WarDefendNationsGui(war, user), user, player);
                return;
            }
            return;
        }
        if (inventory.getHolder() instanceof WarAttackNationsGui warAttackNationsGui) {
            ItemMeta clickedItemMeta = clickedItem.getItemMeta();
            if (clickedItemMeta == null) {
                return;
            }
            String clickedItemDisplayName = ChatColor.stripColor(clickedItemMeta.getDisplayName());
            Nation nation = Database.nations.get(clickedItemDisplayName);
            if (nation == null) {
                return;
            }
            GuiUtils.moveGui(warAttackNationsGui, new NationInfoGui(nation, user), user, player);
            return;
        }
        if (inventory.getHolder() instanceof WarDefendNationsGui warDefendNationsGui) {
            ItemMeta clickedItemMeta = clickedItem.getItemMeta();
            if (clickedItemMeta == null) {
                return;
            }
            String clickedItemDisplayName = ChatColor.stripColor(clickedItemMeta.getDisplayName());
            Nation nation = Database.nations.get(clickedItemDisplayName);
            if (nation == null) {
                return;
            }
            GuiUtils.moveGui(warDefendNationsGui, new NationInfoGui(nation, user), user, player);
            return;
        }
    }
}
