package com.tuenkle.earthintimeplugin.listeners;

import com.tuenkle.earthintimeplugin.EarthInTimePlugin;
import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.database.War;
import com.tuenkle.earthintimeplugin.dynmap.NationDynmap;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import com.tuenkle.earthintimeplugin.gui.buttons.NationButtons;
import com.tuenkle.earthintimeplugin.gui.nation.*;
import com.tuenkle.earthintimeplugin.gui.war.WarInfoGui;
import com.tuenkle.earthintimeplugin.scheduler.ParticlesScheduler;
import com.tuenkle.earthintimeplugin.utils.GeneralUtils;
import com.tuenkle.earthintimeplugin.utils.NationUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

import static com.tuenkle.earthintimeplugin.utils.NationUtils.*;

public class NationGuiListener implements Listener {

    @EventHandler
    public void onGuiClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        if (inventory == null) {
            return;
        }
        if (inventory.getType().equals(InventoryType.PLAYER)) {
            return;
        }
        final ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null) {
            return;
        }
        if (event.getWhoClicked() instanceof Player player) {
            if (clickedItem.equals(GeneralButtons.getCloseButton())) {
                event.setCancelled(true);
                player.closeInventory();
                return;
            }
            if (inventory.getHolder() instanceof NationMainGui) {
                event.setCancelled(true);
                int clickedSlot = event.getRawSlot();
                switch (clickedSlot) {
                    case NationMainGui.INFO_SLOT -> { // 나라 정보
                        User user = Database.users.get(player.getUniqueId());
                        Nation nation = user.getNation();
                        if (nation == null) {
                            player.closeInventory();
                            player.sendMessage(ChatColor.YELLOW + "소속된 나라가 없습니다.", ChatColor.YELLOW + "/나라 만들기 <나라이름>", ChatColor.YELLOW + "10분을 소모하여 나라를 생성합니다.");
                            return;
                        }
                        NationInfoGui nationInfoGui = new NationInfoGui(nation, user, "main");
                        player.openInventory(nationInfoGui.getInventory());
                    }
                    case NationMainGui.LIST_SLOT -> { // 나라 목록
                        User user = Database.users.get(player.getUniqueId());
                        player.openInventory(new NationListGui(user).getInventory());
                    }
                }
                return;
            }
            if (inventory.getHolder() instanceof NationInfoGui nationInfoGui) {
                event.setCancelled(true);
                int clickedSlot = event.getRawSlot();
                switch (clickedSlot) { // - 시민, 왕, 타민
                    case NationInfoGui.RESIDENTS_SLOT -> {
                        Nation nation = ((NationInfoGui) inventory.getHolder()).getNation();
                        if (!Database.nations.containsValue(nation)) {
                            player.closeInventory();
                            return;
                        }
                        player.openInventory(new NationResidentsGui(nation).getInventory());
                        return;
                    }
                    case NationInfoGui.ALLIES_SLOT -> {
                        Nation nation = ((NationInfoGui) inventory.getHolder()).getNation();
                        if (!Database.nations.containsValue(nation)) {
                            player.closeInventory();
                            return;
                        }
                        player.openInventory(new NationAlliesGui(nation).getInventory());
                        return;
                    }
                    case NationInfoGui.WARS_SLOT -> {
                        Nation nation = ((NationInfoGui) inventory.getHolder()).getNation();
                        if (!Database.nations.containsValue(nation)) {
                            player.closeInventory();
                            return;
                        }
                        player.openInventory(new NationWarsGui(nation).getInventory());
                        return;
                    }
                }
                if (clickedItem.equals(GeneralButtons.getBackButton())) {
                    User user = Database.users.get(player.getUniqueId());
                    if (((NationInfoGui) inventory.getHolder()).getCameFrom().equals("main")) {
                        player.openInventory(new NationMainGui().getInventory());
                        return;
                    }
                    if (((NationInfoGui) inventory.getHolder()).getCameFrom().equals("list")) {
                        player.openInventory(new NationListGui(user).getInventory());
                        return;
                    }
                    return;
                }
                if (clickedItem.equals(NationButtons.getSpawnButton())) {
                    User user = Database.users.get(player.getUniqueId());
                    Nation nation = user.getNation();
                    player.closeInventory();
                    player.teleport(nation.getSpawn());
                    player.sendMessage("나라 스폰으로 이동되었습니다.");
                    return;
                }
                if (clickedItem.equals(NationButtons.getDepositButton())) {
                    player.closeInventory();
                    player.sendMessage(ChatColor.YELLOW + "/나라 입금 <시간>");
                    return;
                }
                if (clickedItem.equals(NationButtons.getBorderVisualizationButton())) {
                    player.closeInventory();
                    User user = Database.users.get(player.getUniqueId());
                    Nation nation = user.getNation();
                    if (nation.isParticleOn()) {
                        player.sendMessage("이미 실행중입니다.");
                        return;
                    }
                    double particleY = player.getLocation().getY() + 2;
                    if (particleY > 315) {
                        player.sendMessage("플레이어의 위치가 너무 높습니다.");
                        return;
                    }
                    new ParticlesScheduler(player, nation).runTaskTimer(EarthInTimePlugin.getMainInstance(), 0, 20);
                    player.sendMessage("나라 청크의 경계를 표시합니다.(지속시간 20초)");
                    return;
                }
                if (clickedItem.equals(NationButtons.getDisbandButton())) {
                    player.closeInventory();
                    player.sendMessage(ChatColor.YELLOW + "/나라 삭제");
                    return;
                }
                if (clickedItem.equals(NationButtons.getWithdrawButton())) {
                    player.closeInventory();
                    player.sendMessage(ChatColor.YELLOW + "/나라 출금 <시간>");
                    return;
                }
                if (clickedItem.equals(NationButtons.getSetSpawnButton())) {
                    player.closeInventory();
                    User user = Database.users.get(player.getUniqueId());
                    Nation nation = user.getNation();
                    if (!isChunkInNation(player.getLocation().getChunk(), nation)){
                        player.sendMessage("본인 나라 안에 있지 않습니다.");
                        return;
                    }
                    nation.setSpawn(player.getLocation());
                    player.sendMessage("플레이어의 위치를 나라 스폰으로 설정하였습니다.");
                    return;
                }
                if (clickedItem.equals(NationButtons.getExpandButton())) {
                    player.closeInventory();
                    User user = Database.users.get(player.getUniqueId());
                    Nation nation = user.getNation();
                    long requiredMoney = getNationExpandMoney(nation);
                    if (nation.getMoney() < requiredMoney) {
                        player.sendMessage("나라 잔고가 부족합니다. 필요 잔고: " + GeneralUtils.secondToUniversalTime(requiredMoney));
                        return;
                    }
                    Chunk playerChunk = player.getLocation().getChunk();
                    int[] chunk = {playerChunk.getX(), playerChunk.getZ()};
                    player.sendMessage(nation.expand(chunk));
                    return;
                }
                if (clickedItem.equals(NationButtons.getShrinkButton())) {
                    player.closeInventory();
                    User user = Database.users.get(player.getUniqueId());
                    Nation nation = user.getNation();
                    int[] chunk = {player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ()};
                    player.sendMessage(nation.shrink(chunk));
                    return;
                }
                if (clickedItem.equals(NationButtons.getInviteButton())) {
                    User user = Database.users.get(player.getUniqueId());
                    Nation nation = user.getNation();
                    player.openInventory(new NationInviteGui(nation).getInventory());
                    return;
                }
                String clickedItemDisplayName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
                if (clickedItemDisplayName.equals("전쟁 선포")) {
                    player.closeInventory();
                    player.sendMessage("/전쟁 선포 <나라이름>");
                    return;
                }
                if (clickedItemDisplayName.equals("전쟁 중")) {
                    User user = Database.users.get(player.getUniqueId());
                    Nation userNation = user.getNation();
                    Nation targetNation = nationInfoGui.getNation();
                    War war = Database.getWar(userNation, targetNation);
                    if (war == null) {
                        player.openInventory(new NationInfoGui(targetNation, user, "main").getInventory());
                        return;
                    }
                    player.openInventory(new WarInfoGui(war).getInventory());
                    return;
                }
                //TODO 버튼 늘어남에 따라 추가
            }
            if (inventory.getHolder() instanceof NationListGui) {
                event.setCancelled(true);
                if (clickedItem.equals(GeneralButtons.getBackButton())) {
                    player.openInventory(new NationMainGui().getInventory());
                    return;
                }
                if (clickedItem.getType().equals(Material.GREEN_WOOL)) {
                    if (clickedItem.getItemMeta().hasLore()) {
                        String nationName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
                        Nation nation = Database.nations.get(nationName);
                        if (nation == null) {
                            return;
                        }
                        player.openInventory(new NationInfoGui(nation, Database.users.get(player.getUniqueId()), "list").getInventory());
                        return;
                    }
                    return;
                }
                return;
            }
            if (inventory.getHolder() instanceof NationResidentsGui) {
                event.setCancelled(true);
                if (clickedItem.equals(GeneralButtons.getCloseButton())) {
                    player.closeInventory();
                    return;
                }
                if (clickedItem.equals(GeneralButtons.getBackButton())) {
                    Nation nation = ((NationResidentsGui) inventory.getHolder()).getNation();
                    if (!Database.nations.containsValue(nation)) {
                        player.closeInventory();
                        return;
                    }
                    User user = Database.users.get(player.getUniqueId());
                    player.openInventory(new NationInfoGui(nation, user, "main").getInventory());
                    return;
                }
                return;
            }
            if (inventory.getHolder() instanceof NationAlliesGui) {
                event.setCancelled(true);
                if (clickedItem.equals(GeneralButtons.getCloseButton())) {
                    player.closeInventory();
                    return;
                }
                if (clickedItem.equals(GeneralButtons.getBackButton())) {
                    Nation nation = ((NationAlliesGui) inventory.getHolder()).getNation();
                    if (!Database.nations.containsValue(nation)) {
                        player.closeInventory();
                        return;
                    }
                    User user = Database.users.get(player.getUniqueId());
                    player.openInventory(new NationInfoGui(nation, user, "main").getInventory());
                    return;
                }
                //TODO-동맹인 나라 누르면 그 나라 info로 바로 가게
                return;
            }
            if (inventory.getHolder() instanceof NationWarsGui) {
                event.setCancelled(true);
                if (clickedItem.equals(GeneralButtons.getCloseButton())) {
                    player.closeInventory();
                    return;
                }
                if (clickedItem.equals(GeneralButtons.getBackButton())) {
                    Nation nation = ((NationWarsGui) inventory.getHolder()).getNation();
                    if (!Database.nations.containsValue(nation)) {
                        player.closeInventory();
                        return;
                    }
                    User user = Database.users.get(player.getUniqueId());
                    player.openInventory(new NationInfoGui(nation, user, "main").getInventory());
                    return;
                }
                //TODO-전쟁 누르면 그 전쟁 info로 바로 가게
                return;
            }
            if (inventory.getHolder() instanceof NationInviteGui) {
                event.setCancelled(true);
                if (clickedItem.equals(GeneralButtons.getCloseButton())) {
                    player.closeInventory();
                    return;
                }
                if (clickedItem.equals(GeneralButtons.getBackButton())) {
                    Nation nation = ((NationInviteGui) inventory.getHolder()).getNation();
                    if (!Database.nations.containsValue(nation)) {
                        player.closeInventory();
                        return;
                    }
                    User user = Database.users.get(player.getUniqueId());
                    player.openInventory(new NationInfoGui(nation, user, "main").getInventory());
                    return;
                }
                if (clickedItem.equals(NationButtons.getInviteRequestButton())) {
                    player.closeInventory();
                    player.sendMessage(ChatColor.YELLOW + "/나라 초대 <유저이름>");
                    return;
                }
                ItemMeta clickedItemMeta = clickedItem.getItemMeta();
                if (clickedItemMeta != null) {
                    if (clickedItemMeta.hasLore()) {
                        List<String> lores = clickedItemMeta.getLore();
                        if (lores == null || lores.size() != 3) {
                            return;
                        }
                        UUID userUuid = UUID.fromString(ChatColor.stripColor(lores.get(2)));
                        User user = Database.users.get(userUuid);
                        Nation nation = Database.nations.get(ChatColor.stripColor(inventory.getItem(4).getItemMeta().getDisplayName()));
                        if (nation == null) {
                            player.closeInventory();
                            return;
                        }
                        nation.removeInvite(user);
                        player.openInventory(new NationInviteGui(nation).getInventory());
                        player.sendMessage(ChatColor.GREEN + user.getName() + " 초대 취소 완료");
                        return;
                    }
                }
                return;
            }
        }
    }
}