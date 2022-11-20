package com.tuenkle.earthintimeplugin.warps;

import com.tuenkle.earthintimeplugin.EarthInTimePlugin;
import com.tuenkle.earthintimeplugin.utils.NationUtils;
import com.tuenkle.earthintimeplugin.utils.WarpUtils;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WarpListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location toLocation = event.getTo();
        int blockX = toLocation.getBlockX();
        int blockY = toLocation.getBlockY();
        int blockZ = toLocation.getBlockZ();
        int[] block = new int[]{blockX, blockY, blockZ};
        if (WarpUtils.isIntLocationInLocations(block, EarthInTimePlugin.spawnNorthWestWarp)) {
            while (true) {
                int randomX = (int) (Math.random() * 18700);
                int randomZ = (int) (Math.random() * 6150);
                int randomY = EarthInTimePlugin.getWorld().getHighestBlockAt(randomX, randomZ).getLocation().getBlockY() + 1;
                Location newLocation = new Location(EarthInTimePlugin.getWorld(), randomX, randomY, randomZ);
                Chunk newChunk = newLocation.getChunk();
                int[] newIntChunk = new int[]{newChunk.getX(), newChunk.getZ()};
                if (!NationUtils.isIntChunkInNations(newIntChunk)) {
                    player.teleport(newLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);
                    break;
                }
            }
        }
    }
}
