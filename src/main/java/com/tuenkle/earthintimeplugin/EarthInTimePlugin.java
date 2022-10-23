package com.tuenkle.earthintimeplugin;

import com.tuenkle.earthintimeplugin.listeners.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;
import com.tuenkle.earthintimeplugin.commands.CommandClock;
import com.tuenkle.earthintimeplugin.commands.CommandNation;
import com.tuenkle.earthintimeplugin.dynmap.NationDynmap;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import com.tuenkle.earthintimeplugin.gui.buttons.NationButtons;
import com.tuenkle.earthintimeplugin.recipes.ClockRecipes;
import com.tuenkle.earthintimeplugin.scheduler.OneSecondScheduler;

public class EarthInTimePlugin extends JavaPlugin {
    private static EarthInTimePlugin mainInstance;

    public EarthInTimePlugin() {
        mainInstance = this;
    }
    public static EarthInTimePlugin getMainInstance() {
        return mainInstance;
    }
    @Override
    public void onEnable() {
        GeneralButtons.makeAll();
        NationButtons.makeAll();
        Objects.requireNonNull(this.getCommand("나라")).setExecutor(new CommandNation(this));
        Objects.requireNonNull(this.getCommand("clock")).setExecutor(new CommandClock());
        getServer().getPluginManager().registerEvents(new ClockListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new NationListener(), this);
        getServer().getPluginManager().registerEvents(new NationGuiListenerNew(), this);
        getServer().getPluginManager().registerEvents(new WarGuiListener(), this);

        ClockRecipes.EmptyClock(this);
        ClockRecipes.Clock10Minute(this);
        ClockRecipes.Clock1Hour(this);

        NationDynmap.setNationDynmapAPI();
        NationDynmap.drawNations();
        BukkitTask task = new OneSecondScheduler(this).runTaskTimer(this, 0, 20);
        getLogger().info("====EarthInTime is Enabled====");
    }
    public void onDisable() {
        getLogger().info("====EarthInTime is Disabled====");
    }
}
