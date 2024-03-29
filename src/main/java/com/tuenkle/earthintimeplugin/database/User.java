package com.tuenkle.earthintimeplugin.database;

import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

public class User {
    private double money = 600000000L;
    private int language = 0;
    private String name;
    private UUID uuid;
    private Nation nation;
    private ArrayList<InventoryHolder> guiList = new ArrayList<>();
    public boolean isGuiMoving = false;
    public InventoryHolder popLastGui() {
        if (guiList.size() == 0) {
            return null;
        }
        return guiList.remove(guiList.size() - 1);
    }
    public InventoryHolder getLastGui() {
        if (guiList.size() == 0) {
            return null;
        }
        return guiList.get(guiList.size() - 1);
    }
    public boolean hasLastGui() {
        return guiList.size() != 0;
    }
    public void addLastGui(InventoryHolder gui) {
        guiList.add(gui);
    }
    public void resetGuiList() {
        guiList.clear();
    }
    public HashSet<int[]> getSafeChunk() {
        return nation.getChunks();
    }
    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Nation getNation() {
        return nation;
    }
    public void setNation(Nation nation) {
        this.nation = nation;
    }
    public void withdrawMoney(double amount) {
        money = money - amount;
    }
    public void depositMoney(double amount) {
        money = money + amount;
    }

    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
}
