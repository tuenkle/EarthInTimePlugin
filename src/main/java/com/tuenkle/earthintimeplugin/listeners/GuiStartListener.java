package com.tuenkle.earthintimeplugin.listeners;

import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.gui.MainGui;
import com.tuenkle.earthintimeplugin.utils.GuiUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class GuiStartListener implements Listener {
    @EventHandler
    public void onShiftSwap(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking()) {
            event.setCancelled(true);
            User user = Database.users.get(player.getUniqueId());
            GuiUtils.openGui(new MainGui(user), user, player);
            return;
        }
    }
}
