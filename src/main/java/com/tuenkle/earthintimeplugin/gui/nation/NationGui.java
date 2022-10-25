package com.tuenkle.earthintimeplugin.gui.nation;

import com.tuenkle.earthintimeplugin.database.Nation;
import com.tuenkle.earthintimeplugin.database.User;
import com.tuenkle.earthintimeplugin.gui.Gui;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class NationGui extends Gui {
    protected final Nation nation;
    public Nation getNation() {
        return nation;
    }
    public ItemStack getNationRemovedButton() {
        return getButton(Material.BARRIER, "삭제된 나라입니다.");
    }
    public ItemStack getNationNullButton() {
        return getButtonWithLores(Material.BARRIER, "나라가 존재하지 않습니다.", "/나라 만들기 <나라이름>");
    }
    public NationGui(Nation nation, User user) {
        super(user);
        this.nation = nation;
    }
}
