package com.tuenkle.earthintimeplugin.database;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;

import java.time.LocalDateTime;
import java.util.*;

import static com.tuenkle.earthintimeplugin.EarthInTimePlugin.getWorld;

public class War {
    public boolean isScheduled() {
        return isScheduled;
    }

    public void setScheduled(boolean scheduled) {
        isScheduled = scheduled;
    }

    public HashSet<Nation> getAttackNations() {
        return attackNations;
    }

    public void setAttackNations(HashSet<Nation> attackNations) {
        this.attackNations = attackNations;
    }

    public HashSet<Nation> getDefendNations() {
        return defendNations;
    }

    public void setDefendNations(HashSet<Nation> defendNations) {
        this.defendNations = defendNations;
    }

    public Nation getAttackNation() {
        return attackNation;
    }

    public void setAttackNation(Nation attackNation) {
        this.attackNation = attackNation;
    }

    public Nation getDefendNation() {
        return defendNation;
    }

    public void setDefendNation(Nation defendNation) {
        this.defendNation = defendNation;
    }

    public LocalDateTime getPhase1Time() {
        return phase1Time;
    }

    public void setPhase1Time(LocalDateTime phase1Time) {
        this.phase1Time = phase1Time;
    }

    public LocalDateTime getPhase2Time() {
        return phase2Time;
    }
    public LocalDateTime getPhase3Time() {
        return phase3Time;
    }
    private LocalDateTime startTime;

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setPhase2Time(LocalDateTime phase2Time) {
        this.phase2Time = phase2Time;
    }
    public void terminateWar() {
        Database.wars.remove(this);
    }
    private boolean isScheduled = false;
    private HashSet<Nation> attackNations = new HashSet<>();
    private HashSet<Nation> defendNations = new HashSet<>();
    public HashSet<Nation> attackJoinApplicationNations = new HashSet<>();
    public HashSet<Nation> defendJoinApplicationNations = new HashSet<>();
    public boolean isRelated(Nation nation) {
        if (attackNations.contains(nation)) {
            return true;
        }
        if (attackJoinApplicationNations.contains(nation)) {
            return true;
        }
        if (defendNations.contains(nation)) {
            return true;
        }
        if (defendJoinApplicationNations.contains(nation)) {
            return true;
        }
        return false;
    }
    private Nation attackNation;
    private Nation defendNation;
    private LocalDateTime phase1Time;
    private LocalDateTime phase2Time;
    private LocalDateTime phase3Time;
    public boolean isPhase1Start = false;
    public boolean isPhase2Start = false;
    public boolean isPhase3Start = false;

    public HashMap<Nation, HashSet<ChunkSnapshot>> attackNationsSnapShot = new HashMap<>();
    public HashSet<ChunkSnapshot> defendNationSnapShot = new HashSet<>();
    public War (Nation attackNation, Nation defendNation, LocalDateTime startTime) {
        this.attackNations.add(attackNation);
        this.attackNation = attackNation;
        this.defendNations.add(defendNation);
        this.defendNation = defendNation;
        this.startTime = startTime;
        this.phase1Time = startTime.plusSeconds(20);
        this.phase2Time = startTime.plusSeconds(40);
        this.phase3Time = startTime.plusSeconds(20);
    }
    public boolean isAttackUser(User user) {
        for (Nation nation : attackNations) {
            if (nation.getResidents().containsKey(user)) {
                return true;
            }
        }
        return false;
    }
    public boolean isDefendUser(User user) {
        for (Nation nation : defendNations) {
            if (nation.getResidents().containsKey(user)) {
                return true;
            }
        }
        return false;
    }
    public void warStartIfAttackStartTimeIsAfterNow () {
        LocalDateTime now = LocalDateTime.now();
        if (!isPhase1Start) {
            if (phase1Time.isBefore(now)) {
                for (Nation nation : attackNations) {
                    HashSet<ChunkSnapshot> chunks = new HashSet<>();
                    for (int[] intChunk : nation.getChunks()) {
                        chunks.add(getWorld().getChunkAt(intChunk[0], intChunk[1]).getChunkSnapshot());
                    }
                    attackNationsSnapShot.put(nation, chunks);
                }
                isPhase1Start = true;
                Bukkit.getLogger().info("phase1start");
            }
        } else if (!isPhase2Start) {
            if (phase2Time.isBefore(now)) {
                isScheduled = true;
                HashSet<ChunkSnapshot> chunks = new HashSet<>();
                for (int[] intChunk : defendNation.getChunks()) {
                    chunks.add(getWorld().getChunkAt(intChunk[0], intChunk[1]).getChunkSnapshot());
                }
                defendNationSnapShot = chunks;
                isPhase2Start = true;
                Bukkit.getLogger().info("phase2start");
            }
        } else if (!isPhase3Start) {
            if (phase3Time.isBefore(now)) {
                isPhase3Start = true;
                Bukkit.getLogger().info("phase3start");
            }
        }
    }
}
