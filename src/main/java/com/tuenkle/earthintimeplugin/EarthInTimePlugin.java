package com.tuenkle.earthintimeplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class EarthInTimePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("====EarthInTime is Enabled====");
    }
    public void onDisable() {
        getLogger().info("====EarthInTime is Disabled====");
    }
}
