package org.stellardev.wavecore.command;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.stellardev.wavecore.message.CoreMessages;
import org.stellardev.wavecore.util.Placeholder;

import java.util.Collection;
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

    public Collection<WaveSubCommand> getSubCommands(){
        return subCommands.values();
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        WaveCommandSender wCS = WaveCommandSender.of(commandSender);
        if(strings.length == 0 || getSubCommand(strings[0]) == null) {
            CommandAuthResponse authResponse = wCS.authorise(this);
            return authenticateCommand(this, strings, wCS, authResponse);
        }


        WaveSubCommand subCommand = getSubCommand(strings[0]);
        CommandAuthResponse authResponse = wCS.authorise(subCommand);
        return authenticateCommand(subCommand, strings, wCS, authResponse);
    }

    private boolean authenticateCommand(WaveCommandFrame waveCommandFrame, String[] strings, WaveCommandSender wCS, CommandAuthResponse authResponse) {
        switch (authResponse){
            case INVALID_SENDER:
                CoreMessages.INVALID_SENDER.send(wCS);
                return true;
            case NO_PERMISSION:
                CoreMessages.NO_PERMISSION.send(wCS);
                return true;
            case AUTHENTICATED:
                String usage = waveCommandFrame.getUsage();
                if(usage.split(" ").length -1 < strings.length) {
                    CoreMessages.INVALID_USAGE.send(wCS, new Placeholder("{usage}", usage));
                    return true;
                }
                return waveCommandFrame.perform(wCS, strings);
        }
        return false;
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
