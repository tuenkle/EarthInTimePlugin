package com.tuenkle.earthintimeplugin.gui.buttons;
import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDateTime;
import java.util.*;
public class NationButtons {
    private static ItemStack getNationMyInfoButton;
    private static ItemStack nationListButton;
    private static ItemStack chunksButton;
    //    private static ItemStack attackButton;
//    private static ItemStack allyButton;
    public static void makeAll() {
        makeNationListButton();
        makeNationInfoButton();
        makeChunksButton();
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
    public static void makeChunksButton() {
        ItemStack button = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "나라 청크 확인");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "나라 청크를 확인합니다!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        chunksButton = button;
    }
    public static ItemStack getDisbandButton() {
        ItemStack button = new ItemStack(Material.GREEN_WOOL);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "나라 해산");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "나라를 해산하려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        return button;
    }
    public static ItemStack getInviteButton() {
        ItemStack button = new ItemStack(Material.GREEN_WOOL);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "초대 관리");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "초대 목록을 관리하려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        return button;
    }
    public static ItemStack getSetSpawnButton() {
        ItemStack button = new ItemStack(Material.GREEN_WOOL);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "나라 스폰 설정");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "현재 위치를 나라 스폰으로 설정하려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        return button;
    }
    public static ItemStack getExpandButton() {
        ItemStack button = new ItemStack(Material.GREEN_WOOL);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "나라 확장");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "현재 청크로 나라를 확장하려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        return button;
    }
    public static ItemStack getShrinkButton() {
        ItemStack button = new ItemStack(Material.GREEN_WOOL);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "나라 축소");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "현재 청크를 나라에서 제거하려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        return button;
    }
    public static ItemStack getWithdrawButton() {
        ItemStack button = new ItemStack(Material.GREEN_WOOL);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "나라 잔고 출금");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "나라 잔고에서 시간을 출금하려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        return button;
    }
    public static ItemStack getLeaveButton() {
        ItemStack button = new ItemStack(Material.GREEN_WOOL);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "나라 떠나기");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "나라를 떠나려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        return button;
    }
    public static ItemStack getSpawnButton() {
        ItemStack button = new ItemStack(Material.GREEN_WOOL);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "나라 스폰");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "나라 스폰으로 가려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        return button;
    }

    public static ItemStack getDepositButton() {
        ItemStack button = new ItemStack(Material.GREEN_WOOL);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "나라 잔고 입금");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "나라 잔고로 시간을 입금하려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        return button;
    }
    public static ItemStack getBorderVisualizationButton() {
        ItemStack button = new ItemStack(Material.GREEN_WOOL);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "나라 경계 확인");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "나라 경계를 시각화하려면 클릭하세요!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        return button;
    }
    public static ItemStack getAlliesButton() {
        ItemStack button = new ItemStack(Material.GREEN_WOOL);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "동맹 정보");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "나라의 동맹 정보를 보려면 클릭하세요");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        return button;
    }
    public static ItemStack getWarsButton() {
        ItemStack button = new ItemStack(Material.GREEN_WOOL);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "전쟁 정보");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "나라의 전쟁 정보를 보려면 클릭하세요");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        return button;
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
    public static ItemStack getResidentsButton(Nation nation) {
        String kingName = nation.getKing().getName();
        ItemStack button = new ItemStack(Material.BOOK);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "왕: " + kingName);
        ArrayList<String> lores = new ArrayList<>();
        for (Map.Entry<User, LocalDateTime> resident : nation.getResidents().entrySet()) {
            lores.add(ChatColor.GRAY + "시민: " + resident.getKey().getName() + "가입 날짜: " + resident.getValue());
        }
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        return button;
    }
    public static ItemStack getMoneyButton(Nation nation) {
        ItemStack button = new ItemStack(Material.GOLD_INGOT);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "국고: " + GeneralUtils.secondToUniversalTime(nation.getMoney()));
        button.setItemMeta(buttonMeta);
        return button;
    }
    public static ItemStack getNationMyInfoButton() {
        return getNationMyInfoButton;
    }
    public static ItemStack getNationListButton() {
        return nationListButton;
    }
    public static ItemStack getChunksButton() {
        return chunksButton;
    }
}
