package org.stellardev.wavecore.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@Getter
public class WaveCommandSender {

    private final CommandSender commandSender;

    public boolean isPlayer(){
        return commandSender instanceof Player;
    }

    public Player getPlayer(){
        return (Player) commandSender;
    }

    public ConsoleCommandSender getConsole(){
        return (ConsoleCommandSender) commandSender;
    }

    public static WaveCommandSender of(CommandSender commandSender){
        return new WaveCommandSender(commandSender);
    }

    public CommandAuthResponse authorise(WaveCommandFrame waveCommandFrame){
        if(!isPlayer() && waveCommandFrame.isRequiresPlayer()){
            return CommandAuthResponse.INVALID_SENDER;
        }

        if(waveCommandFrame.getPermission() != null && !commandSender.hasPermission(waveCommandFrame.getPermission())){
            return CommandAuthResponse.NO_PERMISSION;
        }

        return CommandAuthResponse.AUTHENTICATED;
    }

    public void sendMessage(String message){
        commandSender.sendMessage(message);
    }

}
