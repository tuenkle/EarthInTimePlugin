package com.tuenkle.earthintimeplugin.utils;

import com.tuenkle.earthintimeplugin.database.Database;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import static com.tuenkle.earthintimeplugin.utils.GeneralUtils.secondToUniversalTime;

public class ScoreboardUtils {
    public static void setPlayerScoreboard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("TimeScoreBoard", Criteria.DUMMY, "Earth In Time");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score = objective.getScore(ChatColor.AQUA + "=-=-=-=-=-=-=-=-=-=-=-=");
        score.setScore(2);
        long userMoney = Database.users.get(player.getUniqueId()).getMoney();
        Score score2 = objective.getScore("소지 시간: " + secondToUniversalTime(userMoney));
        score2.setScore(1);
        player.setDisplayName("[" + secondToUniversalTime(userMoney) + ChatColor.WHITE + "]" + player.getName());
        player.setPlayerListName("[" + secondToUniversalTime(userMoney) + ChatColor.WHITE + "]" + player.getName());
        player.setScoreboard(board);
    }
}
