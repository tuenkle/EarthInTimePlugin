package com.tuenkle.earthintimeplugin.database;

import com.tuenkle.earthintimeplugin.dynmap.NationDynmap;
import org.bukkit.Location;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class Nation {
    private User king;
    private HashMap<User, LocalDateTime> residents = new HashMap<>();
    private HashMap<User, LocalDateTime> invites = new HashMap<>();

    private LocalDateTime createdAt;
    private Location spawn;
    private String name;
    private long money = 600000L;
    private HashSet<int[]> chunks = new HashSet<>();
    private HashMap<Nation, LocalDateTime> allies = new HashMap<>();
    private HashMap<Nation, LocalDateTime> allyInvites = new HashMap<>();
    private boolean isParticleOn = false;
    private boolean isDuringWar = false;
    public HashMap<User, LocalDateTime> getInvites() {
        return invites;
    }

    public void setInvites(HashMap<User, LocalDateTime> invites) {
        this.invites = invites;
    }
    public void removeNationInAllyInvites(Nation nation) {
        allyInvites.remove(nation);
    }
    public void removeNationInAllies(Nation nation) {
        allies.remove(nation);
    }
    public User getKing() {
        return king;
    }

    public void setKing(User king) {
        this.king = king;
    }

    public HashMap<User, LocalDateTime> getResidents() {
        return residents;
    }

    public void setResidents(HashMap<User, LocalDateTime> residents) {
        this.residents = residents;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public HashSet<int[]> getChunks() {
        return chunks;
    }

    public void setChunks(HashSet<int[]> chunks) {
        this.chunks = chunks;
    }
    public HashMap<Nation, LocalDateTime> getAllies() {
        return allies;
    }

    public void setAllies(HashMap<Nation, LocalDateTime> allies) {
        this.allies = allies;
    }
    public HashMap<Nation, LocalDateTime> getAllyInvites() {
        return allyInvites;
    }

    public void setAllyInvites(HashMap<Nation, LocalDateTime> allyInvites) {
        this.allyInvites = allyInvites;
    }

    public boolean isParticleOn() {
        return isParticleOn;
    }

    public void setParticleOn(boolean particleOn) {
        isParticleOn = particleOn;
    }

    public boolean isDuringWar() {
        return isDuringWar;
    }

    public void setDuringWar(boolean duringWar) {
        isDuringWar = duringWar;
    }
    public void withdrawMoney(long amount) {
        money -= amount;
    }
    public void addChunk(int[] chunk){
        chunks.add(chunk);
    }
    public void depositMoney(long amount) {
        money += amount;
    }
    public void removeChunk(int[] targetChunk) {
        chunks.removeIf(chunk -> chunk[0] == targetChunk[0] && chunk[1] == targetChunk[1]);
    }




    public Nation(String name, User king, int[] chunk, Location spawn) {
        this.name = name;
        this.king = king;
        this.residents.put(king, LocalDateTime.now());
        this.chunks.add(chunk);
        this.createdAt = LocalDateTime.now();
        this.spawn = spawn;
    }
}
