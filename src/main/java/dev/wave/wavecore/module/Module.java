package dev.wave.wavecore.module;

import dev.wave.wavecore.WaveCore;
import dev.wave.wavecore.command.WaveCommand;
import dev.wave.wavecore.plugin.WavePlugin;
import lombok.Getter;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public abstract class Module {

    private final String name;
    private List<Class<? extends Module>> dependencies;

    private final File directory;

    public Module(String name){
        this.name = name;
        this.dependencies = new ArrayList<>();
        this.directory = new File(WaveCore.DIRECTORY, name);
        if(!this.directory.exists()){
            this.directory.mkdirs();
        }
    }

    @SafeVarargs
    public final void addDependencies(Class<? extends Module>... dependencies){
        this.dependencies.addAll(Arrays.asList(dependencies));
    }

    public void registerListeners(WavePlugin instance, Listener... listeners){
      instance.registerEvents(listeners);
    }
    public abstract void enable();

    public abstract void disable();


    public void registerCommands(WavePlugin wavePlugin, WaveCommand... waveCommands){
        wavePlugin.registerCommands(waveCommands);
    }
    

}
