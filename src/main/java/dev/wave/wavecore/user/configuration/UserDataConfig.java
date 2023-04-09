package dev.wave.wavecore.user.configuration;

import dev.wave.wavecore.configuration.Configuration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@AllArgsConstructor
public enum UserDataConfig implements Configuration {

    SAVE_INTERVAL("save-interval", 180);

    private final String path;
    @Setter
    private Object value;

}
