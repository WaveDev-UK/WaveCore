package dev.wave.wavecore.user;


import dev.wave.wavecore.module.Module;
import dev.wave.wavecore.plugin.WavePlugin;
import dev.wave.wavecore.user.manager.UserManager;
import lombok.Getter;


@Getter
public class UserDataModule  extends Module {

    @Getter
    private static UserDataModule instance;
    private UserManager userManager;

    private WavePlugin waveInstance;
    public UserDataModule(WavePlugin instance) {

        super("UserDataModule");
        this.waveInstance = instance;
    }
    public void enable() {
        instance = this;
       this.userManager = new UserManager(waveInstance);
    }

    public void disable() {
        instance = null;
    }
}
