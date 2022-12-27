package org.stellardev.wavecore.command;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.stellardev.wavecore.message.CoreMessages;

import java.util.HashMap;
import java.util.Map;


public abstract class WaveCommand extends Command implements WaveCommandFrame {

    private final Map<String, WaveSubCommand> subCommands;

    @Getter
    @Setter
    private boolean requiresPlayer = false;

    public WaveCommand(String name) {
        super(name);
        this.subCommands = new HashMap<>();
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        WaveCommandSender wCS = WaveCommandSender.of(commandSender);
        if(strings.length == 0){
            if(!wCS.isAuthorised(this)){
                CoreMessages.NO_PERMISSION.send(wCS);
                return true;
            }

            return perform(wCS, strings);
        }

        if(getSubCommand(strings[0]) == null){
            if(wCS.isAuthorised(this)) {
                return perform(wCS, strings);
            }else{
                CoreMessages.NO_PERMISSION.send(wCS);
                return true;
            }
        }

        WaveSubCommand subCommand = getSubCommand(strings[0]);
        if(!wCS.isAuthorised(subCommand)){
            CoreMessages.NO_PERMISSION.send(wCS);
            return true;
        }

        subCommand.perform(wCS, strings);
        return true;
    }

    public WaveSubCommand getSubCommand(String label){
        return subCommands.get(label);
    }

    public void addSubCommands(WaveSubCommand... waveSubCommands){
        for(WaveSubCommand waveSubCommand : waveSubCommands){
            subCommands.put(waveSubCommand.getLabel(), waveSubCommand);
        }
    }

}
