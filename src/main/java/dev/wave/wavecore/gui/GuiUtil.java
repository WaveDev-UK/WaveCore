package dev.wave.wavecore.gui;

public class GuiUtil {

    public static int getScaledInventory(int size) {
        return size <= 9 ? 9
                : size <= 18 ? 18
                : size <= 27 ? 27
                : size <= 36 ? 36
                : size <= 45 ? 45
                : size <= 54 ? 54
                : 54;
    }

}
