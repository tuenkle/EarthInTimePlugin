package com.tuenkle.earthintimeplugin.database;
import java.time.LocalDateTime;
import java.util.*;

public class War {
    public boolean isAttackStarted() {
        return isAttackStarted;
    }
    public void setAttackStarted(boolean attackStarted) {
        isAttackStarted = attackStarted;
    }

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

    public HashSet<User> getAttackPlayers() {
        return attackPlayers;
    }

    public void setAttackPlayers(HashSet<User> attackPlayers) {
        this.attackPlayers = attackPlayers;
    }

    public HashSet<User> getDefendPlayers() {
        return defendPlayers;
    }

    public void setDefendPlayers(HashSet<User> defendPlayers) {
        this.defendPlayers = defendPlayers;
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

    public LocalDateTime getAttackStartTime() {
        return attackStartTime;
    }

    public void setAttackStartTime(LocalDateTime attackStartTime) {
        this.attackStartTime = attackStartTime;
    }

    public LocalDateTime getDefendStartTime() {
        return defendStartTime;
    }

    public void setDefendStartTime(LocalDateTime defendStartTime) {
        this.defendStartTime = defendStartTime;
    }
    public void terminateWar() {
        Database.wars.remove(this);
    }
    private boolean isAttackStarted = false;
    private boolean isScheduled = false;
    private HashSet<Nation> attackNations = new HashSet<>();
    private HashSet<Nation> defendNations = new HashSet<>();
    private HashSet<User> attackPlayers = new HashSet<>();
    private HashSet<User> defendPlayers = new HashSet<>();
    private Nation attackNation;
    private Nation defendNation;
    private LocalDateTime attackStartTime;
    private LocalDateTime defendStartTime;

    public War (Nation attackNation, Nation defendNation) {
        this.attackNations.add(attackNation);
        this.attackNation = attackNation;
        this.defendNations.add(defendNation);
        this.defendNation = defendNation;
        LocalDateTime declarationTime = LocalDateTime.now();
        this.attackStartTime = declarationTime.plusHours(11);
        this.defendStartTime = declarationTime.plusHours(1);
    }
    public void warStartIfAttackStartTimeIsAfterNow () {
        if (isAttackStarted) {
            return;
        }
        if (LocalDateTime.now().isAfter(attackStartTime)) {
            for (Nation nation : attackNations){
                for (Map.Entry<User, LocalDateTime> resident: nation.getResidents().entrySet()) {
                    if (LocalDateTime.now().isAfter(resident.getValue().plusHours(48))) { //나라 가입한 지 48시간이 지나야 공격으로 참가 가능
                        attackPlayers.add(resident.getKey());
                    }
                }
            }
            for (Nation nation : defendNations){
                for (Map.Entry<User, LocalDateTime> residents: nation.getResidents().entrySet()) {
                    defendPlayers.add(residents.getKey());
                }
            }
            isScheduled = true;
            isAttackStarted = true;
        }
    }
}
