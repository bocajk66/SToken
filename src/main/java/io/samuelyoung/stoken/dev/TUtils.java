package io.samuelyoung.stoken.dev;

import org.bukkit.ChatColor;

import java.text.NumberFormat;
import java.util.Locale;

public class TUtils {

    public static String c(String msg) { return ChatColor.translateAlternateColorCodes('&', msg); }

    public static String formatNumber(long number) {
        return NumberFormat.getNumberInstance(Locale.US).format(number);
    }
}