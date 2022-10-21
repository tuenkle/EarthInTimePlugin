package com.tuenkle.earthintimeplugin.utils;

import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.database.War;
import com.tuenkle.earthintimeplugin.dynmap.NationDynmap;
import com.tuenkle.earthintimeplugin.gui.NationGui;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.*;

import static com.tuenkle.earthintimeplugin.database.Database.nations;

public class NationUtils {
    public static boolean isIntChunkInNations(int[] playerChunk, HashMap<String, Nation> nations) {
        for (Nation nation : nations.values()) {
            for (int[] chunk : nation.getChunks()) {
                if (chunk[0] == playerChunk[0] && chunk[1] == playerChunk[1]){
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean isIntChunkInNation(int[] playerChunk, Nation nation) {
        for (int[] chunk : nation.getChunks()) {
            if (chunk[0] == playerChunk[0] && chunk[1] == playerChunk[1]){
                return true;
            }
        }
        return false;
    }
    public static boolean isPlayerInChunks(Player player, ArrayList<int[]> chunks) {
        Chunk playerChunk = player.getLocation().getChunk();
        for (int[] chunk : chunks) {
            if (chunk[0] == playerChunk.getX() && chunk[1] == playerChunk.getZ()) {
                return true;
            }
        }
        return false;
    }
    public static boolean isChunkNearChunks(int x, int y, HashSet<int[]> chunks) {
        for (int[] chunk : chunks) {
            if ((Math.abs(chunk[0] - x) + Math.abs(chunk[1] - y)) == 1) {
                return true;
            }
        }
        return false;
    }
    public static boolean isChunkInNation(Chunk chunk, Nation nation) {
        for (int[] oneChunk : nation.getChunks()) {
            if (chunk.getX() == oneChunk[0] && chunk.getZ() == oneChunk[1]) {
                return true;
            }
        }
        return false;
    }
    public static boolean isChunkInNationsExceptNation(Chunk chunk, Nation nation) {
        for (Nation tempNation : nations.values()) {
            if (nation == tempNation) {
                continue;
            }
            for (int[] tempChunk : tempNation.getChunks()) {
                if (tempChunk[0] == chunk.getX() && tempChunk[1] == chunk.getZ()){
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean isChunkInNations(Chunk chunk) {
        for (Nation tempNation : nations.values()) {
            for (int[] tempChunk : tempNation.getChunks()) {
                if (tempChunk[0] == chunk.getX() && tempChunk[1] == chunk.getZ()){
                    return true;
                }
            }
        }
        return false;
    }
    public static void disbandNation(Nation nation) {
        for (War war : Database.wars) {
            if (war.getDefendNation() == nation) {
                war.terminateWar();
            }
            war.getDefendNations().remove(nation);
        }
        for (Map.Entry<String, Nation> nationEntry : Database.nations.entrySet()) {
            Nation oneNation = nationEntry.getValue();
            oneNation.removeNationInAllyInvites(nation);
            oneNation.removeNationInAllies(nation);
        }
        for (Map.Entry<User, LocalDateTime> resident : nation.getResidents().entrySet()) {
            resident.getKey().setNation(null);
        }
        Database.nations.remove(nation.getName());
        NationDynmap.eraseNation(nation);
        NationGui.makeNationsList();
    }

    public static long getNationExpandMoney(Nation nation) {
        return 600;
    }
}
