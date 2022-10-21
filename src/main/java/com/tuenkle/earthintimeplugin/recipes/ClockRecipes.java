package com.tuenkle.earthintimeplugin.recipes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collections;

import static com.tuenkle.earthintimeplugin.utils.GeneralUtils.secondToUniversalTime;

public class ClockRecipes {
    public static ItemStack emptyclock;

    public static ItemStack clock10m;

    public static ItemStack clock1h;

    public static void EmptyClock(Plugin plugin) {
        ItemStack clock = new ItemStack(Material.CLOCK);
        ItemMeta clockMeta = clock.getItemMeta();
        clockMeta.setDisplayName(secondToUniversalTime(0));
        ArrayList<String> lore = new ArrayList<>();
        lore.add("0");
        clockMeta.setLore(lore);
        clock.setItemMeta(clockMeta);
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "emptyclock"), clock);
        recipe.shape("SKS",
                "SKS",
                "SKS");
        recipe.setIngredient('S', Material.IRON_INGOT);
        recipe.setIngredient('K', Material.COAL);
        Bukkit.addRecipe(recipe);
        emptyclock = clock;
    }
    public static void Clock10Minute(Plugin plugin) {
        ItemStack clock = new ItemStack(Material.CLOCK);
        ItemMeta clockMeta = clock.getItemMeta();
        clockMeta.setDisplayName(secondToUniversalTime(600));
        clockMeta.addEnchant(Enchantment.LUCK, 1, false);
        clockMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("600");
        clockMeta.setLore(lore);
        clock.setItemMeta(clockMeta);
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "clock10m"), clock);
        recipe.shape("YOY", "OXO", "YOY");
        recipe.setIngredient('X', new RecipeChoice.ExactChoice(emptyclock));
        recipe.setIngredient('O', Material.IRON_INGOT);
        recipe.setIngredient('Y', Material.COAL);
        Bukkit.addRecipe(recipe);
        clock10m = clock;
    }
    public static void Clock1Hour(Plugin plugin) {
        ItemStack clock = new ItemStack(Material.CLOCK);
        ItemMeta clockMeta = clock.getItemMeta();
        clockMeta.setDisplayName(secondToUniversalTime(3600));
        clockMeta.addEnchant(Enchantment.LUCK, 1, false);
        clockMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("3600");
        clockMeta.setLore(lore);
        clock.setItemMeta(clockMeta);
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, "clock1h"), clock);
        recipe.shape("YOY", "OXO", "YOY");
        recipe.setIngredient('X', new RecipeChoice.ExactChoice(emptyclock));
        recipe.setIngredient('O', Material.IRON_BLOCK);
        recipe.setIngredient('Y', Material.COAL);
        Bukkit.addRecipe(recipe);
        clock1h = clock;
    }
}
