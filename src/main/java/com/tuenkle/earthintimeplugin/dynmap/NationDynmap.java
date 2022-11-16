package com.tuenkle.earthintimeplugin.dynmap;

import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.Nation;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.MarkerSet;
import org.bukkit.Bukkit;
import org.dynmap.markers.AreaMarker;

import java.util.*;

import static com.tuenkle.earthintimeplugin.EarthInTimePlugin.getSpawnChunks;

public class NationDynmap {
    private static DynmapAPI dynmapAPI;
    private static MarkerSet markerSet;

    public static void setNationDynmapAPI() {
        dynmapAPI = (DynmapAPI) Bukkit.getServer().getPluginManager().getPlugin("Dynmap");
        markerSet = dynmapAPI.getMarkerAPI().createMarkerSet("tuenkle.markerset", "Areas", dynmapAPI.getMarkerAPI().getMarkerIcons(), false);
    }
    public static void setMarkerStyle(AreaMarker marker) {
        marker.setLineStyle(1, 0.8, 0x101010);
        marker.setFillStyle(0.3, 0x42f4f1);
    }
    public static void eraseAndDrawNation(Nation nation) {
        HashSet<int[]> chunks = nation.getChunks();
        ArrayList<int[]> sortedVertices = chunksToSortedVertices(chunks);
        ArrayList<Integer> sortedVerticeX = new ArrayList<>();
        ArrayList<Integer> sortedVerticeZ = new ArrayList<>();
        for (int[] vertex: sortedVertices){
            sortedVerticeX.add(vertex[0]);
            sortedVerticeZ.add(vertex[1]);
        }
        double[] x = sortedVerticeX.stream().mapToDouble(i -> i).toArray();
        double[] z = sortedVerticeZ.stream().mapToDouble(i -> i).toArray();

        markerSet.findAreaMarker(nation.getName()).deleteMarker();
        AreaMarker marker = markerSet.createAreaMarker(nation.getName(), nation.getName(), true, "world", x, z, false);
        setMarkerStyle(marker);
    }
    public static void drawSpawn() {
        ArrayList<int[]> points = chunksToPoints(getSpawnChunks());
        ArrayList<int[]> vertices = pointsToVertices(points);
        ArrayList<int[]> sortedVertices = verticesToSortedVertices(vertices);
        ArrayList<Integer> sortedVerticeX = new ArrayList<>();
        ArrayList<Integer> sortedVerticeZ = new ArrayList<>();
        for (int[] vertex: sortedVertices){
            sortedVerticeX.add(vertex[0]);
            sortedVerticeZ.add(vertex[1]);
        }
        double[] x = sortedVerticeX.stream().mapToDouble(i -> i).toArray();
        double[] z = sortedVerticeZ.stream().mapToDouble(i -> i).toArray();

        AreaMarker marker = markerSet.createAreaMarker("스폰", "스폰", true, "world", x, z, false);
        setMarkerStyle(marker);
    }
    public static void drawNations() {
        for (Map.Entry<String, Nation> nation : Database.nations.entrySet()) {
            String nationName = nation.getKey();
            HashSet<int[]> chunks = nation.getValue().getChunks();
            ArrayList<int[]> points = chunksToPoints(chunks);
            ArrayList<int[]> vertices = pointsToVertices(points);
            ArrayList<int[]> sortedVertices = verticesToSortedVertices(vertices);
            ArrayList<Integer> sortedVerticeX = new ArrayList<>();
            ArrayList<Integer> sortedVerticeZ = new ArrayList<>();
            for (int[] vertex: sortedVertices){
                sortedVerticeX.add(vertex[0]);
                sortedVerticeZ.add(vertex[1]);
            }
            double[] x = sortedVerticeX.stream().mapToDouble(i -> i).toArray();
            double[] z = sortedVerticeZ.stream().mapToDouble(i -> i).toArray();

            AreaMarker marker = markerSet.createAreaMarker(nationName, nationName, true, "world", x, z, false);
            setMarkerStyle(marker);
        }
    }
    public static void drawNation(Nation nation) {
        HashSet<int[]> chunks = nation.getChunks();
        ArrayList<int[]> points = chunksToPoints(chunks);
        ArrayList<int[]> vertices = pointsToVertices(points);
        ArrayList<int[]> sortedVertices = verticesToSortedVertices(vertices);
        ArrayList<Integer> sortedVerticeX = new ArrayList<>();
        ArrayList<Integer> sortedVerticeZ = new ArrayList<>();
        for (int[] vertex: sortedVertices){
            sortedVerticeX.add(vertex[0]);
            sortedVerticeZ.add(vertex[1]);
        }
        double[] x = sortedVerticeX.stream().mapToDouble(i -> i).toArray();
        double[] z = sortedVerticeZ.stream().mapToDouble(i -> i).toArray();

        AreaMarker marker = markerSet.createAreaMarker(nation.getName(), nation.getName(), true, "world", x, z, false);
        setMarkerStyle(marker);
    }

