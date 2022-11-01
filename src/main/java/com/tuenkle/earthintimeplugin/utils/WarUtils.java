package com.tuenkle.earthintimeplugin.utils;

import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.War;
import org.bukkit.Bukkit;

import java.time.LocalDateTime;

public class WarUtils {
    public static void declareWar(Nation nation, Nation targetNation) {
        nation.getAllyInvites().remove(targetNation);
        targetNation.getAllyInvites().remove(nation);
        Database.wars.add(new War(nation, targetNation, LocalDateTime.now()));
        Bukkit.broadcastMessage(String.format("%s 국가가 %s 국가에 전쟁을 선포하였습니다. 11시간안에 %s 국가는 전쟁시간을 확정하여야 합니다. 확정하지 못할시 전쟁은 %s에 시작됩니다.", nation.getName(), targetNation.getName(), targetNation.getName(), GeneralUtils.dateTimeFormatter(LocalDateTime.now().plusHours(11))));
    }
    public static void removeNationRelated(Nation nation) {
        for (War war : Database.wars) {
            war.attackJoinApplicationNations.remove(nation);
            war.defendJoinApplicationNations.remove(nation);
        }
    }
}
