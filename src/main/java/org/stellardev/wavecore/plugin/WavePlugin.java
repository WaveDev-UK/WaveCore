package org.stellardev.wavecore.plugin;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.stellardev.wavecore.command.WaveCommand;

public abstract class WavePlugin extends JavaPlugin {

    public void registerEvents(Listener... listeners){
        for(Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    public void registerCommands(WaveCommand... commands){
        for(WaveCommand command : commands) {

        }
    }

}
