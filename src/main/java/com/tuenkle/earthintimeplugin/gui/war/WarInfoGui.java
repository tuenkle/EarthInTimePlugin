package com.tuenkle.earthintimeplugin.gui.war;

import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.database.War;
import com.tuenkle.earthintimeplugin.gui.buttons.GeneralButtons;
import com.tuenkle.earthintimeplugin.utils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WarInfoGui extends WarGui{

    public ItemStack getAttackNationsButton() {
        return getButtonWithLores(Material.RED_BANNER, ChatColor.WHITE + "공격국 목록"
        , ChatColor.GRAY + "공격국 목록을 보려면 클릭하세요!");
    }
    public ItemStack getAttackNationButton() {
        return getButtonWithLores(Material.RED_WOOL, ChatColor.WHITE + "공격국"
                , ChatColor.GREEN + war.getAttackNation().getName());
    }
    public ItemStack getDefendNationButton() {
        return getButtonWithLores(Material.BLUE_WOOL, ChatColor.WHITE + "수비국"
                , ChatColor.GREEN + war.getDefendNation().getName());
    }
    public ItemStack getDefendNationsButton() {
        return getButtonWithLores(Material.BLUE_BANNER, ChatColor.WHITE + "수비국 목록"
                , ChatColor.GRAY + "수비국 목록을 보려면 클릭하세요!");
    }

    public ItemStack getStartTimeButton(boolean isCurrent) {
        ItemStack clock = new ItemStack(Material.CLOCK);
        if (isCurrent) {
            ItemMeta clockMeta = clock.getItemMeta();
            clockMeta.addEnchant(Enchantment.LUCK, 1, false);
            clockMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            clock.setItemMeta(clockMeta);
        }
        return getButtonWithLoresAndItemStack(clock, ChatColor.GREEN + "전쟁 시작 시간",
                ChatColor.GREEN + GeneralUtils.dateTimeFormatter(war.getStartTime()));
    }
    public ItemStack getPhase1TimeButton(boolean isCurrent) {
        ItemStack clock = new ItemStack(Material.CLOCK);
        if (isCurrent) {
            ItemMeta clockMeta = clock.getItemMeta();
            clockMeta.addEnchant(Enchantment.LUCK, 1, false);
            clockMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            clock.setItemMeta(clockMeta);

        }
        return getButtonWithLoresAndItemStack(clock, ChatColor.GREEN + "공격국 방어 시작 시간",
                ChatColor.GREEN + GeneralUtils.dateTimeFormatter(war.getPhase1Time()),
                ChatColor.GRAY + "이 시간이 지나면 전쟁 종료시까지 수비국 연합은 공격국 연합을 공격할 수 있습니다.",
                ChatColor.GRAY + "이 시간이 지나면 공격국 연합에 다른 나라가 참전할 수 없습니다.");
    }
    public ItemStack getPhase2TimeButton(boolean isCurrent) {
        ItemStack clock = new ItemStack(Material.CLOCK);
        if (isCurrent) {
            ItemMeta clockMeta = clock.getItemMeta();
            clockMeta.addEnchant(Enchantment.LUCK, 1, false);
            clockMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        if (war.getDefendNation().isUserKing(user)) {
            if (war.isScheduled()) {
                return getButtonWithLoresAndItemStack(clock, ChatColor.GREEN + "수비국 방어 시작 시간",
                        ChatColor.GREEN + "(확정) " + GeneralUtils.dateTimeFormatter(war.getPhase2Time()),
                        ChatColor.GRAY + "이 시간이 지나면 1시간동안 공격국 연합은 수비국을 공격할 수 있습니다.",
                        ChatColor.GRAY + "이 시간이 지나면 수비국 연합에 다른 나라가 참전할 수 없습니다.",
                        ChatColor.GRAY + "수비국 왕은 1회에 한해 해당 시간을 설정할 수 있습니다.",
                        ChatColor.GRAY + "미설정시 전쟁 선포 11시간 이후로 설정됩니다.");
            }
            return getButtonWithLoresAndItemStack(clock, ChatColor.GREEN + "수비국 방어 시작 시간",
                    ChatColor.GREEN + "(미확정) " + GeneralUtils.dateTimeFormatter(war.getPhase2Time()),
                    ChatColor.RED + "시계를 클릭하여 방어 시작 시간을 설정하세요!.",
                    ChatColor.GRAY + "이 시간이 지나면 1시간동안 공격국 연합은 수비국을 공격할 수 있습니다.",
                    ChatColor.GRAY + "이 시간이 지나면 수비국 연합에 다른 나라가 참전할 수 없습니다.",
                    ChatColor.GRAY + "수비국 왕은 1회에 한해 해당 시간을 설정할 수 있습니다.",
                    ChatColor.GRAY + "미설정시 전쟁 선포 11시간 이후로 설정됩니다.");
        } else {
            if (war.isScheduled()) {
                return getButtonWithLoresAndItemStack(clock, ChatColor.GREEN + "수비국 방어 시작 시간",
                        ChatColor.GREEN + "(확정) " + GeneralUtils.dateTimeFormatter(war.getPhase2Time()),
                        ChatColor.GRAY + "이 시간이 지나면 1시간동안 공격국 연합은 수비국을 공격할 수 있습니다.",
                        ChatColor.GRAY + "이 시간이 지나면 수비국 연합에 다른 나라가 참전할 수 없습니다.",
                        ChatColor.GRAY + "수비국 왕은 1회에 한해 해당 시간을 설정할 수 있습니다.",
                        ChatColor.GRAY + "미설정시 전쟁 선포 11시간 이후로 설정됩니다.");
            }
            return getButtonWithLoresAndItemStack(clock, ChatColor.GREEN + "수비국 방어 시작 시간",
                    ChatColor.GREEN + "(미확정) " + GeneralUtils.dateTimeFormatter(war.getPhase2Time()),
                    ChatColor.GRAY + "이 시간이 지나면 1시간동안 공격국 연합은 수비국을 공격할 수 있습니다.",
                    ChatColor.GRAY + "이 시간이 지나면 수비국 연합에 다른 나라가 참전할 수 없습니다.",
                    ChatColor.GRAY + "수비국 왕은 1회에 한해 해당 시간을 설정할 수 있습니다.",
                    ChatColor.GRAY + "미설정시 전쟁 선포 11시간 이후로 설정됩니다.");
        }
    }
    public ItemStack getEndTimeButton(boolean isCurrent) {
        ItemStack clock = new ItemStack(Material.CLOCK);
        if (isCurrent) {
            ItemMeta clockMeta = clock.getItemMeta();
            clockMeta.addEnchant(Enchantment.LUCK, 1, false);
            clockMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            clock.setItemMeta(clockMeta);
        }
        if (war.isScheduled()) {
            return getButtonWithLoresAndItemStack(clock, ChatColor.GREEN + "전쟁 종료 시간",
                    ChatColor.GREEN + "(확정)" + GeneralUtils.dateTimeFormatter(war.getPhase3Time()),
                    ChatColor.GRAY + "이 시간이 되면 전쟁이 종료됩니다.");
        }
        return getButtonWithLoresAndItemStack(clock, ChatColor.GREEN + "전쟁 종료 시간",
                ChatColor.GREEN + "(미확정)" + GeneralUtils.dateTimeFormatter(war.getPhase3Time()),
                ChatColor.GRAY + "이 시간이 되면 전쟁이 종료됩니다.");

    }
    public WarInfoGui(War war, User user) {
        super(war, user);
    }
    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, 54, "전쟁 정보");
        setDefaultInventory(inventory);
        inventory.setItem(4, getWarTitleButton());
        inventory.setItem(20, getAttackNationsButton());
        inventory.setItem(21, getAttackNationButton());
        inventory.setItem(23, getDefendNationButton());
        inventory.setItem(24, getDefendNationsButton());
        Nation userNation = user.getNation();
        if (war.isPhase3Start) {
            inventory.setItem(15, getEndTimeButton(true));
            inventory.setItem(11, getStartTimeButton(false));
            inventory.setItem(12, getPhase1TimeButton(false));
            inventory.setItem(14, getPhase2TimeButton(false));
            if (userNation != null) {
                if (userNation.isUserKing(user)) {
                    if (war.getAttackNations().contains(userNation)) {
                        inventory.setItem(19, GeneralButtons.warRecoveryButton);
                    } else if (userNation == war.getDefendNation()) {
                        inventory.setItem(25, GeneralButtons.warRecoveryButton);
                    }
                }
            }
        } else if (war.isPhase2Start) {
            inventory.setItem(15, getEndTimeButton(false));
            inventory.setItem(11, getStartTimeButton(false));
            inventory.setItem(12, getPhase1TimeButton(false));
            inventory.setItem(14, getPhase2TimeButton(true));
            if (userNation != null) {
                if (userNation.isUserKing(user)) {

                }
            }
        } else if (war.isPhase1Start) {
            inventory.setItem(15, getEndTimeButton(false));
            inventory.setItem(11, getStartTimeButton(false));
            inventory.setItem(12, getPhase1TimeButton(true));
            inventory.setItem(14, getPhase2TimeButton(false));
            if (userNation != null) {
                if (userNation.isUserKing(user)) {
                    if (userNation == war.getDefendNation()) {
                        inventory.setItem(25, GeneralButtons.defendJoinNationListButton);
                    } else if (war.defendJoinApplicationNations.contains(userNation)) {
                        inventory.setItem(25, GeneralButtons.defendJoinInfoButton);
                    } else if (!war.getAttackNations().contains(userNation) && !war.getDefendNations().contains(userNation)){
                        inventory.setItem(25, GeneralButtons.defendJoinButton);
                    }
                }
            }
        } else {
            inventory.setItem(15, getEndTimeButton(false));
            inventory.setItem(11, getStartTimeButton(true));
            inventory.setItem(12, getPhase1TimeButton(false));
            inventory.setItem(14, getPhase2TimeButton(false));
            if (userNation != null) {
                if (userNation.isUserKing(user)) {
                    if (userNation == war.getAttackNation()) {
                        inventory.setItem(19, GeneralButtons.attackJoinNationListButton);
                    } else if (userNation == war.getDefendNation()) {
                        inventory.setItem(25, GeneralButtons.defendJoinNationListButton);
                    } else if (war.attackJoinApplicationNations.contains(userNation)) {
                        inventory.setItem(19, GeneralButtons.attackJoinInfoButton);
                    } else if (war.defendJoinApplicationNations.contains(userNation)) {
                        inventory.setItem(25, GeneralButtons.defendJoinInfoButton);
                    } else if (!war.getAttackNations().contains(userNation) && !war.getDefendNations().contains(userNation)){
                        inventory.setItem(19, GeneralButtons.attackJoinButton);
                        inventory.setItem(25, GeneralButtons.defendJoinButton);
                    }
                }
            }
        }
        return inventory;
    }
}
