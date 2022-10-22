package com.tuenkle.earthintimeplugin.database;

import java.util.HashSet;
import java.util.UUID;

public class User {
    private long money = 600000000L;
    private int language = 0;
    private String name;
    private UUID uuid;
    private Nation nation;
    public HashSet<int[]> getSafeChunk() {
        return nation.getChunks();
    }
    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
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
    public void withdrawMoney(long amount) {
        money = money - amount;
    }
    public void depositMoney(long amount) {
        money = money + amount;
    }
    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
}
