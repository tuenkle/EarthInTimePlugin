package com.tuenkle.earthintimeplugin.utils;

import java.util.HashSet;

public class WarpUtils {
    public static boolean isIntLocationInLocations(int[] targetLocation, HashSet<int[]> locations) {
        for (int[] location : locations) {
            if (targetLocation[0] == location[0] && targetLocation[1] == location[1] && targetLocation[2] == location[2]){
                return true;
            }
        }
        return false;
    }
}
