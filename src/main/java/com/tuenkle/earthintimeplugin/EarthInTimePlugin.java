package com.tuenkle.earthintimeplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;
import com.tuenkle.earthintimeplugin.commands.CommandClock;
import com.tuenkle.earthintimeplugin.commands.CommandNation;
import com.tuenkle.earthintimeplugin.dynmap.NationDynmap;
import com.tuenkle.earthintimeplugin.gui.NationGui;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import com.tuenkle.earthintimeplugin.gui.buttons.NationButtons;
import com.tuenkle.earthintimeplugin.listeners.ClockListener;
import com.tuenkle.earthintimeplugin.listeners.NationGuiListener;
import com.tuenkle.earthintimeplugin.listeners.NationListener;
import com.tuenkle.earthintimeplugin.listeners.PlayerJoinListener;
import com.tuenkle.earthintimeplugin.recipes.ClockRecipes;
import com.tuenkle.earthintimeplugin.scheduler.OneSecondScheduler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;
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
        NationGui.makeAll();
        Objects.requireNonNull(this.getCommand("나라")).setExecutor(new CommandNation(this));
        Objects.requireNonNull(this.getCommand("clock")).setExecutor(new CommandClock());
        getServer().getPluginManager().registerEvents(new ClockListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new NationListener(), this);
        getServer().getPluginManager().registerEvents(new NationGuiListener(), this);

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
