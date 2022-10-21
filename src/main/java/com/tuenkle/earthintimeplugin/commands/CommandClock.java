package com.tuenkle.earthintimeplugin.commands;

import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

import static com.tuenkle.earthintimeplugin.recipes.ClockRecipes.emptyclock;
import static com.tuenkle.earthintimeplugin.utils.GeneralUtils.secondToUniversalTime;


public class CommandClock implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args[0].equals("give")) {
                if (sender.isOp()){
                    if (args[1].equals("empty")){
                        player.getInventory().addItem(emptyclock);
                    } else {
                        try {
                            long seconds = Long.parseLong(args[1]);
                            if (seconds <= 0) {
                                player.sendMessage("0초 이하는 담을 수 없습니다.");
                                return true;
                            }
                            Bukkit.getLogger().info(secondToUniversalTime(seconds));
                            player.sendMessage(secondToUniversalTime(seconds));
                            ItemStack clock = new ItemStack(Material.CLOCK);
                            ItemMeta clockMeta = clock.getItemMeta();
                            clockMeta.setDisplayName(ChatColor.GREEN + secondToUniversalTime(seconds));
                            clockMeta.addEnchant(Enchantment.LUCK, 1, false);
                            clockMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
                            ArrayList<String> lore = new ArrayList<>();
                            lore.add(Long.toString(seconds));
                            clockMeta.setLore(lore);
                            clock.setItemMeta(clockMeta);
                            player.getInventory().addItem(clock);
                            return true;
                        } catch (Exception e) {
                            player.sendMessage("숫자가 아닙니다. or 너무 큰 수입니다.");
                        }
                    }
                }
            } else if (args[0].equals("set")) {
                if (player.getInventory().getItemInMainHand().isSimilar(emptyclock)){
                    try {
                        long seconds = Long.parseLong(args[1]);
                        if (seconds <= 0) {
                            player.sendMessage("0초 이하는 담을 수 없습니다.");
                            return true;
                        }
                        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
                        player.sendMessage(secondToUniversalTime(seconds));
                        ItemStack clock = new ItemStack(Material.CLOCK);
                        ItemMeta clockMeta = clock.getItemMeta();
                        clockMeta.setDisplayName(ChatColor.GREEN + secondToUniversalTime(seconds));
                        clockMeta.addEnchant(Enchantment.LUCK, 1, false);
                        clockMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
                        ArrayList<String> lore = new ArrayList<>();
                        lore.add(Long.toString(seconds));
                        clockMeta.setLore(lore);
                        clock.setItemMeta(clockMeta);
                        User user = Database.users.get(player.getUniqueId());
                        user.withdrawMoney(seconds);
                        player.getInventory().addItem(clock);
                    } catch (Exception e) {
                        player.sendMessage("숫자가 아닙니다. or 너무 큰 수입니다.");
                    }
                }
            }
        }
        return true;
    }
}

