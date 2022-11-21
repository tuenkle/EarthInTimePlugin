package com.tuenkle.earthintimeplugin.warps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class WarpCoordinates {
    public static HashSet<ArrayList<Integer>> spawnNorthWestWarp = new HashSet<>();
    public static HashSet<ArrayList<Integer>> spawnNorthEastWarp = new HashSet<>();
    public static HashSet<ArrayList<Integer>> spawnSouthWestWarp = new HashSet<>();
    public static HashSet<ArrayList<Integer>> spawnSouthEastWarp = new HashSet<>();
    public static void setCoordinates() {
        setSpawnNorthWestWarp();
        setSpawnNorthEastWarp();
        setSpawnSouthWestWarp();
        setSpawnSouthEastWarp();
    }
    public static void setSpawnNorthWestWarp() {
        spawnNorthWestWarp.add(new ArrayList<>(Arrays.asList(18854, 160, 6305)));
        spawnNorthWestWarp.add(new ArrayList<>(Arrays.asList(18855, 160, 6305)));
        spawnNorthWestWarp.add(new ArrayList<>(Arrays.asList(18855, 160, 6304)));
        spawnNorthWestWarp.add(new ArrayList<>(Arrays.asList(18856, 160, 6304)));
        spawnNorthWestWarp.add(new ArrayList<>(Arrays.asList(18856, 160, 6303)));
        spawnNorthWestWarp.add(new ArrayList<>(Arrays.asList(18857, 160, 6303)));
        spawnNorthWestWarp.add(new ArrayList<>(Arrays.asList(18857, 160, 6302)));
        spawnNorthWestWarp.add(new ArrayList<>(Arrays.asList(18858, 160, 6302)));
    }
    public static void setSpawnNorthEastWarp() {
        spawnNorthEastWarp.add(new ArrayList<>(Arrays.asList(18854, 160, 6305)));
        spawnNorthEastWarp.add(new ArrayList<>(Arrays.asList(18855, 160, 6305)));
        spawnNorthEastWarp.add(new ArrayList<>(Arrays.asList(18855, 160, 6304)));
        spawnNorthEastWarp.add(new ArrayList<>(Arrays.asList(18856, 160, 6304)));
        spawnNorthEastWarp.add(new ArrayList<>(Arrays.asList(18856, 160, 6303)));
        spawnNorthEastWarp.add(new ArrayList<>(Arrays.asList(18857, 160, 6303)));
        spawnNorthEastWarp.add(new ArrayList<>(Arrays.asList(18857, 160, 6302)));
        spawnNorthEastWarp.add(new ArrayList<>(Arrays.asList(18858, 160, 6302)));
    }
    public static void setSpawnSouthWestWarp() {

    }
    public static void setSpawnSouthEastWarp() {

    }
}
