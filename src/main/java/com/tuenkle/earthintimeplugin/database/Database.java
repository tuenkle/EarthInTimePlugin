package com.tuenkle.earthintimeplugin.database;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class Database {
    public static HashMap<String, Nation> nations = new HashMap<>();
    public static HashMap<UUID, User> users = new HashMap<>();
    public static HashSet<War> wars = new HashSet<>();
    public static boolean isNationInWar(Nation nation) {
        for (War war : wars) {
            if (war.getAttackNations().contains(nation)){
                return true;
            }
            if (war.getDefendNations().contains(nation)){
                return true;
            }
        }
        return false;
    }
    public static User getUserByUsername(String username) {
        Player player = Bukkit.getPlayerExact(username);
        if (player != null) {
            return users.get(player.getUniqueId());
        }
        for (User user : users.values()) {
            if (user.getName().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }
    public static boolean isWarTogether(Nation nation, Nation nation2) {
        for (War war : wars) {
            if (war.getDefendNations().contains(nation) && war.getAttackNations().contains(nation2)) {
                return true;
            }
            if (war.getAttackNations().contains(nation) && war.getDefendNations().contains(nation2)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isAttacking(Nation nation) {
        for (War war : Database.wars) {
            if (war.getAttackNations().contains(nation)) {
                return true;
            }
        }
        return false;
    }
    public static War getWar(Nation nation1, Nation nation2) {
        for (War war : wars) {
            if (war.getAttackNation() == nation1 && war.getDefendNation() == nation2) {
                return war;
            }
        }
        return null;
    }
    public static War getRelatedWar(Nation nation) {
        for (War war: wars) {
            if (war.getAttackNations().contains(nation) || war.getDefendNations().contains(nation)) {
                return war;
            }
        }
        return null;
    }
    public static ArrayList<War> getWarRelated(Nation nation) {
        ArrayList<War> relatedWars = new ArrayList<>();
        for (War war: wars) {
            if (war.getAttackNations().contains(nation) || war.getDefendNations().contains(nation)) {
                relatedWars.add(war);
            }
        }
        return relatedWars;
    }
    public static String getRelation(Nation nation1, Nation nation2) {
        if (isWarTogether(nation1, nation2)) {
            return "전쟁";
        }
        if (nation1.getAllies().containsKey(nation2)) {
            return "동맹";
        }
        return "중립";
    }
    public static void removeNation(Nation nation) {
        nation.isRemoved = true;
        nations.remove(nation.getName());
    }
}
