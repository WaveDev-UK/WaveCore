package org.stellardev.wavecore.command;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class WaveSubCommand implements WaveCommandFrame {

    private final String label;
    private String permission;
    private String usage;


}
