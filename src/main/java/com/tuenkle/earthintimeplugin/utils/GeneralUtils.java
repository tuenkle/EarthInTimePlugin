package com.tuenkle.earthintimeplugin.utils;

import org.bukkit.ChatColor;

import java.util.regex.Pattern;

public class GeneralUtils {
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
    public static boolean isEnglishKorean(String string) {
        return Pattern.matches("^[0-9a-zA-Zㄱ-ㅎ가-힣 ]*$", string);
    }
    public static String secondToUniversalTime(long second) { //이 서버에서 가장 많은 연산을 하는 부분이므로 추후 완벽한 최적화를 이루어야 함. 진짜 추후에는 초정보만 보내고 클라이언트에서 연산하면 좋을 듯
        if (second <= 0) {
            return ChatColor.WHITE + "0000:000:00:00:00";
        } else if (second < 60) {
            return String.format("%s0000:000:00:00:%02d", ChatColor.RED, second);
        } else if (second < 3600) {
            return String.format("%s0000:000:00:%02d:%02d", ChatColor.YELLOW, second / 60, second % 60);
        } else if (second < 86400) {
            long rh = second % 3600;
            return String.format("%s0000:000:%02d:%02d:%02d", ChatColor.GREEN, second / 3600, rh / 60, rh % 60);
        } else if (second < 31536000) {
            long rd = second % 86400;
            long rh = rd % 3600;
            return String.format("%s0000:%03d:%02d:%02d:%02d", ChatColor.BLUE, second / 86400, rd / 3600, rh / 60, rh % 60);
        } else {
            long ry = second % 31536000;
            long rd = ry % 86400;
            long rh = rd % 3600;
            return String.format("%s%04d:%03d:%02d:%02d:%02d", ChatColor.DARK_PURPLE, second / 31536000, ry / 86400, rd / 3600, rh / 60, rh % 60);
        }
    }
}
