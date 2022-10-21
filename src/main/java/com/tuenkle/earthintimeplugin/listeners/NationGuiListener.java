package com.tuenkle.earthintimeplugin.listeners;

import com.tuenkle.earthintimeplugin.EarthInTimePlugin;
import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.dynmap.NationDynmap;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import com.tuenkle.earthintimeplugin.gui.buttons.NationButtons;
import com.tuenkle.earthintimeplugin.gui.nation.*;
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
            if (inventory.getHolder() instanceof NationInfoGui) {
                event.setCancelled(true);
                int clickedSlot = event.getRawSlot();
                switch (clickedSlot) { // - 시민, 왕, 타민
                    case NationInfoGui.RESIDENTS_SLOT -> {
                        User user = Database.users.get(player.getUniqueId());
                        Nation nation = user.getNation();
                        player.openInventory(new NationResidentsGui(nation).getInventory());
                        return;
                    }
                    case NationInfoGui.ALLIES_SLOT -> {
                        User user = Database.users.get(player.getUniqueId());
                        Nation nation = user.getNation();
                        player.openInventory(new NationAlliesGui(nation).getInventory());
                        return;
                    }
                    case NationInfoGui.WARS_SLOT -> {
                        User user = Database.users.get(player.getUniqueId());
                        Nation nation = user.getNation();
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
                    player.closeInventory();
                    User user = Database.users.get(player.getUniqueId());
                    Nation nation = user.getNation();
                    int[] chunk = {player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ()};
                    if (!isIntChunkInNation(chunk, nation)) {
                        player.sendMessage("본인 나라 안에 있지 않습니다.");
                        return;
                    }
                    //TODO-이상한 모양으로 축소 안되게
                    nation.removeChunk(chunk);
                    NationDynmap.eraseAndDrawNation(nation);
                    player.sendMessage("나라 축소 완료.");
                    return;
                }
                if (clickedItem.equals(NationButtons.getInviteButton())) {
                    User user = Database.users.get(player.getUniqueId());
                    Nation nation = user.getNation();
                    player.openInventory(new NationInviteGui(nation).getInventory());
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
                    Nation nation = Database.nations.get(ChatColor.stripColor(event.getInventory().getItem(4).getItemMeta().getDisplayName()));
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
                    Nation nation = Database.nations.get(ChatColor.stripColor(event.getInventory().getItem(4).getItemMeta().getDisplayName()));
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
                    Nation nation = Database.nations.get(ChatColor.stripColor(event.getInventory().getItem(4).getItemMeta().getDisplayName()));
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
                    Nation nation = Database.nations.get(ChatColor.stripColor(event.getInventory().getItem(4).getItemMeta().getDisplayName()));
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
                        Nation nation = Database.nations.get(ChatColor.stripColor(event.getInventory().getItem(4).getItemMeta().getDisplayName()));
                        if (nation == null) {
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
//
//
//
//
//        switch (event.getView().getTitle()) {
//            case "나라" -> {
//                event.setCancelled(true);
//                final ItemStack clickedItem = event.getCurrentItem();
//                if (clickedItem == null || clickedItem.equals(GeneralButtons.getDummyButton())) {
//                    return;
//                }
//                final Player player = (Player) event.getWhoClicked();
//                if (clickedItem.equals(GeneralButtons.getCloseButton())) {
//                    player.closeInventory();
//                    return;
//                }
//                if (clickedItem.equals(NationButtons.getNationListButton())) {
//                    player.openInventory(NationGui.getNationsList());
//                    return;
//                }
//                if (clickedItem.equals(NationButtons.getNationMyInfoButton())) {
//                    Nation nation = Database.users.get(player.getUniqueId()).getNation();
//                    if (nation == null) {
//                        player.closeInventory();
//                        player.sendMessage(ChatColor.YELLOW + "소속된 나라가 없습니다.", ChatColor.YELLOW + "/나라 만들기 <나라이름>", ChatColor.YELLOW + "10분을 소모하여 나라를 생성합니다.");
//                        return;
//                    }
//                    player.openInventory(NationGui.getNationInfo(nation, Database.users.get(player.getUniqueId())));
//                    return;
//                }
//            }
//            case "나라 목록" -> {
//                event.setCancelled(true);
//                final ItemStack clickedItem = event.getCurrentItem();
//                if (clickedItem == null || clickedItem.equals(GeneralButtons.getDummyButton())) {
//                    return;
//                }
//                final Player player = (Player) event.getWhoClicked();
//                if (clickedItem.equals(GeneralButtons.getCloseButton())) {
//                    player.closeInventory();
//                    return;
//                }
//                if (clickedItem.equals(GeneralButtons.getBackButton())) {
//                    player.openInventory(NationGui.getStart());
//                    return;
//                }
//                if (clickedItem.getType().equals(Material.GREEN_WOOL)) {
//                    if (clickedItem.getItemMeta().hasLore()) {
//                        String nationName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
//                        Nation nation = Database.nations.get(nationName);
//                        if (nation == null) {
//                            return;
//                        }
//                        player.openInventory(NationGui.getNationInfo(nation, Database.users.get(player.getUniqueId())));
//                        return;
//                    }
//                }
//            }
//            case "나라 정보(본국)-왕" -> {
//                event.setCancelled(true);
//                final ItemStack clickedItem = event.getCurrentItem();
//
//                if (clickedItem == null || clickedItem.equals(GeneralButtons.getDummyButton())) {
//                    return;
//                }
//
//                final Player player = (Player) event.getWhoClicked();
//
//                if (clickedItem.equals(GeneralButtons.getCloseButton())) {
//                    player.closeInventory();
//                    return;
//                }
//                if (clickedItem.equals(GeneralButtons.getBackButton())) {
//                    player.openInventory(NationGui.getStart());
//                    return;
//                }
//                Nation nation = Database.users.get(player.getUniqueId()).getNation();
//                if (nation == null) {
//                    player.closeInventory();
//                    return;
//                }
//                if (clickedItem.equals(NationButtons.getResidentsButton())) {
//                    player.openInventory(NationGui.getResidents(nation));
//                    return;
//                }
//                if (clickedItem.equals(NationButtons.getAlliesButton())) {
//                    player.openInventory(NationGui.getAllies(nation));
//                    return;
//                }
//                if (clickedItem.equals(NationButtons.getWarsButton())) {
//                    player.openInventory(NationGui.getWars(nation));
//                    return;
//                }
//                if (clickedItem.equals(NationButtons.getInviteButton())) {
//                    player.openInventory(NationGui.getInvite(nation));
//                    return;
//                }
//                if (clickedItem.equals(NationButtons.getSpawnButton())) {
//                    player.closeInventory();
//                    player.teleport(nation.getSpawn());
//                    player.sendMessage("나라 스폰으로 이동되었습니다.");
//                    return;
//                }
//                if (clickedItem.equals(NationButtons.getDepositButton())) {
//                    player.closeInventory();
//                    player.sendMessage(ChatColor.YELLOW + "/나라 입금 <시간>");
//                    return;
//                }
//                if (clickedItem.equals(NationButtons.getBorderVisualizationButton())) {
//                    player.closeInventory();
//                    if (nation.isParticleOn()) {
//                        player.sendMessage("이미 실행중입니다.");
//                        return;
//                    }
//                    double particleY = player.getLocation().getY() + 2;
//                    if (particleY > 315) {
//                        player.sendMessage("플레이어의 위치가 너무 높습니다.");
//                        return;
//                    }
//                    new ParticlesScheduler(player, nation).runTaskTimer(EarthInTimePlugin.getMainInstance(), 0, 20);
//                    player.sendMessage("나라 청크의 경계를 표시합니다.(지속시간 20초)");
//                    return;
//                }
//                if (clickedItem.equals(NationButtons.getDisbandButton())) {
//                    player.closeInventory();
//                    player.sendMessage(ChatColor.YELLOW + "/나라 삭제");
//                    return;
//                }
//                if (clickedItem.equals(NationButtons.getWithdrawButton())) {
//                    player.closeInventory();
//                    player.sendMessage(ChatColor.YELLOW + "/나라 출금 <시간>");
//                    return;
//                }
//                if (clickedItem.equals(NationButtons.getSetSpawnButton())) {
//                    player.closeInventory();
//                    nation.setSpawn(player.getLocation());
//                    player.sendMessage("플레이어의 위치를 나라 스폰으로 설정하였습니다.");
//                    return;
//                }
//                if (clickedItem.equals(NationButtons.getExpandButton())) {
//                    player.closeInventory();
//                    long requiredMoney = getNationExpandMoney(nation);
//                    if (nation.getMoney() < requiredMoney) {
//                        player.sendMessage("나라 잔고가 부족합니다. 필요 잔고: " + GeneralUtils.secondToUniversalTime(requiredMoney));
//                        return;
//                    }
//                    Chunk playerChunk = player.getLocation().getChunk();
//                    int[] chunk = {playerChunk.getX(), playerChunk.getZ()};
//                    if (NationUtils.isIntChunkInNations(chunk, Database.nations)) {
//                        player.sendMessage("나라 안에 있습니다.");
//                        return;
//                    }
//                    if (!NationUtils.isChunkNearChunks(playerChunk.getX(), playerChunk.getZ(), nation.getChunks())) {
//                        player.sendMessage("본인 나라에 근접한 청크가 아닙니다.");
//                        return;
//                    }
//                    //TODO-도넛모양 막기
//                    nation.withdrawMoney(requiredMoney);
//                    nation.addChunk(chunk);
//                    NationDynmap.eraseAndDrawNation(nation);
//                    player.sendMessage("나라 확장 완료. 소모된 시간: " + GeneralUtils.secondToUniversalTime(requiredMoney));
//                    return;
//                }
//                if (clickedItem.equals(NationButtons.getShrinkButton())) {
//                    player.closeInventory();
//                    int[] chunk = {player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ()};
//                    if (!isIntChunkInNation(chunk, nation)){
//                        player.sendMessage("본인 나라 안에 있지 않습니다.");
//                        return;
//                    }
//                    //TODO-이상한 모양으로 축소 안되게
//                    nation.removeChunk(chunk);
//                    NationDynmap.eraseAndDrawNation(nation);
//                    player.sendMessage("나라 축소 완료.");
//                    return;
//                }
//                //TODO 버튼 늘어남에 따라 추가
//            }
//            case "나라 정보(본국)-시민" -> {
//                event.setCancelled(true);
//                final ItemStack clickedItem = event.getCurrentItem();
//
//                if (clickedItem == null || clickedItem.equals(GeneralButtons.getDummyButton())) {
//                    return;
//                }
//
//                final Player player = (Player) event.getWhoClicked();
//
//                if (clickedItem.equals(GeneralButtons.getCloseButton())) {
//                    player.closeInventory();
//                    return;
//                }
//                if (clickedItem.equals(GeneralButtons.getBackButton())) {
//                    player.openInventory(NationGui.getStart());
//                    return;
//                }
//                //TODO 버튼 늘어남에 따라 추가
//            }
//            case "나라 정보(타국)-왕" -> {
//                event.setCancelled(true);
//                final ItemStack clickedItem = event.getCurrentItem();
//
//                if (clickedItem == null || clickedItem.equals(GeneralButtons.getDummyButton())) {
//                    return;
//                }
//
//                final Player player = (Player) event.getWhoClicked();
//
//                if (clickedItem.equals(GeneralButtons.getCloseButton())) {
//                    player.closeInventory();
//                    return;
//                }
//                if (clickedItem.equals(GeneralButtons.getBackButton())) {
//                    player.openInventory(NationGui.getNationsList());
//                    return;
//                }
//                Nation nation = Database.nations.get(ChatColor.stripColor(event.getInventory().getItem(4).getItemMeta().getDisplayName()));
//                User user = Database.users.get(player.getUniqueId());
//                if (!nation.getKing().equals(user)) {
//                    player.closeInventory();
//                    return;
//                }
//                if (event.getRawSlot() == 29) {
//                    String relation = Database.getRelation(nation, nation);
//                }
//                //TODO 버튼 늘어남에 따라 추가
//            }
//            case "나라 정보(타국)-시민" -> {
//                event.setCancelled(true);
//                final ItemStack clickedItem = event.getCurrentItem();
//
//                if (clickedItem == null || clickedItem.equals(GeneralButtons.getDummyButton())) {
//                    return;
//                }
//
//                final Player player = (Player) event.getWhoClicked();
//
//                if (clickedItem.equals(GeneralButtons.getCloseButton())) {
//                    player.closeInventory();
//                    return;
//                }
//                if (clickedItem.equals(GeneralButtons.getBackButton())) {
//                    player.openInventory(NationGui.getNationsList());
//                    return;
//                }
//                //TODO 버튼 늘어남에 따라 추가
//            }
//            case "나라 구성원 정보" -> {
//                event.setCancelled(true);
//                final ItemStack clickedItem = event.getCurrentItem();
//
//                if (clickedItem == null || clickedItem.equals(GeneralButtons.getDummyButton())) {
//                    return;
//                }
//
//                final Player player = (Player) event.getWhoClicked();
//
//                if (clickedItem.equals(GeneralButtons.getCloseButton())) {
//                    player.closeInventory();
//                    return;
//                }
//                if (clickedItem.equals(GeneralButtons.getBackButton())) {
//                    Nation nation = Database.nations.get(ChatColor.stripColor(event.getInventory().getItem(4).getItemMeta().getDisplayName()));
//                    User user = Database.users.get(player.getUniqueId());
//                    player.openInventory(NationGui.getNationInfo(nation, user));
//                    return;
//                }
//            }
//            case "나라 동맹 정보" -> {
//                event.setCancelled(true);
//                final ItemStack clickedItem = event.getCurrentItem();
//
//                if (clickedItem == null || clickedItem.equals(GeneralButtons.getDummyButton())) {
//                    return;
//                }
//                final Player player = (Player) event.getWhoClicked();
//
//                if (clickedItem.equals(GeneralButtons.getCloseButton())) {
//                    player.closeInventory();
//                    return;
//                }
//                if (clickedItem.equals(GeneralButtons.getBackButton())) {
//                    Nation nation = Database.nations.get(ChatColor.stripColor(event.getInventory().getItem(4).getItemMeta().getDisplayName()));
//                    User user = Database.users.get(player.getUniqueId());
//                    player.openInventory(NationGui.getNationInfo(nation, user));
//                    return;
//                }
//                //TODO-동맹인 나라 누르면 그 나라 info로 바로 가게
//            }
//            case "나라 전쟁 정보" -> {
//                event.setCancelled(true);
//                final ItemStack clickedItem = event.getCurrentItem();
//
//                if (clickedItem == null || clickedItem.equals(GeneralButtons.getDummyButton())) {
//                    return;
//                }
//                final Player player = (Player) event.getWhoClicked();
//
//                if (clickedItem.equals(GeneralButtons.getCloseButton())) {
//                    player.closeInventory();
//                    return;
//                }
//                if (clickedItem.equals(GeneralButtons.getBackButton())) {
//                    Nation nation = Database.nations.get(ChatColor.stripColor(event.getInventory().getItem(4).getItemMeta().getDisplayName()));
//                    User user = Database.users.get(player.getUniqueId());
//                    player.openInventory(NationGui.getNationInfo(nation, user));
//                    return;
//                }
//                //TODO-전쟁 누르면 그 전쟁 info로 바로 가게
//            }
//            case "나라 초대 정보" -> {
//                event.setCancelled(true);
//                final ItemStack clickedItem = event.getCurrentItem();
//
//                if (clickedItem == null || clickedItem.equals(GeneralButtons.getDummyButton())) {
//                    return;
//                }
//                final Player player = (Player) event.getWhoClicked();
//
//                if (clickedItem.equals(GeneralButtons.getCloseButton())) {
//                    player.closeInventory();
//                    return;
//                }
//                if (clickedItem.equals(GeneralButtons.getBackButton())) {
//                    Nation nation = Database.nations.get(ChatColor.stripColor(event.getInventory().getItem(4).getItemMeta().getDisplayName()));
//                    User user = Database.users.get(player.getUniqueId());
//                    player.openInventory(NationGui.getNationInfo(nation, user));
//                    return;
//                }
//                if (clickedItem.equals(NationButtons.getInviteRequestButton())) {
//                    player.closeInventory();
//                    player.sendMessage(ChatColor.YELLOW + "/나라 초대 <유저이름>");
//                    return;
//                }
//                ItemMeta clickedItemMeta = clickedItem.getItemMeta();
//                if (clickedItemMeta != null) {
//                    if (clickedItemMeta.hasLore()) {
//                        List<String> lores = clickedItemMeta.getLore();
//                        if (lores == null || lores.size() != 3) {
//                            return;
//                        }
//                        UUID userUuid = UUID.fromString(ChatColor.stripColor(lores.get(2)));
//                        User user = Database.users.get(userUuid);
//                        Nation nation = Database.nations.get(ChatColor.stripColor(event.getInventory().getItem(4).getItemMeta().getDisplayName()));
//                        if (nation == null) {
//                            return;
//                        }
//                        nation.removeInvite(user);
//                        player.openInventory(NationGui.getInvite(nation));
//                        player.sendMessage(ChatColor.GREEN + user.getName() + " 초대 취소 완료");
//                        return;
//                    }
//                }
//            }
//        }
//    }
//}
