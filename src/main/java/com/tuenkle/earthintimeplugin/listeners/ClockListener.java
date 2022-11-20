package com.tuenkle.earthintimeplugin.listeners;

import com.tuenkle.earthintimeplugin.database.Database;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;


public class ClockListener implements Listener {
    @EventHandler
    public void onClockUse(PlayerInteractEvent event) {
        if (event.getItem() != null) {
            if (event.getItem().getType() == Material.CLOCK) {
                ItemMeta clockMeta = event.getItem().getItemMeta();
                if (clockMeta != null && clockMeta.hasEnchant(Enchantment.LUCK)) {
                    if (clockMeta.hasLore()) {
                        long clockSeconds = Long.parseLong(clockMeta.getLore().get(0));
                        Player player = event.getPlayer();
                        event.getItem().setAmount(event.getItem().getAmount() - 1);
                        Database.users.get(player.getUniqueId()).depositMoney(clockSeconds);
                        player.sendMessage(clockMeta.getDisplayName());
                    }
                }
            }
        }
    }
}
