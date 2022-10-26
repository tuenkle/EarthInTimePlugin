package com.tuenkle.earthintimeplugin.commands;

import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.database.War;
import com.tuenkle.earthintimeplugin.dynmap.NationDynmap;
import com.tuenkle.earthintimeplugin.gui.nation.NationMainGui;
import com.tuenkle.earthintimeplugin.gui.war.WarMainGui;
import com.tuenkle.earthintimeplugin.scheduler.ParticlesScheduler;
import com.tuenkle.earthintimeplugin.utils.GeneralUtils;
import com.tuenkle.earthintimeplugin.utils.NationUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.tuenkle.earthintimeplugin.utils.NationUtils.*;


public class CommandNation implements CommandExecutor {
    public CommandNation(JavaPlugin plugin){
        this.plugin = plugin;
    }
    private final JavaPlugin plugin;
    public static final long NATION_CREATION_MONEY = 3600L;
    public static final long NATION_INVITE_MONEY = 600L;
    public static final long WAR_DECLARATION_MONEY = 86400L;
    public static final int WAR_DECLARATION_MIN_TIME = 0;
    public static final int WAR_ATTACK_MIN_TIME = 0;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            UUID userUuid = player.getUniqueId();
            User user = Database.users.get(userUuid);

            if (args.length == 0) {
                player.openInventory(new NationMainGui(user).getInventory());
                return true;
            }
            switch (args[0]) {
                case "만들기" -> {
                    if (args.length != 2) {
                        player.sendMessage(ChatColor.YELLOW + "/나라 만들기 <나라이름> - " + "나라를 생성합니다.(비용: 1시간)");
                        return true;
                    }
                    if (user.getNation() != null) {
                        player.sendMessage("이미 나라가 있습니다.");
                        return true;
                    }
                    if (user.getMoney() <= NATION_CREATION_MONEY + 60) {
                        player.sendMessage("소지 시간이 부족합니다. 나라 생성 소모 시간: " + GeneralUtils.secondToUniversalTime(NATION_CREATION_MONEY));
                        return true;
                    }
                    String nationName = args[1];
                    if (Database.nations.containsKey(nationName)) {
                        player.sendMessage("이미 존재하는 나라 이름입니다.");
                        return true;
                    }
                    if (nationName.length() > 10) {
                        player.sendMessage("10자 이하의 나라 이름만 가능합니다.");
                        return true;
                    }
                    if (!GeneralUtils.isEnglishKorean(nationName)) {
                        player.sendMessage("영어와 한글로된 나라 이름만 가능합니다.");
                        return true;
                    }
                    Chunk playerChunk = player.getLocation().getChunk();
                    int[] chunk = {playerChunk.getX(), playerChunk.getZ()};
                    if (NationUtils.isIntChunkInNations(chunk)) {
                        player.sendMessage("이 땅은 이미 다른 나라가 소유중입니다.");
                        return true;
                    }
                    Nation nation = new Nation(nationName, user, chunk, player.getLocation());
                    user.withdrawMoney(NATION_CREATION_MONEY);
                    user.setNation(nation);
                    Database.nations.put(nationName, nation);
                    NationDynmap.drawNation(nation);
                    player.sendMessage("나라 생성 완료");
                    return true;
                }
                case "삭제" -> {
                    if (args.length != 1) {
                        player.sendMessage(ChatColor.YELLOW + "/나라 삭제");
                        return true;
                    }
                    Nation nation = user.getNation();
                    if (nation == null) {
                        player.sendMessage("소속된 나라가 없습니다.");
                        return true;
                    }
                    if (!nation.getKing().equals(user)) {
                        player.sendMessage("왕이 아닙니다.");
                        return true;
                    }
                    for (War war : Database.wars) {
                        if (war.getAttackNations().contains(nation)) {
                            player.sendMessage("공격 중에는 나라를 삭제할 수 없습니다.");
                            return true;
                        }
                    }
                    for (War war : Database.wars) {
                        if (war.getDefendNation() == nation) {
                            war.terminateWar();
                        }
                        war.getDefendNations().remove(nation);
                    }
                    for (Map.Entry<String, Nation> nationEntry : Database.nations.entrySet()) {
                        Nation oneNation = nationEntry.getValue();
                        oneNation.removeNationInAllyInvites(nation);
                        oneNation.removeNationInAllies(nation);
                    }
                    for (Map.Entry<User, LocalDateTime> resident : nation.getResidents().entrySet()) {
                        resident.getKey().setNation(null);
                    }
                    Database.removeNation(nation);
                    NationDynmap.eraseNation(nation);
                    player.sendMessage("나라 삭제 완료: " + nation.getName());
                    return true;
                }
                case "동맹" -> {
                    if (args.length != 3) {
                        player.sendMessage(ChatColor.YELLOW + "/나라 동맹 신청 <나라이름>", ChatColor.YELLOW + "/나라 동맹 수락 <나라이름>", ChatColor.YELLOW + "/나라 동맹 거절 <나라이름>", ChatColor.YELLOW + "/나라 동맹 파기 <나라이름>");
                        return true;
                    }
                    Nation nation = user.getNation();
                    if (nation == null) {
                        player.sendMessage("소속된 나라가 없습니다.");
                        return true;
                    }
                    if (!nation.getKing().equals(user)) {
                        player.sendMessage("왕이 아닙니다.");
                        return true;
                    }
                    String targetNationName = args[2];
                    Nation targetNation = Database.nations.get(targetNationName);
                    if (targetNation == null) {
                        player.sendMessage("존재하지 않는 나라입니다.");
                        return true;
                    }
                    switch (args[1]) {
                        case "신청" -> {
                            if (nation.getAllies().containsKey(targetNation)) {
                                player.sendMessage("이미 동맹인 국가입니다.");
                            }
                            if (targetNation.getAllyInvites().containsKey(nation)) {
                                player.sendMessage("이미 동맹 신청을 한 국가입니다.");
                                return true;
                            }
                            if (nation.getAllyInvites().containsKey(targetNation)) {
                                player.sendMessage("해당 국가로부터 이미 동맹 신청이 와있습니다.");
                                return true;
                            }
                            if (Database.isWarTogether(nation, targetNation)) {
                                player.sendMessage("해당 국가와 전쟁 중입니다.");
                                return true;
                            }
                            targetNation.getAllyInvites().put(nation, LocalDateTime.now());
                            player.sendMessage("동맹 신청 완료. " + targetNationName);
                            return true;
                        }
                        case "수락" -> {
                            if (!nation.getAllyInvites().containsKey(targetNation)) {
                                player.sendMessage(targetNationName + "국가로부터 온 동맹 신청 이력이 없습니다.");
                                return true;
                            }
                            nation.getAllyInvites().remove(targetNation);
                            nation.getAllies().put(targetNation, LocalDateTime.now());
                            targetNation.getAllies().put(nation, LocalDateTime.now());
                            player.sendMessage(targetNationName + "국가와 동맹이 되었습니다.");
                            return true;
                        }
                        case "거절" -> {
                            if (!nation.getAllyInvites().containsKey(targetNation)) {
                                player.sendMessage(targetNationName + "국가로부터 온 동맹 신청 이력이 없습니다.");
                                return true;
                            }
                            nation.getAllyInvites().remove(targetNation);
                            player.sendMessage(targetNationName + "국가로부터 온 동맹 신청을 거절했습니다.");
                            return true;
                        }
                        case "파기" -> {
                            if (!nation.getAllies().containsKey(targetNation)) {
                                player.sendMessage(targetNation + "국가와 동맹이 아닙니다.");
                                return true;
                            }
                            nation.getAllies().remove(targetNation);
                            targetNation.getAllies().remove(nation);
                            player.sendMessage(targetNationName + "국가와의 동맹을 파기하였습니다.");
                            return true;
                        }
                        default -> {
                            player.sendMessage(ChatColor.YELLOW + "/나라 동맹 신청 <나라이름>", ChatColor.YELLOW + "/나라 동맹 수락 <나라이름>", ChatColor.YELLOW + "/나라 동맹 거절 <나라이름>", ChatColor.YELLOW + "/나라 동맹 파기 <나라이름>");
                            return true;
                        }
                    }
                }
                case "전쟁" -> {
                    Nation nation = user.getNation();
                    if (args.length == 1) {
                        player.openInventory(new WarMainGui(user).getInventory());
                        return true;
                    }
                    switch (args[1]) {
                        case "선포" -> {
                            if (nation == null) {
                                player.sendMessage("소속된 나라가 없습니다.");
                                return true;
                            }
                            if (!nation.getKing().equals(user)) {
                                player.sendMessage("왕이 아닙니다.");
                                return true;
                            }
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                            if (LocalDateTime.now().isBefore(nation.getCreatedAt().plusHours(WAR_DECLARATION_MIN_TIME))) {
                                player.sendMessage("나라를 생성한지 48시간이 지나지 않았습니다. 전쟁은 나라 생성 후 48시간 이후에 선포할 수 있습니다. 전쟁 가능 시간: " + nation.getCreatedAt().plusHours(48).format(formatter));
                                return true;
                            }
                            if (nation.getMoney() <= WAR_DECLARATION_MONEY + 60) {
                                player.sendMessage("나라 시간이 부족합니다. 전쟁 선포 소모 시간: " + GeneralUtils.secondToUniversalTime(WAR_DECLARATION_MONEY));
                            }
                            String targetNationName = args[2];
                            Nation targetNation = Database.nations.get(targetNationName);
                            if (targetNation == null) {
                                player.sendMessage("존재하지 않는 나라입니다.");
                                return true;
                            }
                            if (nation == targetNation) {
                                player.sendMessage("본인 나라입니다.");
                                return true;
                            }
                            if (nation.getAllies().containsKey(targetNation)) {
                                player.sendMessage("해당 국가와 동맹국입니다. 동맹국에는 전쟁을 선포할 수 없습니다.");
                                return true;
                            }
                            if (Database.isNationInWar(nation)) {
                                player.sendMessage("현재 전쟁 중입니다. 전쟁은 동시에 할 수 없습니다.");
                                return true;
                            }
                            if (Database.isNationInWar(targetNation)) {
                                player.sendMessage("대상 나라가 현재 전쟁 중입니다.");
                                return true;
                            }
                            nation.getAllyInvites().remove(targetNation);
                            targetNation.getAllyInvites().remove(nation);
                            Database.wars.add(new War(nation, targetNation, LocalDateTime.now()));
                            player.sendMessage("전쟁을 선포하였습니다. 대상 국가: " + targetNationName);
                            Bukkit.broadcastMessage(String.format("%s 국가가 %s 국가에 전쟁을 선포하였습니다. 11시간안에 %s 국가는 전쟁시간을 확정하여야 합니다. 확정하지 못할시 전쟁은 %s에 시작됩니다.", nation.getName(), targetNationName, targetNationName, LocalDateTime.now().plusHours(11).format(formatter)));
                            return true;
                        }
                        case "참가" -> {
                            if (args.length != 5) {
                                player.sendMessage(ChatColor.YELLOW + "/나라 전쟁 선포 <나라이름> - 해당 나라에 전쟁을 선포합니다.(비용: 1일)");
                                return true;
                            }
                            if (nation == null) {
                                player.sendMessage("소속된 나라가 없습니다.");
                                return true;
                            }
                            if (!nation.getKing().equals(user)) {
                                player.sendMessage("왕이 아닙니다.");
                                return true;
                            }
                            switch (args[2]) {
                                case "공격" -> {
                                    String attackNationName = args[3];
                                    String defendNationName = args[4];
                                    Nation attackNation = Database.nations.get(attackNationName);
                                    Nation defendNation = Database.nations.get(defendNationName);
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                                    if (LocalDateTime.now().isBefore(nation.getCreatedAt().plusHours(WAR_DECLARATION_MIN_TIME))) {
                                        player.sendMessage("나라를 생성한지 48시간이 지나지 않았습니다. 공격은 나라 생성 후 48시간 이후에 참여할 수 있습니다. 전쟁 가능 시간: " + nation.getCreatedAt().plusHours(48).format(formatter));
                                        return true;
                                    }
                                    if (attackNation == null) {
                                        player.sendMessage("공격국에 해당하는 나라가 존재하지 않습니다.");
                                        return true;
                                    }
                                    if (defendNation == null) {
                                        player.sendMessage("수비국에 해당하는 나라가 존재하지 않습니다.");
                                        return true;
                                    }
                                    War war = Database.getWar(attackNation, defendNation);
                                    if (war == null) {
                                        player.sendMessage("전쟁을 찾을 수 없습니다.");
                                        return true;
                                    }
                                    if (war.getDefendNations().contains(nation) || war.getAttackNations().contains(nation)) {
                                        player.sendMessage("이미 해당 전쟁에 참가한 상태입니다.");
                                        return true;
                                    }
                                    if (LocalDateTime.now().isAfter(war.getPhase2Time())) {
                                        player.sendMessage("공격으로 전쟁에 참가할 수 있는 시간이 종료되었습니다.");
                                        return true;
                                    }
                                    if (!nation.getAllies().containsKey(attackNation)) {
                                        player.sendMessage(attackNationName + "국가와 동맹이 아닙니다.");
                                        return true;
                                    }
                                    if (LocalDateTime.now().isBefore(nation.getAllies().get(attackNation).plusHours(WAR_ATTACK_MIN_TIME))) {
                                        player.sendMessage(attackNationName + "국가와 동맹을 한지 24시간이 지나지 않았습니다. 공격으로 참가하려면 동맹을 한지 24시간이 되어야 합니다.");
                                        return true;
                                    }
                                    war.getAttackNations().add(nation);
                                    player.sendMessage("전쟁에 공격으로 참가하였습니다.");
                                    return true;
                                }
                                case "수비" -> {
                                    String attackNationName = args[3];
                                    String defendNationName = args[4];
                                    Nation attackNation = Database.nations.get(attackNationName);
                                    Nation defendNation = Database.nations.get(defendNationName);
                                    if (attackNation == null) {
                                        player.sendMessage("공격국에 해당하는 나라가 존재하지 않습니다.");
                                        return true;
                                    }
                                    if (defendNation == null) {
                                        player.sendMessage("수비국에 해당하는 나라가 존재하지 않습니다.");
                                        return true;
                                    }
                                    War war = Database.getWar(attackNation, defendNation);
                                    if (war == null) {
                                        player.sendMessage("전쟁을 찾을 수 없습니다.");
                                        return true;
                                    }
                                    if (war.getDefendNations().contains(nation) || war.getAttackNations().contains(nation)) {
                                        player.sendMessage("이미 해당 전쟁에 참가한 상태입니다.");
                                        return true;
                                    }
                                    if (LocalDateTime.now().isAfter(war.getPhase1Time())) {
                                        player.sendMessage("수비로 전쟁에 참가할 수 있는 시간이 종료되었습니다.");
                                        return true;
                                    }
                                    if (!nation.getAllies().containsKey(defendNation)) {
                                        player.sendMessage(defendNationName + "국가와 동맹이 아닙니다.");
                                        return true;
                                    }

                                    war.getDefendNations().add(nation);
                                    player.sendMessage("전쟁에 수비로 참가하였습니다.");
                                    return true;
                                }
                                default -> {
                                    player.sendMessage(ChatColor.YELLOW + "/나라 전쟁 선포 <나라이름> - 해당 나라에 전쟁을 선포합니다.(비용: 1일)");
                                    return true;
                                }
                            }
                        }
                        case "목록" -> {
                            StringJoiner warList = new StringJoiner(",");
                            for (War war : Database.wars) {
                                warList.add(war.getAttackNation().getName());
                                for (Nation oneNation : war.getAttackNations()) {
                                    warList.add(oneNation.getName());
                                }
                                warList.add("|");
                                warList.add(war.getDefendNation().getName());
                                for (Nation oneNation : war.getDefendNations()) {
                                    warList.add(oneNation.getName());
                                }
                            }
                            player.sendMessage(warList.toString());
                            return true;
                        }
                    }
                }
                case "초대" -> {
                    if (!(args.length == 2 || args.length == 3)) {
                        player.sendMessage(ChatColor.YELLOW + "/나라 초대 <유저이름> - 유저를 나라에 초대합니다.(비용: 10분)",
                                ChatColor.YELLOW + "/나라 초대 취소 <유저이름> - 유저에게 보낸 초대를 취소합니다.",
                                ChatColor.YELLOW + "/나라 초대 받은 목록 - 나에게 온 초대장을 확인합니다.",
                                ChatColor.YELLOW + "/나라 초대 보낸 목록 - 내가 보낸 초대장을 확인합니다.",
                                ChatColor.YELLOW + "/나라 초대 수락 <나라이름> - 해당 나라로 가입합니다.",
                                ChatColor.YELLOW + "/나라 초대 거절 <나라이름> - 해당 나라로부터 온 초대를 거절합니다.");
                        return true;
                    }

                    Nation nation = user.getNation();
                    if (args.length == 2) {
                        if (nation == null) {
                            player.sendMessage("소속된 나라가 없습니다.");
                            return true;
                        }
                        if (!nation.getKing().equals(user)) {
                            player.sendMessage("왕이 아닙니다.");
                            return true;
                        }
                        String targetUsername = args[1];
                        User targetUser = Database.getUserByUsername(targetUsername);
                        if (targetUser == null) {
                            player.sendMessage("존재하지 않는 유저입니다.");
                            return true;
                        }
                        if (targetUser.getNation() != null) {
                            player.sendMessage("해당 유저는 이미 나라에 소속되어 있습니다.");
                            return true;
                        }
                        if (nation.getInvites().containsKey(targetUser)) {
                            player.sendMessage("해당 유저에게 이미 초대가 간 상태입니다.");
                            return true;
                        }
                        if (nation.getMoney() < NATION_INVITE_MONEY + 60) {
                            player.sendMessage("시간이 부족합니다. 나라 초대 소모 시간: " + GeneralUtils.secondToUniversalTime(NATION_INVITE_MONEY));
                            return true;
                        }
                        nation.withdrawMoney(NATION_INVITE_MONEY);
                        nation.getInvites().put(targetUser, LocalDateTime.now());
                        Player targetPlayer = Bukkit.getPlayer(targetUser.getUuid());
                        if (targetPlayer != null) {
                            targetPlayer.sendMessage(nation.getName() + "국가로부터 초대가 왔습니다.");
                        }
                        player.sendMessage("초대 완료");
                        return true;
                    }
                    switch (args[1]) {
                        case "취소" -> {
                            if (nation == null) {
                                player.sendMessage("소속된 나라가 없습니다.");
                                return true;
                            }
                            if (!nation.getKing().equals(user)) {
                                player.sendMessage("왕이 아닙니다.");
                                return true;
                            }
                            String targetUsername = args[2];
                            User targetUser = Database.getUserByUsername(targetUsername);
                            if (targetUser == null) {
                                player.sendMessage("존재하지 않는 유저입니다.");
                                return true;
                            }
                            if (!nation.getInvites().containsKey(targetUser)) {
                                player.sendMessage("해당 유저에게 간 초대가 없습니다.");
                                return true;
                            }
                            nation.getInvites().remove(targetUser);
                            player.sendMessage("초대를 취소했습니다. 해당 유저: " + targetUsername);
                            return true;
                        }
                        case "거절" -> {
                            if (nation != null) {
                                player.sendMessage("이미 나라에 소속되어 있습니다.");
                                return true;
                            }
                            String targetNationName = args[2];
                            Nation targetNation = Database.nations.get(targetNationName);
                            if (targetNation == null) {
                                player.sendMessage("존재하지 않는 나라입니다.");
                                return true;
                            }
                            if (!targetNation.getInvites().containsKey(user)) {
                                player.sendMessage("해당 국가로부터 온 초대가 없습니다.");
                                return true;
                            }
                            targetNation.getInvites().remove(user);
                            player.sendMessage(targetNationName + "국가에서 온 초대를 거절했습니다.");
                        }
                        case "수락" -> {
                            if (nation != null) {
                                player.sendMessage("이미 나라에 소속되어 있습니다.");
                                return true;
                            }
                            String targetNationName = args[2];
                            Nation targetNation = Database.nations.get(targetNationName);
                            if (targetNation == null) {
                                player.sendMessage("존재하지 않는 나라입니다.");
                                return true;
                            }
                            if (!targetNation.getInvites().containsKey(user)) {
                                player.sendMessage("해당 국가로부터 온 초대가 없습니다.");
                                return true;
                            }
                            targetNation.getInvites().remove(user);
                            targetNation.getResidents().put(user, LocalDateTime.now());
                            user.setNation(targetNation);
                            player.sendMessage(targetNationName + "국가에 가입되었습니다.");
                        }
                        case "보낸" -> {
                            if (!args[2].equals("목록")) {
                                player.sendMessage(ChatColor.YELLOW + "/나라 초대 <유저이름> - 유저를 나라에 초대합니다.(비용: 10분)",
                                        ChatColor.YELLOW + "/나라 초대 취소 <유저이름> - 유저에게 보낸 초대를 취소합니다.",
                                        ChatColor.YELLOW + "/나라 초대 받은 목록 - 나에게 온 초대장을 확인합니다.",
                                        ChatColor.YELLOW + "/나라 초대 보낸 목록 - 내가 보낸 초대장을 확인합니다.",
                                        ChatColor.YELLOW + "/나라 초대 수락 <나라이름> - 해당 나라로 가입합니다.",
                                        ChatColor.YELLOW + "/나라 초대 거절 <나라이름> - 해당 나라로부터 온 초대를 거절합니다.");
                                return true;
                            }
                            if (nation == null) {
                                player.sendMessage("소속된 나라가 없습니다.");
                                return true;
                            }
                            if (!nation.getKing().equals(user)) {
                                player.sendMessage("왕이 아닙니다.");
                                return true;
                            }
                            StringJoiner invitedUsers = new StringJoiner(",");
                            for (User invitedUser : nation.getInvites().keySet()) {
                                invitedUsers.add(invitedUser.getName());
                            }
                            player.sendMessage(invitedUsers.toString());
                            return true;
                        }
                        case "받은" -> {
                            if (!args[2].equals("목록")) {
                                player.sendMessage(ChatColor.YELLOW + "/나라 초대 <유저이름> - 유저를 나라에 초대합니다.(비용: 10분)",
                                        ChatColor.YELLOW + "/나라 초대 취소 <유저이름> - 유저에게 보낸 초대를 취소합니다.",
                                        ChatColor.YELLOW + "/나라 초대 받은 목록 - 나에게 온 초대장을 확인합니다.",
                                        ChatColor.YELLOW + "/나라 초대 보낸 목록 - 내가 보낸 초대장을 확인합니다.",
                                        ChatColor.YELLOW + "/나라 초대 수락 <나라이름> - 해당 나라로 가입합니다.",
                                        ChatColor.YELLOW + "/나라 초대 거절 <나라이름> - 해당 나라로부터 온 초대를 거절합니다.");
                                return true;
                            }
                            StringJoiner invitedNations = new StringJoiner(",");
                            for (Nation oneNation : Database.nations.values()) {
                                if (oneNation.getInvites().containsKey(user)) {
                                    invitedNations.add(oneNation.getName());
                                }
                            }
                            player.sendMessage(invitedNations.toString());
                            return true;
                        }
                        default -> {
                            player.sendMessage(ChatColor.YELLOW + "/나라 초대 <유저이름> - 유저를 나라에 초대합니다.(비용: 10분)",
                                    ChatColor.YELLOW + "/나라 초대 취소 <유저이름> - 유저에게 보낸 초대를 취소합니다.",
                                    ChatColor.YELLOW + "/나라 초대 받은 목록 - 나에게 온 초대장을 확인합니다.",
                                    ChatColor.YELLOW + "/나라 초대 보낸 목록 - 내가 보낸 초대장을 확인합니다.",
                                    ChatColor.YELLOW + "/나라 초대 수락 <나라이름> - 해당 나라로 가입합니다.",
                                    ChatColor.YELLOW + "/나라 초대 거절 <나라이름> - 해당 나라로부터 온 초대를 거절합니다.");
                            return true;
                        }
                    }
                }
                case "떠나기" -> {
                    Nation nation = user.getNation();
                    if (nation == null) {
                        player.sendMessage("소속된 나라가 없습니다.");
                        return true;
                    }
                    if (nation.getKing().equals(user)) {
                        player.sendMessage("왕은 나라를 떠날 수 없습니다.");
                        return true;
                    }
                    //TODO-전쟁중일때
                    nation.getResidents().remove(user);
                    user.setNation(null);
                    player.sendMessage("나라를 떠났습니다.");
                    return true;
                }
                case "확장" -> {
                    Nation nation = user.getNation();
                    if (nation == null) {
                        player.sendMessage("소속된 나라가 없습니다.");
                        return true;
                    }
                    if (!nation.getKing().equals(user)) {
                        player.sendMessage("왕이 아닙니다.");
                        return true;
                    }
                    Chunk playerChunk = player.getLocation().getChunk();
                    int[] chunk = {playerChunk.getX(), playerChunk.getZ()};
                    player.sendMessage(nation.expand(chunk));
                    return true;
                }
                case "축소" -> {
                    Nation nation = user.getNation();
                    if (nation == null) {
                        player.sendMessage("소속된 나라가 없습니다.");
                        return true;
                    }
                    if (!nation.getKing().equals(user)) {
                        player.sendMessage("왕이 아닙니다.");
                        return true;
                    }
                    Chunk playerChunk = player.getLocation().getChunk();
                    int[] chunk = {playerChunk.getX(), playerChunk.getZ()};
                    player.sendMessage(nation.shrink(chunk));
                    return true;
                }
                case "스폰설정" -> {
                    Nation nation = user.getNation();
                    if (nation == null) {
                        player.sendMessage("소속된 나라가 없습니다.");
                        return true;
                    }
                    if (!nation.getKing().equals(user)) {
                        player.sendMessage("왕이 아닙니다.");
                        return true;
                    }
                    if (!isChunkInNation(player.getLocation().getChunk(), nation)){
                        player.sendMessage("본인 나라 안에 있지 않습니다.");
                        return true;
                    }
                    Location playerLocation = player.getLocation();
                    nation.setSpawn(player.getLocation());
                    player.sendMessage(String.format("나라 스폰 설정 완료. 좌표: %d, %d, %d", (int) playerLocation.getX(), (int) playerLocation.getY(), (int) playerLocation.getZ()));
                    return true;
                }
                case "스폰" -> {
                    Nation nation = user.getNation();
                    if (nation == null) {
                        player.sendMessage("소속된 나라가 없습니다.");
                        return true;
                    }
                    player.teleport(nation.getSpawn());
                    player.sendMessage("스폰 이동 완료");
                    return true;
                }
                case "강퇴" -> {
                    Nation nation = user.getNation();
                    if (nation == null) {
                        player.sendMessage("소속된 나라가 없습니다.");
                        return true;
                    }
                    if (!nation.getKing().equals(user)) {
                        player.sendMessage("왕이 아닙니다.");
                        return true;
                    }
                    String targetUsername = args[1];
                    User targetUser = Database.getUserByUsername(targetUsername);
                    if (targetUser == null) {
                        player.sendMessage("존재하지 않는 유저입니다.");
                        return true;
                    }
                    if (!targetUser.getNation().equals(nation)) {
                        player.sendMessage("나라에 소속되지 않은 유저입니다.");
                        return true;
                    }
                    nation.getResidents().remove(targetUser);
                    targetUser.setNation(null);
                    player.sendMessage("유저를 나라에서 내보냈습니다.");
                }
                case "경계" -> {
                    Nation nation = user.getNation();
                    if (nation == null) {
                        player.sendMessage("소속된 나라가 없습니다.");
                        return true;
                    }
                    if (nation.isParticleOn()) {
                        player.sendMessage("이미 실행중입니다.");
                        return true;
                    }
                    double particleY = player.getLocation().getY() + 2;
                    if (particleY > 315) {
                        player.sendMessage("플레이어의 위치가 너무 높습니다.");
                        return true;
                    }
                    new ParticlesScheduler(player, nation).runTaskTimer(this.plugin, 0, 20);
                    player.sendMessage("나라 청크의 경계를 표시합니다.(지속시간 20초)");
                    return true;
                }
                case "입금" -> {
                    Nation nation = user.getNation();
                    if (nation == null) {
                        player.sendMessage("소속된 나라가 없습니다.");
                        return true;
                    }
                    try {
                        long deposit = Long.parseLong(args[1]);
                        if (user.getMoney() <= deposit) {
                            player.sendMessage("시간이 충분하지 않습니다.");
                        }
                        user.withdrawMoney(deposit);
                        nation.depositMoney(deposit);
                        player.sendMessage("나라에 입금 완료. 나라 총 시간: " + GeneralUtils.secondToUniversalTime(nation.getMoney()));
                        return true;
                    } catch (Exception e) {
                        player.sendMessage("숫자가 아닙니다. or 너무 큰 수입니다.");
                        return true;
                    }
                }
                case "출금" -> {
                    Nation nation = user.getNation();
                    if (nation == null) {
                        player.sendMessage("소속된 나라가 없습니다.");
                        return true;
                    }
                    if (!nation.getKing().equals(user)) {
                        player.sendMessage("왕이 아닙니다.");
                        return true;
                    }
                    try {
                        long withdraw = Long.parseLong(args[1]);
                        if (nation.getMoney() <= withdraw) {
                            player.sendMessage("시간이 충분하지 않습니다.");
                        }
                        nation.withdrawMoney(withdraw);
                        user.depositMoney(withdraw);
                        player.sendMessage("나라에서 출금 완료. 나라 총 시간: " + GeneralUtils.secondToUniversalTime(nation.getMoney()));
                        return true;
                    } catch (Exception e) {
                        player.sendMessage("숫자가 아닙니다. or 너무 큰 수입니다.");
                        return true;
                    }
                }
            }
            //도움말
        }
        return false;
    }
}
