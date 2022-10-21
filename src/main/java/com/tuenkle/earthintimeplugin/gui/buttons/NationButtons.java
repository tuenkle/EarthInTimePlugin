package com.tuenkle.earthintimeplugin.gui.buttons;
import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.database.War;
import com.tuenkle.earthintimeplugin.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class NationButtons {
    private static ItemStack getNationMyInfoButton;
    private static ItemStack nationListButton;

    public static ItemStack getDisbandButton() {
        return disbandButton;
    }

    public static ItemStack getInviteButton() {
        return inviteButton;
    }

    public static ItemStack getSetSpawnButton() {
        return setSpawnButton;
    }

    public static ItemStack getAlliesButton() {
        return alliesButton;
    }

    public static ItemStack getResidentsButton() {
        return residentsButton;
    }

    public static ItemStack getWarsButton() {
        return warsButton;
    }

    public static ItemStack getExpandButton() {
        return expandButton;
    }

    public static ItemStack getShrinkButton() {
        return shrinkButton;
    }

    public static ItemStack getWithdrawButton() {
        return withdrawButton;
    }

    public static ItemStack getLeaveButton() {
        return leaveButton;
    }

    public static ItemStack getSpawnButton() {
        return spawnButton;
    }

    public static ItemStack getBorderVisualizationButton() {
        return borderVisualizationButton;
    }

    public static ItemStack getDepositButton() {
        return depositButton;
    }



    private static ItemStack disbandButton;
    private static ItemStack inviteButton;
    private static ItemStack setSpawnButton;
    private static ItemStack alliesButton;
    private static ItemStack residentsButton;
    private static ItemStack warsButton;
    private static ItemStack expandButton;
    private static ItemStack shrinkButton;
    private static ItemStack withdrawButton;
    private static ItemStack leaveButton;
    private static ItemStack spawnButton;
    private static ItemStack borderVisualizationButton;
    private static ItemStack depositButton;
    private static ItemStack inviteRequestButton;


    //    private static ItemStack attackButton;
//    private static ItemStack allyButton;
    public static void makeAll() {
        makeNationListButton();
        makeNationInfoButton();
        makeAlliesButton();
        makeDisbandButton();
        makeDepositButton();
        makeExpandButton();
        makeBorderVisualizationButton();
        makeLeaveButton();
        makeResidentsButton();
        makeSetSpawnButton();
        makeShrinkButton();
        makeSpawnButton();
        makeWarsButton();
        makeWithdrawButton();
        makeInviteButton();
        makeInviteRequestButton();
//        makeAttackButton();
//        makeAllyButton();
    }
    //    public static void makeAttackButton() {
//        ItemStack button = new ItemStack(Material.DIAMOND_SWORD);
//        ItemMeta buttonMeta = button.getItemMeta();
//        buttonMeta.setDisplayName(ChatColor.GREEN + "전쟁 개시");
//        ArrayList<String> lores = new ArrayList<>();
//        lores.add(ChatColor.GRAY + "왕만 사용 가능합니다!");
//        buttonMeta.setLore(lores);
//        button.setItemMeta(buttonMeta);
//        attackButton = button;
//    }
//    public static void makeAllyButton() {
//        ItemStack button = new ItemStack(Material.SHIELD);
//        ItemMeta buttonMeta = button.getItemMeta();
//        buttonMeta.setDisplayName(ChatColor.GREEN + "동맹 신청");
//        ArrayList<String> lores = new ArrayList<>();
//        lores.add(ChatColor.GRAY + "왕만 사용 가능합니다!");
//        buttonMeta.setLore(lores);
//        button.setItemMeta(buttonMeta);
//        allyButton = button;
//    }
    public static void makeInviteRequestButton() {
        ItemStack button = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "유저 초대");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "유저를 초대하려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        inviteRequestButton = button;
    }
    public static void makeNationInfoButton() {
        ItemStack button = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "나라 정보");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "소속된 나라의 정보를 봅니다!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        getNationMyInfoButton = button;
    }
    public static void makeNationListButton() {
        ItemStack button = new ItemStack(Material.BOOKSHELF);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "나라 목록");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "나라 목록을 봅니다!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        nationListButton = button;
    }
    public static void makeDisbandButton() {
        ItemStack button = new ItemStack(Material.MAGENTA_DYE);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "나라 해산");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "나라를 해산하려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        disbandButton = button;
    }
    public static void makeInviteButton() {
        ItemStack button = new ItemStack(Material.LIGHT_BLUE_DYE);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "초대 관리");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "초대 목록을 관리하려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        inviteButton = button;
    }
    public static void makeSetSpawnButton() {
        ItemStack button = new ItemStack(Material.LIME_DYE);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "나라 스폰 설정");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "현재 위치를 나라 스폰으로 설정하려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        setSpawnButton =  button;
    }
    public static void makeExpandButton() {
        ItemStack button = new ItemStack(Material.PINK_DYE);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "나라 확장");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "현재 청크로 나라를 확장하려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        expandButton = button;
    }
    public static void makeShrinkButton() {
        ItemStack button = new ItemStack(Material.GRAY_DYE);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "나라 축소");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "현재 청크를 나라에서 제거하려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        shrinkButton = button;
    }
    public static void makeWithdrawButton() {
        ItemStack button = new ItemStack(Material.PURPLE_DYE);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "나라 잔고 출금");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "나라 잔고에서 시간을 출금하려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        withdrawButton = button;
    }
    public static void makeLeaveButton() {
        ItemStack button = new ItemStack(Material.GREEN_WOOL);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "나라 떠나기");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "나라를 떠나려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        leaveButton = button;
    }
    public static void makeSpawnButton() {
        ItemStack button = new ItemStack(Material.ENDER_PEARL);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "나라 스폰");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "나라 스폰으로 가려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        spawnButton = button;
    }

    public static void makeDepositButton() {
        ItemStack button = new ItemStack(Material.IRON_INGOT);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "나라 잔고 입금");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "나라 잔고로 시간을 입금하려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        depositButton = button;
    }
    public static void makeBorderVisualizationButton() {
        ItemStack button = new ItemStack(Material.COMPASS);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "나라 경계 확인");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "나라 경계를 시각화하려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        borderVisualizationButton = button;
    }
    public static void makeAlliesButton() {
        ItemStack button = new ItemStack(Material.LIME_BANNER);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "동맹 정보");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "나라의 동맹 정보를 보려면 클릭하세요");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        alliesButton = button;
    }
    public static void makeWarsButton() {
        ItemStack button = new ItemStack(Material.RED_BANNER);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "전쟁 정보");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "나라의 전쟁 정보를 보려면 클릭하세요");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        warsButton = button;
    }
    public static ItemStack getNationNameButton(String nationName) {
        ItemStack button = new ItemStack(Material.GREEN_WOOL);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + nationName);
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "더 자세한 정보를 보려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        return button;
    }
    public static ItemStack getNationNameOnlyButton(String nationName) {
        ItemStack button = new ItemStack(Material.GREEN_WOOL);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + nationName);
        button.setItemMeta(buttonMeta);
        return button;
    }
    public static ItemStack getAttackButton(Nation nation, Nation targetNation, String relation) {
        ItemStack button = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta buttonMeta = button.getItemMeta();
        ArrayList<String> lores = new ArrayList<>();
        switch (relation) {
            case "전쟁" -> {
                buttonMeta.setDisplayName(ChatColor.GREEN + "전쟁 정보");
                lores.add(String.format("%s%s - %s: %s",
                        ChatColor.GRAY,
                        nation.getName(),
                        targetNation.getName(),
                        relation));
            }
            case "동맹" -> {
                buttonMeta.setDisplayName(ChatColor.GREEN + "전쟁 불가");
                lores.add(String.format("%s%s - %s: %s",
                        ChatColor.GRAY,
                        nation.getName(),
                        targetNation.getName(),
                        relation));
            }
            case "중립" -> {
                buttonMeta.setDisplayName(ChatColor.GREEN + "전쟁 선포");
                lores.add(String.format("%s%s - %s: %s",
                        ChatColor.GRAY,
                        nation.getName(),
                        targetNation.getName(),
                        relation));
            }
        }
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        return button;
    }
    public static ItemStack getAllyButton(Nation nation, Nation targetNation, String relation) {//relation must be 전쟁, 동맹, 중립
        ItemStack button = new ItemStack(Material.SHIELD);
        ItemMeta buttonMeta = button.getItemMeta();
        ArrayList<String> lores = new ArrayList<>();
        switch (relation) {
            case "전쟁" -> {
                buttonMeta.setDisplayName(ChatColor.GREEN + "동맹 불가");
                lores.add(String.format("%s%s - %s: %s",
                        ChatColor.GRAY,
                        nation.getName(),
                        targetNation.getName(),
                        relation));
            }
            case "동맹" -> {
                buttonMeta.setDisplayName(ChatColor.GREEN + "동맹 파기");
                lores.add(String.format("%s%s - %s: %s",
                        ChatColor.GRAY,
                        nation.getName(),
                        targetNation.getName(),
                        relation)); //미국 - 한국 관계: 동맹
            }
            case "중립" -> {
                buttonMeta.setDisplayName(ChatColor.GREEN + "동맹 신청");
                lores.add(String.format("%s%s - %s: %s",
                        ChatColor.GRAY,
                        nation.getName(),
                        targetNation.getName(),
                        relation));
            }
        }
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        return button;
    }
    public static void makeResidentsButton() {
        ItemStack button = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "구성원 목록");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "나라 구성원 목록을 보려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        residentsButton = button;
    }
    public static ItemStack getMoneyButton(Nation nation) {
        ItemStack button = new ItemStack(Material.GOLD_INGOT);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "국고");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(GeneralUtils.secondToUniversalTime(nation.getMoney()));
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        return button;
    }
    public static ItemStack getUserButton(User user, LocalDateTime registeredTime, boolean isKing) {
        ItemStack button = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + user.getName());
        ArrayList<String> lores = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (isKing) {
            lores.add(ChatColor.RED + "왕");
        } else {
            lores.add(ChatColor.RED + "시민");
        }
        lores.add(ChatColor.GRAY + "가입일: " + registeredTime.format(formatter));
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        return button;
    }
    public static ItemStack getWarInfoButton(War war) {
        ItemStack button = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + war.getAttackNation().getName() + " -> " + war.getDefendNation().getName());
        ArrayList<String> lores = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        lores.add(ChatColor.GRAY + "전쟁시작시간: " + war.getDefendStartTime().format(formatter));
        lores.add(ChatColor.GRAY + "더 자세한 정보를 보려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        return button;
    }
    public static ItemStack getInvitedUserButton(User user, LocalDateTime registeredTime) {
        ItemStack button = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + user.getName());
        ArrayList<String> lores = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        lores.add(ChatColor.GRAY + "초대 시간: " + registeredTime.format(formatter));
        lores.add(ChatColor.GRAY + "초대를 취소하려면 클릭하세요!");
        lores.add(ChatColor.GRAY + user.getUuid().toString());
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        return button;
    }
    public static ItemStack getAllyInfoButton(Nation nation, LocalDateTime registeredTime) {
        ItemStack button = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + nation.getName());
        ArrayList<String> lores = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        lores.add(ChatColor.GRAY + "동맹일: " + registeredTime.format(formatter));
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        return button;
    }
    public static ItemStack getNationMyInfoButton() {
        return getNationMyInfoButton;
    }
    public static ItemStack getNationListButton() {
        return nationListButton;
    }
    public static ItemStack getInviteRequestButton() {
        return inviteRequestButton;
    }
}
