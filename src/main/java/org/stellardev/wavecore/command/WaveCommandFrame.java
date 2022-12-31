package org.stellardev.wavecore.command;

public interface WaveCommandFrame {

    String getPermission();
    String getUsage();
    String getLabel();

    boolean isRequiresPlayer();
    boolean perform(WaveCommandSender waveCommandSender, String[] args);

}
