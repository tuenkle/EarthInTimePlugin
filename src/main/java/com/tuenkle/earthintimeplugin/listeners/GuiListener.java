package com.tuenkle.earthintimeplugin.listeners;

import com.tuenkle.earthintimeplugin.EarthInTimePlugin;
import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.database.War;
import com.tuenkle.earthintimeplugin.gui.Gui;
import com.tuenkle.earthintimeplugin.gui.MainGui;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import com.tuenkle.earthintimeplugin.gui.buttons.NationButtons;
import com.tuenkle.earthintimeplugin.gui.nation.*;
import com.tuenkle.earthintimeplugin.gui.war.*;
import com.tuenkle.earthintimeplugin.scheduler.ParticlesScheduler;
import com.tuenkle.earthintimeplugin.utils.GeneralUtils;
import com.tuenkle.earthintimeplugin.utils.GuiUtils;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

import static com.tuenkle.earthintimeplugin.utils.NationUtils.isChunkInNation;

public class GuiListener implements Listener {
    @EventHandler
    public void onGuiClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if (!(inventory.getHolder() instanceof Gui gui)) {
            return;
        }
        User user = gui.getUser();
        if (user.isGuiMoving) {
            return;
        }
        user.resetGuiList();
    }
    @EventHandler
    public void onNationGuiClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        if (inventory == null) {
            return;
        }

        if (!(inventory.getHolder() instanceof Gui gui)) {
            return;
        }
        event.setCancelled(true);
        if (event.getRawSlot() >= gui.getSize()) {
            return;
        }
        final ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        if (clickedItem.equals(GeneralButtons.getCloseButton())) {
            player.closeInventory();
            return;
        }
        User user = gui.getUser();
        if (clickedItem.equals(GeneralButtons.getBackButton())) {
            GuiUtils.backGui(user, player);
            return;
        }
        if (clickedItem.equals(GeneralButtons.mainMenuButton)) {
            GuiUtils.moveToMainGui(new MainGui(user), user, player);
            return;
        }
        if (gui instanceof MainGui mainGui) {
            if (clickedItem.equals(GeneralButtons.nationMenuButton)) {
                GuiUtils.moveGui(mainGui, new NationMainGui(user), user ,player);
                return;
            }
            if (clickedItem.equals(GeneralButtons.warMenuButton)) {
                GuiUtils.moveGui(mainGui, new WarMainGui(user), user ,player);
                return;
            }
        }
        if (gui instanceof NationMainGui nationMainGui) {
            Nation userNation = user.getNation();
            if (clickedItem.equals(NationButtons.getNationMyInfoButton())) {
                if (userNation == null) {
                    player.closeInventory();
                    player.sendMessage(ChatColor.YELLOW + "나라가 존재하지 않습니다.",
                            ChatColor.YELLOW + "/나라 만들기 <나라이름>",
                            ChatColor.YELLOW + "/나라 초대 수락 <나라이름>");
                    return;
                }
                if (!(Database.nations.containsKey(userNation.getName()))) {
                    player.closeInventory();
                    return;
                }
                GuiUtils.moveGui(nationMainGui, new NationInfoGui(userNation, user), user, player);
                return;
            }
            if (clickedItem.equals(NationButtons.getNationListButton())) {
                GuiUtils.moveGui(nationMainGui, new NationListGui(userNation, user), user, player);
                return;
            }
        }
        if (gui instanceof NationInfoGui nationInfoGui) {
            Nation guiNation = nationInfoGui.getNation();
            if (guiNation != null && !Database.nations.containsKey(guiNation.getName())) {
                player.closeInventory();
                return;
            }
            if (guiNation == null) {
                player.closeInventory();
                player.sendMessage(ChatColor.YELLOW + "나라가 존재하지 않습니다.",
                        ChatColor.YELLOW + "/나라 만들기 <나라이름>",
                        ChatColor.YELLOW + "/나라 초대 수락 <나라이름>");
                return;
            }
            if (clickedItem.equals(NationButtons.getResidentsButton())) {
                GuiUtils.moveGui(nationInfoGui, new NationResidentsGui(guiNation, user), user, player);
                return;
            }
            if (clickedItem.equals(NationButtons.getAlliesButton())) {
                GuiUtils.moveGui(nationInfoGui, new NationAlliesGui(guiNation, user), user, player);
                return;
            }
            if (clickedItem.equals(NationButtons.getWarsButton())) {
                GuiUtils.moveGui(nationInfoGui, new NationWarsGui(guiNation, user), user, player);
                return;
            }
            //이 위로는 공용 버튼
            if (user.getNation() != guiNation){
                return;
            }
            //이 아래로는 시민, 왕 버튼
            if (clickedItem.equals(NationButtons.getSpawnButton())) {
                player.closeInventory();
                player.teleport(guiNation.getSpawn());
                player.sendMessage("나라 스폰으로 이동되었습니다.");
                return;
            }
            if (clickedItem.equals(NationButtons.getDepositButton())) {
                player.closeInventory();
                player.sendMessage(ChatColor.YELLOW + "/나라 입금 <시간>");//TODO NationDepositGui
                return;
            }
            if (clickedItem.equals(NationButtons.getBorderVisualizationButton())) {
                player.closeInventory();
                if (guiNation.isParticleOn()) {
                    player.sendMessage("이미 실행중입니다.");
                    return;
                }
                double particleY = player.getLocation().getY() + 2;
                if (particleY > 315) {
                    player.sendMessage("플레이어의 위치가 너무 높습니다.");
                    return;
                }
                new ParticlesScheduler(player, guiNation).runTaskTimer(EarthInTimePlugin.getMainInstance(), 0, 20);
                player.sendMessage("나라 청크의 경계를 표시합니다.(지속시간 20초)");
                return;
            }
            if (guiNation.isUserKing(user)){
                return;
            }
            //이 아래로는 왕 버튼
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
                if (!isChunkInNation(player.getLocation().getChunk(), guiNation)){
                    player.sendMessage("본인 나라 안에 있지 않습니다.");
                    return;
                }
                guiNation.setSpawn(player.getLocation());
                player.sendMessage("플레이어의 위치를 나라 스폰으로 설정하였습니다.");
                return;
            }
            if (clickedItem.equals(NationButtons.getExpandButton())) {
                player.closeInventory();
                long requiredMoney = guiNation.getExpandRequireMoney();
                if (guiNation.getMoney() < requiredMoney) {
                    player.sendMessage("나라 잔고가 부족합니다. 필요 잔고: " + GeneralUtils.secondToUniversalTime(requiredMoney));
                    return;
                }
                Chunk playerChunk = player.getLocation().getChunk();
                int[] chunk = {playerChunk.getX(), playerChunk.getZ()};
                player.sendMessage(guiNation.expand(chunk));
                return;
            }
            if (clickedItem.equals(NationButtons.getShrinkButton())) {
                player.closeInventory();
                int[] chunk = {player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ()};
                player.sendMessage(guiNation.shrink(chunk));
                return;
            }
            if (clickedItem.equals(NationButtons.getInviteButton())) {
                GuiUtils.moveGui(nationInfoGui, new NationInviteGui(guiNation, user), user, player);
                return;
            }
            ItemMeta clickedItemMeta = clickedItem.getItemMeta();
            if (clickedItemMeta == null) {
                return;
            }
            String clickedItemDisplayName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
            if (clickedItemDisplayName.equals("전쟁 선포")) {
                player.closeInventory();
                player.sendMessage(ChatColor.YELLOW + "/나라 전쟁 선포 <나라이름>");
                return;
            }
            if (clickedItemDisplayName.equals("전쟁 중")) {
                Nation userNation = user.getNation();
                Nation targetNation = nationInfoGui.getNation();
                War war = Database.getWar(userNation, targetNation);
                if (war == null) {
                    GuiUtils.reopenGui(nationInfoGui, user, player);
                    return;
                }
                GuiUtils.moveGui(nationInfoGui, new WarInfoGui(war, user), user, player);
                return;
            }
        }
        if (gui instanceof NationInviteGui nationInviteGui) {
            Nation guiNation = nationInviteGui.getNation();
            if (guiNation != null && !Database.nations.containsKey(guiNation.getName())) {
                player.closeInventory();
                return;
            }
            if (guiNation == null) {
                player.closeInventory();
                player.sendMessage(ChatColor.YELLOW + "나라가 존재하지 않습니다.",
                        ChatColor.YELLOW + "/나라 만들기 <나라이름>",
                        ChatColor.YELLOW + "/나라 초대 수락 <나라이름>");
                return;
            }
            if (!(guiNation.isUserKing(user))) {
                player.closeInventory();
                return;
            }
            ItemMeta clickedItemMeta = clickedItem.getItemMeta();
            if (clickedItemMeta == null) {
                return;
            }
            List<String> clickedItemMetaLore = clickedItemMeta.getLore();
            if (clickedItemMetaLore == null || clickedItemMetaLore.size() != 3) {
                return;
            }
            UUID invitedUserUuid = UUID.fromString(ChatColor.stripColor(clickedItemMetaLore.get(2)));
            User invitedUser = Database.users.get(invitedUserUuid);
            guiNation.removeInvite(invitedUser);
            GuiUtils.reopenGui(nationInviteGui, user, player);

            return;
        }
        if (gui instanceof NationListGui nationListGui) {
            ItemMeta clickedItemMeta = clickedItem.getItemMeta();
            if (clickedItemMeta == null) {
                return;
            }
            String targetNationName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
            Nation targetNation = Database.nations.get(targetNationName);
            if (targetNation == null) {
                return;
            }
            GuiUtils.moveGui(nationListGui, new NationInfoGui(targetNation, user), user, player);
            return;
        }
        if (gui instanceof NationResidentsGui) {
            return;//TODO-눌렀을때 유저 gui로(미정)
        }
        if (gui instanceof NationAlliesGui nationAlliesGui) {
            Nation guiNation = nationAlliesGui.getNation(); //user.getNation()이 아닌 gui에서 nation을 얻었을 경우 꼭 검증
            if (guiNation != null && !Database.nations.containsKey(guiNation.getName())) {
                player.closeInventory();
                return;
            }
            if (guiNation == null) {
                player.closeInventory();
                player.sendMessage(ChatColor.YELLOW + "나라가 존재하지 않습니다.",
                        ChatColor.YELLOW + "/나라 만들기 <나라이름>",
                        ChatColor.YELLOW + "/나라 초대 수락 <나라이름>");
                return;
            }
            ItemMeta clickedItemMeta = clickedItem.getItemMeta();
            if (clickedItemMeta == null) {
                return;
            }
            String targetNationName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
            Nation targetNation = Database.nations.get(targetNationName);
            if (targetNation == null) {
                return;
            }
            GuiUtils.moveGui(nationAlliesGui, new NationInfoGui(targetNation, user), user, player);
            return;
        }
        if (gui instanceof NationWarsGui nationWarsGui) {
            Nation guiNation = nationWarsGui.getNation();
            if (guiNation != null && !Database.nations.containsKey(guiNation.getName())) {
                player.closeInventory();
                return;
            }
            if (guiNation == null) {
                player.closeInventory();
                player.sendMessage(ChatColor.YELLOW + "나라가 존재하지 않습니다.",
                        ChatColor.YELLOW + "/나라 만들기 <나라이름>",
                        ChatColor.YELLOW + "/나라 초대 수락 <나라이름>");
                return;
            }
            ItemMeta clickedItemMeta = clickedItem.getItemMeta();
            if (clickedItemMeta == null) {
                return;
            }
            String clickedItemDisplayName = ChatColor.stripColor(clickedItemMeta.getDisplayName());
            String[] clickedItemDisplayNameSplited = clickedItemDisplayName.split(" -> ");
            War war = Database.getWar(Database.nations.get(clickedItemDisplayNameSplited[0]), Database.nations.get(clickedItemDisplayNameSplited[1]));
            if (war == null) {
                GuiUtils.reopenGui(nationWarsGui, user, player);

                return;
            }
            GuiUtils.moveGui(nationWarsGui, new WarInfoGui(war, user), user, player);
            return;
        }
        if (gui instanceof WarMainGui warMainGui) {
            if (clickedItem.equals(warMainGui.warMyInfoButton)) {
                Nation userNation = user.getNation();
                if (userNation == null) {
                    player.closeInventory();
                    player.sendMessage(ChatColor.YELLOW + "나라가 존재하지 않습니다.",
                            ChatColor.YELLOW + "/나라 만들기 <나라이름>",
                            ChatColor.YELLOW + "/나라 초대 수락 <나라이름>");
                    return;
                }
                GuiUtils.moveGui(warMainGui, new NationWarsGui(userNation, user), user, player);
                return;
            }
            if (clickedItem.equals(warMainGui.warListButton)) {
                GuiUtils.moveGui(warMainGui, new WarListGui(user), user, player);
                return;
            }
        }
        if (gui instanceof WarListGui warListGui) {
            ItemMeta clickedItemMeta = clickedItem.getItemMeta();
            if (clickedItemMeta == null) {
                return;
            }
            String clickedItemDisplayName = ChatColor.stripColor(clickedItemMeta.getDisplayName());
            String[] clickedItemDisplayNameSplited = clickedItemDisplayName.split(" -> ");
            if (clickedItemDisplayNameSplited.length != 2) {
                return;
            }
            Nation attackNation = Database.nations.get(clickedItemDisplayNameSplited[0]);
            Nation defendNation = Database.nations.get(clickedItemDisplayNameSplited[1]);
            if (attackNation == null || defendNation == null) {
                return;
            }
            War war = Database.getWar(Database.nations.get(clickedItemDisplayNameSplited[0]), Database.nations.get(clickedItemDisplayNameSplited[1]));
            if (war == null) {
                GuiUtils.reopenGui(warListGui, user, player);
                return;
            }
            GuiUtils.moveGui(warListGui, new WarInfoGui(war, user), user, player);
            return;
        }
        if (gui instanceof WarInfoGui warInfoGui) {
            War war = warInfoGui.getWar();
            if (war == null || !Database.wars.contains(war)) {
                player.closeInventory();
                return;
            }
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
            if (clickedItem.equals(GeneralButtons.attackJoinButton)) {
                Nation userNation = user.getNation();
                if (userNation != null && userNation.isUserKing(user) && Database.nations.containsKey(userNation.getName())) {
                    war.attackJoinApplicationNations.add(userNation);
                    GuiUtils.moveGui(warInfoGui, new WarAttackJoinGui(war, user), user, player);
                    return;
                }
                return;
            }
            if (clickedItem.equals(GeneralButtons.defendJoinButton)) {
                Nation nation = user.getNation();
                if (nation != null && nation.isUserKing(user) && Database.nations.containsKey(nation.getName())) {
                    war.defendJoinApplicationNations.add(user.getNation());
                    GuiUtils.moveGui(warInfoGui, new WarDefendJoinGui(war, user), user, player);
                    return;
                }
                return;
            }
            if (clickedItem.equals(GeneralButtons.attackJoinInfoButton)) {
                Nation userNation = user.getNation();
                if (userNation != null && userNation.isUserKing(user) && Database.nations.containsKey(userNation.getName())) {
                    GuiUtils.moveGui(warInfoGui, new WarAttackJoinGui(war, user), user, player);
                    return;
                }
                return;
            }
            if (clickedItem.equals(GeneralButtons.defendJoinInfoButton)) {
                Nation userNation = user.getNation();
                if (userNation != null && userNation.isUserKing(user) && Database.nations.containsKey(userNation.getName())) {
                    GuiUtils.moveGui(warInfoGui, new WarDefendJoinGui(war, user), user, player);
                    return;
                }
                return;
            }
            return;
        }
        if (gui instanceof WarAttackJoinGui warAttackJoinGui) {
            ItemMeta clickedItemMeta = clickedItem.getItemMeta();
            if (clickedItemMeta == null) {
                return;
            }
            Nation userNation = user.getNation();
            if (userNation != null && userNation.isUserKing(user) && Database.nations.containsKey(userNation.getName())) {
                String clickedItemDisplayName = ChatColor.stripColor(clickedItemMeta.getDisplayName());
                if (clickedItemDisplayName.equals(userNation.getName())) {
                    warAttackJoinGui.getWar().attackJoinApplicationNations.remove(userNation);
                    GuiUtils.reopenGui(warAttackJoinGui, user, player);
                    return;
                }
            }
            return;
        }
        if (gui instanceof WarDefendJoinGui warDefendJoinGui) {
            ItemMeta clickedItemMeta = clickedItem.getItemMeta();
            if (clickedItemMeta == null) {
                return;
            }
            Nation userNation = user.getNation();
            if (userNation != null && userNation.isUserKing(user) && Database.nations.containsKey(userNation.getName())) {
                String clickedItemDisplayName = ChatColor.stripColor(clickedItemMeta.getDisplayName());
                if (clickedItemDisplayName.equals(userNation.getName())) {
                    warDefendJoinGui.getWar().defendJoinApplicationNations.remove(userNation);
                    GuiUtils.reopenGui(warDefendJoinGui, user, player);
                    return;
                }
            }
            return;
        }
        if (gui instanceof WarAttackNationsGui warAttackNationsGui) {
            War war = warAttackNationsGui.getWar();
            if (war == null || !Database.wars.contains(war)) {
                player.closeInventory();
                return;
            }
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
        if (gui instanceof WarDefendNationsGui warDefendNationsGui) {
            War war = warDefendNationsGui.getWar();
            if (war == null || !Database.wars.contains(war)) {
                player.closeInventory();
                return;
            }
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
        if (gui instanceof WarAttackJoinGui warAttackJoinGui) {
            War war = warAttackJoinGui.getWar();
            if (war == null || !Database.wars.contains(war)) {
                player.closeInventory();
                return;
            }
        }
        if (gui instanceof WarDefendJoinGui warDefendJoinGui) {
            War war = warDefendJoinGui.getWar();
            if (war == null || !Database.wars.contains(war)) {
                player.closeInventory();
                return;
            }
        }
    }

}
