package com.tuenkle.earthintimeplugin.listeners;

import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.utils.NationUtils;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.Map;

//블럭 설치, 부수기 등 모든 이벤트 소속국이 아닌 다른 나라에서 하지 못하게
//공격, 액자안의 물건 가져가기 등 모든

public class NationListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Nation playerNation = Database.users.get(player.getUniqueId()).getNation();
        Chunk playerChunk = player.getLocation().getChunk();
        Block block = event.getClickedBlock();
        if (block != null) {
            Chunk blockChunk = block.getChunk();
            if (playerNation != null) {
                if (NationUtils.isChunkInNation(blockChunk, playerNation)) {
                    return;
                }
            }
            if (NationUtils.isChunkInNationsExceptNation(blockChunk, playerNation)) {
                event.setCancelled(true);
                return;
            }
        } else {
            if (playerNation != null) {
                if (NationUtils.isChunkInNation(playerChunk, playerNation)) {
                    return;
                }
            }
            if (NationUtils.isChunkInNationsExceptNation(playerChunk, playerNation)) {
                event.setCancelled(true);
                return;
            }
        }
    }
    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) { //이벤트를 일으킨 대상이 플레이어라면 그냥 엔티티의 위치를 조회해서 만약 플레이어의 나라와 다른 나라에 있다면 모든 이벤트를 취소시킨다.
            Nation damagerPlayerNation = Database.users.get(player.getUniqueId()).getNation();
            Chunk damagedEntityChunk = event.getEntity().getLocation().getChunk();
            if (damagerPlayerNation == null) {
                if (NationUtils.isChunkInNations(damagedEntityChunk)) {
                    event.setCancelled(true);
                }
                return;
            }
            if (NationUtils.isChunkInNationsExceptNation(damagedEntityChunk, damagerPlayerNation)){
                event.setCancelled(true);
                return;
            }
        }
        if (event.getDamager() instanceof Projectile projectile) {
            if (projectile.getShooter() instanceof Player){
                    Player damagedPlayer = (Player) event.getEntity();
                    Nation damagedPlayerNation = Database.users.get(damagedPlayer.getUniqueId()).getNation();
                    Chunk damagedPlayerChunk = damagedPlayer.getLocation().getChunk();
                    if (damagedPlayerNation == null) {
                        if (NationUtils.isChunkInNations(damagedPlayerChunk)) {
                            event.setCancelled(true);
                        }
                        return;
                    }
                    if (NationUtils.isChunkInNation(damagedPlayerChunk, damagedPlayerNation)) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }
