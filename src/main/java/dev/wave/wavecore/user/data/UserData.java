package dev.wave.wavecore.user.data;

import java.util.UUID;

public abstract class UserData {

    private final UUID uuid;

    public UserData(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUniqueId() {
        return uuid;
    }

}
