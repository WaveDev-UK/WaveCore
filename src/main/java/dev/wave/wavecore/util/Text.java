package dev.wave.wavecore.util;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

@UtilityClass
public class Text {

    public static String c(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