    public static ArrayList<int[]> chunksToSortedVertices(HashSet<int[]> chunks) {
        ArrayList<int[]> points = chunksToPoints(chunks);
        ArrayList<int[]> vertices = pointsToVertices(points);
        return verticesToSortedVertices(vertices);
    }
    public static ArrayList<int[]> chunksToPoints(HashSet<int[]> chunks) {
        ArrayList<int[]> points = new ArrayList<>();
        for (int[] chunk : chunks) {
            int chunkX = chunk[0];
            int chunkZ = chunk[1];
            int worldX = chunkX * 16;
            int worldZ = chunkZ * 16;
            for (int i = 0; i <= 1; i++) {
                for (int j = 0; j <= 1; j++) {
                    points.add(new int[] {worldX + 15 * i, worldZ + 15 * j});
                }
            }
        }
        return points;
    }
    public static void eraseNation(Nation nation) {
        markerSet.findAreaMarker(nation.getName()).deleteMarker();
    }
    public static ArrayList<int[]> pointsToVertices(ArrayList<int[]> points) { //추후에 자신 청크 주변 8개의 청크에 존재하는 노드랑만 비교하게 바꿔야 함
        ArrayList<int[]> vertices = new ArrayList<>();
        for (int[] point : points){
            int pointX = point[0];
            int pointZ = point[1];
            int howManyDistanceOneFromPoint = 0;
            int howManyDistanceRootTwoFromPoint = 0;
            for (int[] otherPoint : points) {
                int otherPointX = otherPoint[0];
                int otherPointZ = otherPoint[1];
                int i = Math.abs(otherPointX - pointX) + Math.abs(otherPointZ - pointZ);
                if (i == 1) {
                    howManyDistanceOneFromPoint++;
                } else if (i == 2) {
                    howManyDistanceRootTwoFromPoint++;
                }
            }
            if (howManyDistanceOneFromPoint == 0){
                vertices.add(point);
            } else if (howManyDistanceOneFromPoint == 2) {
                if (howManyDistanceRootTwoFromPoint != 1) {
                    vertices.add(point);
                }
            }
        }
        return vertices;
    }
    public static ArrayList<int[]> verticesToSortedVertices(ArrayList<int[]> vertices) {
        int i = 0;
        boolean xMovingTurn = true;
        while (i != vertices.size() - 2) { //finished 마지막 전 원소는 확인할 필요가 없음
            int x = vertices.get(i)[0];
            int z = vertices.get(i)[1];
            int increaseCount = 0;
            int increasingSmallestLength = 100000;
            int decreasingSmallestLength = 100000;
            int increasingSmallestLengthIndex = 0;
            int decreasingSmallestLengthIndex = 0;
            if (xMovingTurn) {
                for (int vertexIndex = 0; vertexIndex < vertices.size(); vertexIndex++) {
                    int comparingX = vertices.get(vertexIndex)[0];
                    int comparingZ = vertices.get(vertexIndex)[1];
                    if (comparingZ == z) {
                        if (comparingX == x) {

                        } else if (comparingX > x) {
                            int length = Math.abs(comparingX - x);
                            if (length < increasingSmallestLength) {
                                increasingSmallestLength = length;
                                increasingSmallestLengthIndex = vertexIndex;
                            }
                            increaseCount++;
                        } else if (comparingX < x) {
                            int length = Math.abs(comparingX - x);
                            if (length < decreasingSmallestLength) {
                                decreasingSmallestLength = length;
                                decreasingSmallestLengthIndex = vertexIndex;
                            }
                        }
                    }
                }
                xMovingTurn = false;
            } else {
                for (int vertexIndex = 0; vertexIndex < vertices.size(); vertexIndex++) {
                    int comparingX = vertices.get(vertexIndex)[0];
                    int comparingZ = vertices.get(vertexIndex)[1];
                    if (comparingX == x) {
                        if (comparingZ == z) {

                        } else if (comparingZ > z) {
                            int length = Math.abs(comparingZ - z);
                            if (length < increasingSmallestLength) {
                                increasingSmallestLength = length;
                                increasingSmallestLengthIndex = vertexIndex;
                            }
                            increaseCount++;
                        } else if (comparingZ < z) {
                            int length = Math.abs(comparingZ - z);
                            if (length < decreasingSmallestLength) {
                                decreasingSmallestLength = length;
                                decreasingSmallestLengthIndex = vertexIndex;
                            }
                        }
                    }
                }
                xMovingTurn = true;
            }
            if (increaseCount % 2 == 1) {
                Collections.swap(vertices, i + 1, increasingSmallestLengthIndex);
            } else {
                Collections.swap(vertices, i + 1, decreasingSmallestLengthIndex);
            }
            i++;
        }
        return vertices;
    }
}
