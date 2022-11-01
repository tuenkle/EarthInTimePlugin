package com.tuenkle.earthintimeplugin.gui.buttons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class GeneralButtons {
    private static ItemStack backButton;
    private static ItemStack closeButton;
    private static ItemStack dummyButton;
    private static ItemStack nextPageButton;
    public static ItemStack mainMenuButton;
    public static ItemStack warMenuButton;
    public static ItemStack nationMenuButton;
    public static ItemStack attackJoinButton;
    public static ItemStack defendJoinButton;
    public static ItemStack attackJoinInfoButton;
    public static ItemStack defendJoinInfoButton;
    public static ItemStack attackJoinNationListButton;
    public static ItemStack defendJoinNationListButton;

    public static ItemStack getBackButton() {
        return backButton;
    }
    public static ItemStack getCloseButton() {
        return closeButton;
    }
    public static ItemStack getDummyButton() {
        return dummyButton;
    }
    public static ItemStack getNextPageButton() {
        return nextPageButton;
    }
    public static ItemStack getButton(Material material, String name) {
        ItemStack button = new ItemStack(material);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(name);
        button.setItemMeta(buttonMeta);
        return button;
    }
    public static ItemStack getButtonWithLores(Material material, String name, String ...lores) {
        ItemStack button = new ItemStack(material);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(name);
        buttonMeta.setLore(Arrays.stream(lores).toList());
        button.setItemMeta(buttonMeta);
        return button;
    }
    public static void makeAll() {
        makeBackButton();
        makeCloseButton();
        makeDummyButton();
        makeNextPageButton();
        makeMainMenuButton();
        makeWarMenuButton();
        makeNationMenuButton();
        makeAttackJoinButton();
        makeDefendJoinButton();
        makeAttackJoinInfoButton();
        makeDefendJoinInfoButton();
        makeAttackJoinNationListButton();
        makeDefendJoinNationListButton();
    }
    public static void makeAttackJoinNationListButton() {
        attackJoinNationListButton = getButtonWithLores(Material.DIAMOND_SWORD, ChatColor.GREEN + "공격국 참가 신청국 목록", ChatColor.GRAY + "공격국 참가 신청국 목록을 보려면 클릭하세요!");
    }
    public static void makeDefendJoinNationListButton() {
        defendJoinNationListButton = getButtonWithLores(Material.DIAMOND_SWORD, ChatColor.GREEN + "수비국 참가 신청국 목록", ChatColor.GRAY + "수비국 참가 신청국 목록을 보려면 클릭하세요!");

    }
    public static void makeAttackJoinInfoButton() {
        attackJoinInfoButton = getButtonWithLores(Material.DIAMOND_SWORD, ChatColor.GREEN + "공격국 연합 참가 신청 내역", ChatColor.GRAY + "공격국 연합 참가 신청 내역을 보려면 클릭하세요!");
    }
    public static void makeDefendJoinInfoButton() {
        defendJoinInfoButton = getButtonWithLores(Material.SHIELD, ChatColor.GREEN + "수비국 연합 참가 신청 내역", ChatColor.GRAY + "수비국 연합 참가 신청 내역을 보려면 클릭하세요!");
    }
    public static void makeAttackJoinButton() {
        attackJoinButton =  getButtonWithLores(Material.DIAMOND_SWORD, ChatColor.GREEN + "공격국 연합 참가 신청", ChatColor.GRAY + "공격국 연합에 참가 신청하려면 클릭하세요!");
    }
    public static void makeDefendJoinButton() {
        defendJoinButton =  getButtonWithLores(Material.SHIELD, ChatColor.GREEN + "수비국 연합 참가 신청", ChatColor.GRAY + "수비국 연합에 참가 신청하려면 클릭하세요!");

    }
    public static void makeNationMenuButton() {
        nationMenuButton = getButtonWithLores(Material.GREEN_WOOL, ChatColor.GREEN + "나라 메뉴로 가기", ChatColor.GRAY + "나라 메뉴로 가려면 클릭하세요!");
    }
    public static void makeWarMenuButton() {
        warMenuButton = getButtonWithLores(Material.IRON_SWORD, ChatColor.GREEN + "전쟁 메뉴로 가기", ChatColor.GRAY + "전쟁 메뉴로 가려면 클릭하세요!");
    }
    public static void makeMainMenuButton() {
        mainMenuButton = getButtonWithLores(Material.COMPASS, ChatColor.GREEN + "메인 메뉴로 가기", ChatColor.GRAY + "메인 메뉴로 가려면 클릭하세요!");
    }
    public static void makeNextPageButton() {
        ItemStack button = new ItemStack(Material.ARROW);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "다음 페이지");
        button.setItemMeta(buttonMeta);
        nextPageButton = button;
    }
    public static void makeBackButton() {
        ItemStack button = new ItemStack(Material.ARROW);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "뒤로 가기");
        button.setItemMeta(buttonMeta);
        backButton = button;
    }
    public static void makeCloseButton() {
        ItemStack button = new ItemStack(Material.BARRIER);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(ChatColor.GREEN + "GUI 나가기");
        ArrayList<String> lores = new ArrayList<>();
        lores.add(ChatColor.GRAY + "GUI를 나갑니다!");
        buttonMeta.setLore(lores);
        button.setItemMeta(buttonMeta);
        closeButton = button;
    }
    public static void makeDummyButton() {
        ItemStack button = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta buttonMeta = button.getItemMeta();
        buttonMeta.setDisplayName(" ");
        button.setItemMeta(buttonMeta);
        dummyButton = button;
    }
}
