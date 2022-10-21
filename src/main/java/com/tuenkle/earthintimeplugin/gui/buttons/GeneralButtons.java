package com.tuenkle.earthintimeplugin.gui.buttons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GeneralButtons {
    private static ItemStack backButton;
    private static ItemStack closeButton;
    private static ItemStack dummyButton;
    private static ItemStack nextPageButton;

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

    public static void makeAll() {
        makeBackButton();
        makeCloseButton();
        makeDummyButton();
        makeNextPageButton();
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
