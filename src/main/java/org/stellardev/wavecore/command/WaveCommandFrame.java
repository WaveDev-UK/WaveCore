package org.stellardev.wavecore.command;

public interface WaveCommandFrame {

    String getPermission();
    String getUsage();
    String getLabel();
    boolean perform(WaveCommandSender waveCommandSender, String[] args);

}
