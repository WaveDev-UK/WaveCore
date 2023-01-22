package org.stellardev.wavecore.plugin;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.stellardev.wavecore.armor.ArmorTask;
import org.stellardev.wavecore.command.WaveCommand;
import org.stellardev.wavecore.configuration.Configuration;
import org.stellardev.wavecore.file.YamlFile;
import org.stellardev.wavecore.gui.menu.listeners.MenuListener;
import org.stellardev.wavecore.message.CoreMessages;

import java.io.File;
import java.lang.reflect.Field;

public abstract class WavePlugin extends JavaPlugin {

    public void init(){
        ArmorTask.setInstance(this);
        ArmorTask.init();
        String directory = getDataFolder().getAbsolutePath().split("plugins")[0];
        File file = new File(directory + "plugins" + File.separator + "WaveCore");

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
