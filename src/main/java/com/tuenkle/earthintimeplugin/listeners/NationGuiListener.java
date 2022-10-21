package com.tuenkle.earthintimeplugin.listeners;

import com.tuenkle.earthintimeplugin.EarthInTimePlugin;
import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.dynmap.NationDynmap;
import com.tuenkle.earthintimeplugin.gui.NationGui;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import com.tuenkle.earthintimeplugin.gui.buttons.NationButtons;
import com.tuenkle.earthintimeplugin.scheduler.ParticlesScheduler;
import com.tuenkle.earthintimeplugin.utils.GeneralUtils;
import com.tuenkle.earthintimeplugin.utils.NationUtils;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static com.tuenkle.earthintimeplugin.utils.NationUtils.getNationExpandMoney;
import static com.tuenkle.earthintimeplugin.utils.NationUtils.isIntChunkInNation;

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
                Nation nation = Database.users.get(player.getUniqueId()).getNation();
                if (nation == null) {
                    player.closeInventory();
                    return;
                }
                if (clickedItem.equals(NationButtons.getResidentsButton())) {
                    player.openInventory(NationGui.getResidents(nation));
                    return;
                }
                if (clickedItem.equals(NationButtons.getAlliesButton())) {
                    player.openInventory(NationGui.getAllies(nation));
                    return;
                }
                if (clickedItem.equals(NationButtons.getWarsButton())) {
                    player.openInventory(NationGui.getWars(nation));
                    return;
                }
                if (clickedItem.equals(NationButtons.getInviteButton())) {
                    player.openInventory(NationGui.getInvite(nation));
                    return;
                }
                if (clickedItem.equals(NationButtons.getSpawnButton())) {
                    player.teleport(nation.getSpawn());
                    return;
                }
                if (clickedItem.equals(NationButtons.getDepositButton())) {
                    player.sendMessage(ChatColor.YELLOW + "/나라 입금 <시간>");
                    return;
                }
                if (clickedItem.equals(NationButtons.getBorderVisualizationButton())) {
                    new ParticlesScheduler(player, nation).runTaskTimer(EarthInTimePlugin.getMainInstance(), 0, 20);
                    return;
                }
                if (clickedItem.equals(NationButtons.getDisbandButton())) {
                    player.sendMessage(ChatColor.YELLOW + "/나라 삭제");
                    return;
                }
                if (clickedItem.equals(NationButtons.getWithdrawButton())) {
                    player.sendMessage(ChatColor.YELLOW + "/나라 출금 <시간>");
                    return;
                }
                if (clickedItem.equals(NationButtons.getSetSpawnButton())) {
                    nation.setSpawn(player.getLocation());
                    return;
                }
                if (clickedItem.equals(NationButtons.getExpandButton())) {
                    long requiredMoney = getNationExpandMoney(nation);
                    if (nation.getMoney() < requiredMoney) {
                        player.sendMessage("나라 잔고가 부족합니다. 필요 잔고: " + GeneralUtils.secondToUniversalTime(requiredMoney));
                        return;
                    }
                    Chunk playerChunk = player.getLocation().getChunk();
                    int[] chunk = {playerChunk.getX(), playerChunk.getZ()};
                    if (NationUtils.isIntChunkInNations(chunk, Database.nations)) {
                        player.sendMessage("나라 안에 있습니다.");
                        return;
                    }
                    if (!NationUtils.isChunkNearChunks(playerChunk.getX(), playerChunk.getZ(), nation.getChunks())) {
                        player.sendMessage("본인 나라에 근접한 청크가 아닙니다.");
                        return;
                    }
                    //TODO-도넛모양 막기
                    nation.withdrawMoney(requiredMoney);
                    nation.addChunk(chunk);
                    NationDynmap.eraseAndDrawNation(nation);
                    player.sendMessage("나라 확장 완료. 소모된 시간: " + GeneralUtils.secondToUniversalTime(requiredMoney));
                    return;
                }
                if (clickedItem.equals(NationButtons.getShrinkButton())) {
                    int[] chunk = {player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ()};
                    if (!isIntChunkInNation(chunk, nation)){
                        player.sendMessage("본인 나라 안에 있지 않습니다.");
                        return;
                    }
                    //TODO-이상한 모양으로 축소 안되게
                    nation.removeChunk(chunk);
                    NationDynmap.eraseAndDrawNation(nation);
                    player.sendMessage("나라 축소 완료.");
                    return;
                }
//                if (clickedItem.getItemMeta().hasLore()) {
//                    String title = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
//                    switch (title) {
//                        case "국고" -> {
//                            if (clickedItem.getType().equals(Material.GOLD_INGOT)) {
//                                player.openInventory(NationGui.getNationInfo(nation, Database.users.get(player.getUniqueId())));
//                                return;
//                            }
//                        }
//                        default -> {
//                            return;
//                        }
//                    }
//                }
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
