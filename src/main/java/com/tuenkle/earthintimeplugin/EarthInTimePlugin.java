package com.tuenkle.earthintimeplugin;

import com.tuenkle.earthintimeplugin.commands.CommandGui;
import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.listeners.*;
import com.tuenkle.earthintimeplugin.utils.WarUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;
import java.util.UUID;

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
        Objects.requireNonNull(this.getCommand("메뉴")).setExecutor(new CommandGui());
        getServer().getPluginManager().registerEvents(new ClockListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new NationListener(), this);
        getServer().getPluginManager().registerEvents(new GuiListener(), this);
        getServer().getPluginManager().registerEvents(new GuiStartListener(), this);

        ClockRecipes.EmptyClock(this);
        ClockRecipes.Clock10Minute(this);
        ClockRecipes.Clock1Hour(this);

        //Development Only!!!
        User user = new User(UUID.fromString("d8ea842a-9fc4-4104-a928-95e4b5aa6d42"), "StrawberryF");
        User user2 = new User(UUID.fromString("3a4ee10c-cbf2-4269-bbc5-ed06ef90898a"), "MelonF");
        User user3 = new User(UUID.fromString("bd442907-892e-4586-ae50-629583eecdc0"), "OrangeF");
        Database.users.put(UUID.fromString("d8ea842a-9fc4-4104-a928-95e4b5aa6d42"), user);
        Database.users.put(UUID.fromString("3a4ee10c-cbf2-4269-bbc5-ed06ef90898a"), user2);
        Database.users.put(user3.getUuid(), user3);
        int[] chunk = {0, 0};
        int[] chunk2 = {1, 0};
        int[] chunk3 = {2, 0};
        Nation nation = new Nation("korea", user, chunk, new Location(Bukkit.getWorld("world"), 0,0,0));
        Nation nation2 = new Nation("usa", user2, chunk2, new Location(Bukkit.getWorld("world"), 16,0,0));
        Nation nation3 = new Nation("orangef", user3, chunk3, new Location(Bukkit.getWorld("world"), 16,0,0));
        Database.nations.put("korea", nation);
        Database.nations.put("usa", nation2);
        Database.nations.put("orangef", nation3);
        WarUtils.declareWar(nation, nation2);
        //Development Only!!!

        NationDynmap.setNationDynmapAPI();
        NationDynmap.drawNations();
        BukkitTask task = new OneSecondScheduler(this).runTaskTimer(this, 0, 20);



        getLogger().info("====EarthInTime is Enabled====");
    }
    public void onDisable() {
        getLogger().info("====EarthInTime is Disabled====");
    }
}
