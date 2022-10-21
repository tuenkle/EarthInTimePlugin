package com.tuenkle.earthintimeplugin.listeners;

import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.gui.NationGui;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import com.tuenkle.earthintimeplugin.gui.buttons.NationButtons;
import com.tuenkle.earthintimeplugin.utils.GeneralUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class NationGuiListener implements Listener {
    @EventHandler
    public void onGuiClick(InventoryClickEvent event) {
        switch (event.getView().getTitle()) {
            case "나라" -> {
                event.setCancelled(true);
                final ItemStack clickedItem = event.getCurrentItem();
                if (clickedItem == null || clickedItem.equals(GeneralButtons.getDummyButton())) {
                    return;
                }
                final Player player = (Player) event.getWhoClicked();
                if (clickedItem.equals(GeneralButtons.getCloseButton())) {
                    player.closeInventory();
                    return;
                }
                if (clickedItem.equals(NationButtons.getNationListButton())) {
                    player.openInventory(NationGui.getNationsList());
                    return;
                }
                if (clickedItem.equals(NationButtons.getNationMyInfoButton())) {
                    Nation nation = Database.users.get(player.getUniqueId()).getNation();
                    if (nation == null) {
                        player.closeInventory();
                        player.sendMessage(ChatColor.YELLOW + "소속된 나라가 없습니다.", ChatColor.YELLOW + "/나라 만들기 <나라이름>", ChatColor.YELLOW + "10분을 소모하여 나라를 생성합니다.");
                        return;
                    }
                    player.openInventory(NationGui.getNationInfo(nation, Database.users.get(player.getUniqueId())));
                    return;
                }
            }
            case "나라 목록" -> {
                event.setCancelled(true);
                final ItemStack clickedItem = event.getCurrentItem();
                if (clickedItem == null || clickedItem.equals(GeneralButtons.getDummyButton())) {
                    return;
                }
                final Player player = (Player) event.getWhoClicked();
                if (clickedItem.equals(GeneralButtons.getCloseButton())) {
                    player.closeInventory();
                    return;
                }
                if (clickedItem.equals(GeneralButtons.getBackButton())) {
                    player.openInventory(NationGui.getStart());
                    return;
                }
                if (clickedItem.getType().equals(Material.GREEN_WOOL)) {
                    if (clickedItem.getItemMeta().hasLore()) {
                        String nationName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
                        Nation nation = Database.nations.get(nationName);
                        if (nation == null) {
                            return;
                        }
                        player.openInventory(NationGui.getNationInfo(nation, Database.users.get(player.getUniqueId())));
                        return;
                    }
                }
            }
            case "나라 정보(본국)-왕" -> {
                event.setCancelled(true);
                final ItemStack clickedItem = event.getCurrentItem();

                if (clickedItem == null || clickedItem.equals(GeneralButtons.getDummyButton())) {
                    return;
                }

                final Player player = (Player) event.getWhoClicked();

                if (clickedItem.equals(GeneralButtons.getCloseButton())) {
                    player.closeInventory();
                    return;
                }
                if (clickedItem.equals(GeneralButtons.getBackButton())) {
                    player.openInventory(NationGui.getStart());
                    return;
                }
                //TODO 버튼 늘어남에 따라 추가
            }
            case "나라 정보(본국)-시민" -> {
                event.setCancelled(true);
                final ItemStack clickedItem = event.getCurrentItem();

                if (clickedItem == null || clickedItem.equals(GeneralButtons.getDummyButton())) {
                    return;
                }

                final Player player = (Player) event.getWhoClicked();

                if (clickedItem.equals(GeneralButtons.getCloseButton())) {
                    player.closeInventory();
                    return;
                }
                if (clickedItem.equals(GeneralButtons.getBackButton())) {
                    player.openInventory(NationGui.getStart());
                    return;
                }
                //TODO 버튼 늘어남에 따라 추가
            }
            case "나라 정보(타국)-왕" -> {
                event.setCancelled(true);
                final ItemStack clickedItem = event.getCurrentItem();

                if (clickedItem == null || clickedItem.equals(GeneralButtons.getDummyButton())) {
                    return;
                }

                final Player player = (Player) event.getWhoClicked();

                if (clickedItem.equals(GeneralButtons.getCloseButton())) {
                    player.closeInventory();
                    return;
                }
                if (clickedItem.equals(GeneralButtons.getBackButton())) {
                    player.openInventory(NationGui.getNationsList());
                    return;
                }
                //TODO 버튼 늘어남에 따라 추가
            }
            case "나라 정보(타국)-시민" -> {
                event.setCancelled(true);
                final ItemStack clickedItem = event.getCurrentItem();

                if (clickedItem == null || clickedItem.equals(GeneralButtons.getDummyButton())) {
                    return;
                }

                final Player player = (Player) event.getWhoClicked();

                if (clickedItem.equals(GeneralButtons.getCloseButton())) {
                    player.closeInventory();
                    return;
                }
                if (clickedItem.equals(GeneralButtons.getBackButton())) {
                    player.openInventory(NationGui.getNationsList());
                    return;
                }
                //TODO 버튼 늘어남에 따라 추가
            }
        }
    }
}
