package com.tuenkle.earthintimeplugin.listeners;

import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {
    @EventHandler (priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID userUUID = player.getUniqueId();
        if (!Database.users.containsKey(userUUID)) {
            Database.users.put(userUUID, new User(userUUID, player.getName()));
        }
        User user = Database.users.get(userUUID);
        if (!user.getName().equals(player.getName())) {
            user.setName(player.getName());
        }
//        setPlayerScoreboard(user);
    }
    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {

    }
}
