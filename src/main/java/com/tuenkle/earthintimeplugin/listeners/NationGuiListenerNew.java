package com.tuenkle.earthintimeplugin.listeners;

import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import com.tuenkle.earthintimeplugin.gui.buttons.NationButtons;
import com.tuenkle.earthintimeplugin.gui.nation.*;
import com.tuenkle.earthintimeplugin.gui.war.WarAttackNationsGui;
import com.tuenkle.earthintimeplugin.gui.war.WarDefendNationsGui;
import com.tuenkle.earthintimeplugin.gui.war.WarGui;
import com.tuenkle.earthintimeplugin.gui.war.WarInfoGui;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

public class NationGuiListenerNew implements Listener {
    @EventHandler
    public void onNationGuiClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        if (inventory == null) {
            return;
        }
        if (!(inventory.getHolder() instanceof NationGui)) {
            return;
        }
        event.setCancelled(true);
        NationGui nationGui = (NationGui) inventory.getHolder();
        final ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null) {
            return;
        }
        HumanEntity player = event.getWhoClicked();
        if (clickedItem.equals(GeneralButtons.getCloseButton())) {
            player.closeInventory();
            return;
        }
        User user = nationGui.getUser();
        if (clickedItem.equals(GeneralButtons.getBackButton())) {
            InventoryHolder lastGui = user.getLastGui();
            if (lastGui == null) {
                player.closeInventory();
                return;
            }
            player.openInventory(user.getLastGui().getInventory());
            return;
        }

        Nation nation = nationGui.getNation();

        if (inventory.getHolder() instanceof NationMainGui nationMainGui) {
            if (clickedItem.equals(NationButtons.getNationMyInfoButton())) {
                user.addLastGui(nationMainGui);
                player.openInventory(new NationInfoGui(nation, user).getInventory());//TODO-만약 nation이 null이라면?
                return;
            }
            if (clickedItem.equals(NationButtons.getNationListButton())) {
                user.addLastGui(nationMainGui);
                player.openInventory(new NationListGui(nation, user).getInventory());
                return;
            }
        }
        if (inventory.getHolder() instanceof NationInviteGui nationInviteGui) {
            ItemMeta clickedItemMeta = clickedItem.getItemMeta();
            if (clickedItemMeta == null) {
                return;
            }
            List<String> clickedItemMetaLore = clickedItemMeta.getLore();
            if (clickedItemMetaLore == null || clickedItemMetaLore.size() != 3) {
                return;
            }
            UUID userUuid = UUID.fromString(ChatColor.stripColor(clickedItemMetaLore.get(2)));
            User invitedUser = Database.users.get(userUuid);
            user.addLastGui(nationInviteGui);
            player.openInventory(new NationInfoGui(nation, user).getInventory());//TODO-만약 초대 메뉴를 연 상태에서 나라가 사라진다면?, 만약 초대 메뉴를 연 상태에서 왕이 바뀐다면?
            return;
        }
    }
}
