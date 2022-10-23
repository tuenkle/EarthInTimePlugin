package com.tuenkle.earthintimeplugin.database;

import com.tuenkle.earthintimeplugin.dynmap.NationDynmap;
import com.tuenkle.earthintimeplugin.utils.GeneralUtils;
import com.tuenkle.earthintimeplugin.utils.NationUtils;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import static com.tuenkle.earthintimeplugin.utils.NationUtils.getNationExpandMoney;
import static com.tuenkle.earthintimeplugin.utils.NationUtils.isIntChunkInNation;

public class Nation {
    private User king;
    private HashMap<User, LocalDateTime> residents = new HashMap<>();
    private HashMap<User, LocalDateTime> invites = new HashMap<>();
    public boolean isRemoved = false;
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
    public void removeInvite(User user) {
        invites.remove(user);
    }

    public String shrink(int[] chunk) {
        if (chunks.size() == 1) {
            return "영토가 한 청크일 때는 축소할 수 없습니다.";
        }
        if (!isIntChunkInNation(chunk)) {
            return "나라 안에 있지 않습니다.";
        }
        //TODO-도넛 모양
        removeChunk(chunk);
        NationDynmap.eraseAndDrawNation(this);
        return "나라 축소 완료";
    }
    public String expand(int[] chunk) {
        long requiredMoney = getExpandRequireMoney();

        if (money < requiredMoney) {
            return "나라 잔고가 부족합니다. 필요 잔고: " + GeneralUtils.secondToUniversalTime(requiredMoney);
        }
        if (NationUtils.isIntChunkInNations(chunk)) {
            return "나라 안에 있습니다.";
        }
        if (!isIntChunkNearNation(chunk)) {
            return "본인 나라에 근접한 청크가 아닙니다.";
        }
        //TODO-도넛 모양
        withdrawMoney(requiredMoney);
        addChunk(chunk);
        NationDynmap.eraseAndDrawNation(this);
        return "나라 확장 완료";
    }
    public long getExpandRequireMoney() {
        return 600;
    }
    public boolean isIntChunkNearNation(int[] chunk) {
        for (int[] nationChunk : chunks) {
            if ((Math.abs(nationChunk[0] - chunk[0]) + Math.abs(nationChunk[1] - chunk[1])) == 1) {
                return true;
            }
        }
        return false;
    }
    public boolean isIntChunkInNation(int[] chunk) {
        for (int[] nationChunk : chunks) {
            if (nationChunk[0] == chunk[0] && nationChunk[1] == chunk[1]){
                return true;
            }
        }
        return false;
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
