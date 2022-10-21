package com.tuenkle.earthintimeplugin.scheduler;

import com.tuenkle.earthintimeplugin.database.Nation;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

import static com.tuenkle.earthintimeplugin.dynmap.NationDynmap.chunksToSortedVertices;


public class ParticlesScheduler extends BukkitRunnable {
    private final Player player;
    private int count = 0;
    private Nation nation;
    private final double particleY;
    public ParticlesScheduler(Player player, Nation nation){
        this.player = player;
        this.particleY = player.getLocation().getY() + 2;
        this.nation = nation;
    }
    @Override
    public void run() {
        ArrayList<int[]> sortedVertices = chunksToSortedVertices(nation.getChunks());
        if (count >= 10) {
            this.cancel();
        }
        count++;
        for (int i = 0; i < sortedVertices.size(); i++){
            int x = sortedVertices.get(i)[0];
            int z = sortedVertices.get(i)[1];
            int nextX;
            int nextZ;
            if (i == (sortedVertices.size() - 1)) {
                nextX = sortedVertices.get(0)[0];
                nextZ = sortedVertices.get(0)[1];
            } else {
                nextX = sortedVertices.get(i+1)[0];
                nextZ = sortedVertices.get(i+1)[1];
            }
            if (x == nextX){
                if (nextZ > z) {
                    for (int j = z; j < nextZ; j++){
                        player.spawnParticle(Particle.FLAME, x, particleY, j, 1, 0, 0, 0, 0);
                    }
                } else {
                    for (int j = z; j > nextZ; j--){
                        player.spawnParticle(Particle.FLAME, x, particleY, j, 1, 0, 0, 0, 0);
                    }
                }

            } else {
                if (nextX > x) {
                    for (int j = x; j < nextX; j++){
                        player.spawnParticle(Particle.FLAME, j, particleY, z, 1, 0, 0, 0, 0);
                    }
                } else {
                    for (int j = x; j > nextX; j--){
                        player.spawnParticle(Particle.FLAME, j, particleY, z, 1, 0, 0, 0, 0);
                    }
                }
            }
        }
    }
}

