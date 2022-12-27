package org.stellardev.wavecore.plugin;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.stellardev.wavecore.command.WaveCommand;

import java.lang.reflect.Field;

public abstract class WavePlugin extends JavaPlugin {

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
