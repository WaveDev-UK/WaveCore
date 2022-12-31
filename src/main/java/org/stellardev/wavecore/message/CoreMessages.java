package org.stellardev.wavecore.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum CoreMessages implements Message{

    PREFIX("prefix", "&7[&bWaveCore&7] &f"),
    INVALID_SENDER("invalid-sender", "{prefix}&cYou must be a player to execute this command."),
    NO_PERMISSION("no-permission", "{prefix}&cYou do not have permission to do that."),;

    private final String path;
    @Setter
    private Object value;


    @Override
    public String getPrefix() {
        return PREFIX.getString();
    }
}
