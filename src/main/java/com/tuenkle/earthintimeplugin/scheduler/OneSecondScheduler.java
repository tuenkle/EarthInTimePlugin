package com.tuenkle.earthintimeplugin.scheduler;
import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.database.War;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import static com.tuenkle.earthintimeplugin.utils.ScoreboardUtils.setPlayerScoreboard;

public class OneSecondScheduler extends BukkitRunnable {
    private final JavaPlugin plugin;
    public OneSecondScheduler(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public void run() { // 추후에 틱당 시간분배해서 사람들 나눠서 처리 시간 줄이는건 좀 힘들 것 같고 스코어보드는 가능
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            User user = Database.users.get(onlinePlayer.getUniqueId());
            if (user == null) {
                Database.users.put(onlinePlayer.getUniqueId(), new User(onlinePlayer.getUniqueId(), onlinePlayer.getName()));
                return;
            }
            if (user.getMoney() <= 0){
                onlinePlayer.setHealth(1);
            } else {
                user.withdrawMoney(1);
            }
            setPlayerScoreboard(onlinePlayer);
            World world = Bukkit.getWorld("world");

            for (War war : Database.wars) {
                war.warStartIfAttackStartTimeIsAfterNow();
            }
        }
    }
}
