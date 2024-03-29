package com.tuenkle.earthintimeplugin.vault;

import com.tuenkle.earthintimeplugin.database.Database;
import com.tuenkle.earthintimeplugin.database.User;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;

import java.util.List;

public class EconomyImplementer implements Economy {
    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double v) {
        return null;
    }

    @Override
    public String currencyNamePlural() {
        return null;
    }

    @Override
    public String currencyNameSingular() {
        return null;
    }

    @Override
    public boolean hasAccount(String s) {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }

    @Override
    public double getBalance(String s) {
        User user = Database.getUserByUsername(s);
        return user.getMoney();
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        User user = Database.users.get(offlinePlayer.getUniqueId());
        return user.getMoney();
    }

    @Override
    public double getBalance(String s, String s1) {
        User user = Database.getUserByUsername(s);
        return user.getMoney();
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        User user = Database.users.get(offlinePlayer.getUniqueId());
        return user.getMoney();
    }

    @Override
    public boolean has(String s, double v) {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        return false;
    }

    @Override
    public boolean has(String s, String s1, double v) {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        User user = Database.users.get(offlinePlayer.getUniqueId());
        user.withdrawMoney(v);
        return false;
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        User user = Database.getUserByUsername(s);
        user.withdrawMoney(v);
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        User user = Database.users.get(offlinePlayer.getUniqueId());
        user.withdrawMoney(v);
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        User user = Database.getUserByUsername(s);
        user.withdrawMoney(v);
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        User user = Database.users.get(offlinePlayer.getUniqueId());
        user.withdrawMoney(v);
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String s, double v) {
        User user = Database.getUserByUsername(s);
        user.depositMoney(v);
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        User user = Database.users.get(offlinePlayer.getUniqueId());
        user.depositMoney(v);
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        User user = Database.getUserByUsername(s);
        user.depositMoney(v);
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        User user = Database.users.get(offlinePlayer.getUniqueId());
        user.depositMoney(v);
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String s) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }
}
