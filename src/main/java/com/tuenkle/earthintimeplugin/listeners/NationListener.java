package com.tuenkle.earthintimeplugin.listeners;

import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.database.War;
import com.tuenkle.earthintimeplugin.utils.NationUtils;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
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
        player.sendMessage(event.toString());
        User user = Database.users.get(player.getUniqueId());
        Nation userNation = Database.users.get(player.getUniqueId()).getNation();
        Chunk playerChunk = player.getLocation().getChunk();
        Block block = event.getClickedBlock();
        if (block != null) {
            Chunk blockChunk = block.getChunk();
            if (userNation == null) {
                if (NationUtils.isChunkInNations(blockChunk)) {
                    player.sendMessage("나라없는 유저가 다른 나라의 블럭 상호작용 했으므로 비허용");
                    event.setCancelled(true);
                }
                return;
            }
            War war = Database.getRelatedWar(userNation);
            if (war != null && !war.isPhase3Start) {
                if (war.isAttackUser(user)) {
                    if (war.isPhase2Start) {
                        if (NationUtils.isChunkInNation(blockChunk, war.getDefendNation()) || NationUtils.isChunkInSpecificNations(blockChunk, war.getAttackNations())) {
                            player.sendMessage("페이즈2시작후 공격 유저가 수비국 또는 공격국 연합의 블럭 상호작용 했으므로 허용");
                            return;
                        }
                    } else if (war.isPhase1Start) {
                        if (NationUtils.isChunkInSpecificNations(blockChunk, war.getAttackNations())) {
                            player.sendMessage("페이즈1시작후 공격 유저가 공격국 연합의 블럭 상호작용 했으므로 허용");
                            return;
                        }
                    }
                } else if (war.isDefendUser(user)) {
                    if (war.isPhase1Start) {
                        if (NationUtils.isChunkInSpecificNations(blockChunk, war.getAttackNations())) {
                            player.sendMessage("페이즈1시작후 수비 유저가 공격국 연합의 블럭 상호작용 했으므로 허용");
                            return;
                        }
                    }
                }
            }
            if (NationUtils.isChunkInNationsExceptNation(blockChunk, userNation)) {
                player.sendMessage("나라있는 유저가 자신이 아닌 다른 나라의 블럭 상호작용 했으므로 비허용");
                event.setCancelled(true);
                return;
            }
        } else {
            if (userNation == null) {
                if (NationUtils.isChunkInNations(playerChunk)) {
                    player.sendMessage("나라없는 유저가 다른 나라에서 상호작용 했으므로 비허용");
                    event.setCancelled(true);
                }
                return;
            }
            War war = Database.getRelatedWar(userNation);
            if (war != null && !war.isPhase3Start) {
                if (war.isAttackUser(user)) {
                    if (war.isPhase2Start) {
                        if (NationUtils.isChunkInNation(playerChunk, war.getDefendNation()) || NationUtils.isChunkInSpecificNations(playerChunk, war.getAttackNations())) {
                            player.sendMessage("페이즈2시작후 공격 유저가 수비국 또는 공격국 연합에서 상호작용 했으므로 허용");
                            return;
                        }
                    } else if (war.isPhase1Start) {
                        if (NationUtils.isChunkInSpecificNations(playerChunk, war.getAttackNations())) {
                            player.sendMessage("페이즈1시작후 공격 유저가 공격국 연합에서 상호작용 했으므로 허용");
                            return;
                        }
                    }
                } else if (war.isDefendUser(user)) {
                    if (war.isPhase1Start) {
                        if (NationUtils.isChunkInSpecificNations(playerChunk, war.getAttackNations())) {
                            player.sendMessage("페이즈1시작후 수비 유저가 공격국 연합에서 상호작용 했으므로 허용");
                            return;
                        }
                    }
                }
            }
            if (NationUtils.isChunkInNationsExceptNation(playerChunk, userNation)) {
                player.sendMessage("나라있는 유저가 자신이 아닌 다른 나라에서 상호작용 했으므로 비허용");
                event.setCancelled(true);
                return;
            }
        }
    }
    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) { //이벤트를 일으킨 대상이 플레이어라면 그냥 엔티티의 위치를 조회해서 만약 플레이어의 나라와 다른 나라에 있다면 모든 이벤트를 취소시킨다.
            player.sendMessage(event.toString());
            User user = Database.users.get(player.getUniqueId());
            Nation damagerPlayerNation = user.getNation();
            Entity damagedEntity = event.getEntity();
            Chunk damagedEntityChunk = damagedEntity.getLocation().getChunk();
            if (damagerPlayerNation == null) {
                if (NationUtils.isChunkInNations(damagedEntityChunk)) {
                    player.sendMessage("나라없는 유저가 다른 나라에서 엔티티공격 했으므로 비허용");
                    event.setCancelled(true);
                }
                return;
            }
            War war = Database.getRelatedWar(damagerPlayerNation);
            if (war != null && !war.isPhase3Start) {
                if (war.isAttackUser(user)) {
                    if (war.isPhase2Start) {
                        if (NationUtils.isChunkInNation(damagedEntityChunk, war.getDefendNation())) {//공격자가 방어국에서 벌어난 일은 모든지 허용
                            player.sendMessage("페이즈2시작후 공격 유저가 수비국에서 엔티티공격 했으므로 허용");
                            return;
                        }
                        if (damagedEntity instanceof Player damagedPlayer) {
                            User damagedUser = Database.users.get(damagedPlayer.getUniqueId());
                            if (war.isDefendUser(damagedUser)) {
                                player.sendMessage("페이즈2시작후 공격 유저가 수비 유저를 공격 했으므로 허용");
                                return;
                            }
                        }
                    } else if (war.isPhase1Start) {
                        if (damagedEntity instanceof Player damagedPlayer) {
                            User damagedUser = Database.users.get(damagedPlayer.getUniqueId());
                            if (war.isDefendUser(damagedUser)) {
                                if (!NationUtils.isChunkInSpecificNations(damagedPlayer.getLocation().getChunk(), war.getDefendNations())) {
                                    player.sendMessage("페이즈1시작후 공격 유저가 수비국이 아닌 곳에서 수비 유저를 공격 했으므로 허용");
                                    return;
                                }
                            }
                        }
                    }
                } else if (war.isDefendUser(user)) {
                    if (war.isPhase1Start) {
                        if (NationUtils.isChunkInSpecificNations(damagedEntityChunk, war.getAttackNations())) {//수비자가 공격국에서 벌어난 일은 모든지 허용
                            player.sendMessage("페이즈1시작후 수비 유저가 공격국 연합에서 엔티티공격 했으므로 허용");
                            return;
                        }
                        if (damagedEntity instanceof Player damagedPlayer) {
                            User damagedUser = Database.users.get(damagedPlayer.getUniqueId());
                            if (war.isAttackUser(damagedUser)) {
                                player.sendMessage("페이즈1시작후 수비 유저가 공격 유저를 공격 했으므로 허용");
                                return;
                            }
                        }
                    }
                }
            }
            if (NationUtils.isChunkInNationsExceptNation(damagedEntityChunk, damagerPlayerNation)){
                player.sendMessage("나라있는 유저가 자신의 나라가 아닌 다른 나라에서 엔티티공격 했으므로 비허용");
                event.setCancelled(true);
                return;
            }
        }
        if (event.getDamager() instanceof Projectile projectile) {
            if (projectile.getShooter() instanceof Player damagerPlayer){
                if (event.getEntity() instanceof Player damagedPlayer) {
                    User damagedUser = Database.users.get(damagedPlayer.getUniqueId());
                    Nation damagedPlayerNation = damagedUser.getNation();
                    User damagerUser = Database.users.get(damagerPlayer.getUniqueId());
                    Nation damagerPlayerNation = damagerUser.getNation();

                    Chunk damagedPlayerChunk = damagedPlayer.getLocation().getChunk();
                    if (damagedPlayerNation == null) {
                        if (NationUtils.isChunkInNations(damagedPlayerChunk)) {
                            damagerPlayer.sendMessage("나라없는 유저가 날린 투사체가 나라에서 맞은 경우 비허용");
                            event.setCancelled(true);
                        }
                        return;
                    }
                    War war = Database.getRelatedWar(damagerPlayerNation);
                    if (war != null && !war.isPhase3Start) {
                        if (war.isAttackUser(damagerUser)) {
                            if (war.isPhase2Start) {
                                if (war.isDefendUser(damagedUser)) {
                                    damagerPlayer.sendMessage("페이즈2시작후 공격 유저가 날린 투사체가 수비 유저가 맞은 경우 허용");
                                    return;
                                }
                            } else if (war.isPhase1Start) {
                                if (war.isDefendUser(damagedUser)) {
                                    if (!NationUtils.isChunkInSpecificNations(damagedPlayerChunk, war.getDefendNations())) {//방어국 연합 내에 있을때는 공격 무효
                                        damagerPlayer.sendMessage("페이즈1시작후 공격 유저가 날린 투사체가 수비국이 아닌 곳에서 수비 유저가 맞은 경우 허용");
                                        return;
                                    }
                                }
                            }
                        } else if (war.isDefendUser(damagerUser)) {
                            if (war.isPhase1Start) {
                                if (war.isAttackUser(damagedUser)) {
                                    damagerPlayer.sendMessage("페이즈1시작후 수비 유저가 날린 투사체가 공격 유저가 맞은 경우 허용");
                                    return;
                                }
                            }
                        }
                    }
                    if (NationUtils.isChunkInNationsExceptNation(damagedPlayerChunk, damagedPlayerNation)) {
                        damagerPlayer.sendMessage("나라 있는 유저가 날린 투사체가 자신이 아닌 다른 나라에 위치한 엔티티가 맞은 경우 비허용");
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }
}
