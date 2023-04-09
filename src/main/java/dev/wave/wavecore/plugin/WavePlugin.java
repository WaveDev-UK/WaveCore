package dev.wave.wavecore.plugin;

import dev.wave.wavecore.command.WaveCommand;
import dev.wave.wavecore.configuration.Configuration;
import dev.wave.wavecore.file.YamlFile;
import dev.wave.wavecore.gui.menu.listeners.MenuListener;
import dev.wave.wavecore.message.CoreMessages;
import dev.wave.wavecore.module.ModuleManager;
import dev.wave.wavecore.user.UserDataModule;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;

public abstract class WavePlugin extends JavaPlugin {

    public static File DIRECTORY = null;

    @Getter
    private ModuleManager moduleManager;
    public void init(WavePlugin instance){

        String directory = getDataFolder().getAbsolutePath().split("plugins")[0];

        File file = new File(directory + "plugins" + File.separator + "WaveCore");

        DIRECTORY = file;
        this.moduleManager = new ModuleManager();

        this.moduleManager.initialiseModules(new UserDataModule(this));

        this.moduleManager.loadModules();
        if(!file.exists()){
            file.mkdir();
        }

        Configuration.loadConfig(new YamlFile("messages.yml", file.getAbsolutePath(), null, this), CoreMessages.values());
        registerEvents(new MenuListener());
    }
    public void registerEvents(Listener... listeners){
        for(Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    @SneakyThrows
    public void registerCommands(WaveCommand... commands){
        final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        bukkitCommandMap.setAccessible(true);
        CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

        for(WaveCommand command : commands) {
            commandMap.register(command.getLabel(), command);
        }
    }

}
