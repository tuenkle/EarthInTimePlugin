package com.tuenkle.earthintimeplugin.vault;

import com.tuenkle.earthintimeplugin.EarthInTimePlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.ServicePriority;

public class VaultHook {
    private EarthInTimePlugin plugin = EarthInTimePlugin.getMainInstance();
    private Economy provider;
    public void hook() {
        provider = EarthInTimePlugin.economyImplementer;
        Bukkit.getServicesManager().register(Economy.class, this.provider, this.plugin, ServicePriority.Normal);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "VaultAPI hooked into " + plugin.getName());
    }
    public void unhook() {
        Bukkit.getServicesManager().unregister(Economy.class, this.provider);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "VaultAPI unhooked from " + plugin.getName());
    }
}
