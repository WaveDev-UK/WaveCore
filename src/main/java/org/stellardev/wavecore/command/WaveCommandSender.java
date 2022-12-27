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

    public boolean isAuthorised(WaveCommandFrame waveCommandFrame){
        if(waveCommandFrame.getPermission() == null) return true;
        if(!isPlayer()){
            return true;
        }
        return getPlayer().hasPermission(waveCommandFrame.getPermission());
    }

    public void sendMessage(String message){
        commandSender.sendMessage(message);
    }

}
