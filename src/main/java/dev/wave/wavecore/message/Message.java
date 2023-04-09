package dev.wave.wavecore.message;

import dev.wave.wavecore.configuration.Configuration;
import dev.wave.wavecore.util.Placeholder;
import dev.wave.wavecore.util.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import dev.wave.wavecore.command.WaveCommandSender;

import java.util.Arrays;
import java.util.List;

public interface Message extends Configuration {

    String getPrefix();

    Object getValue();

    default String getString() {
        return (String) getValue();
    }

    default List<String> getStringList() {
        return (List<String>) getValue();
    }

    default TitleMessage getTitleMessage() {
        return (TitleMessage) getValue();
    }

    default void send(WaveCommandSender player, Placeholder... placeholders) {

        if (player == null) {
            return;
        }

        try {
            sendList(player, placeholders);
            return;
        } catch (ClassCastException ignored) {

        }

        try {
            sentTitle(player.getPlayer(), placeholders);
            return;
        } catch (ClassCastException e) {

        }

        String text = this.getString();

        text = Placeholder.apply(text, placeholders);

        player.sendMessage(Text.c(text.replace("{prefix}", getPrefix())));
    }

    default void sendList(WaveCommandSender player, Placeholder... placeholders) {

        if (player == null) {
            return;
        }

        StringBuilder text = new StringBuilder();
        for (String message : this.getStringList()) {
            text.append(Placeholder.apply(message, placeholders)).append("\n");
        }

        player.sendMessage(Text.c(text.toString().replace("{prefix}", (String) getPrefix())));
    }

    default void broadcast(Placeholder... placeholders) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            send(WaveCommandSender.of(player), placeholders);
        }
    }

    default void sentTitle(Player player, Placeholder... placeholders) {
        TitleMessage titleMessage = getTitleMessage();

        List<Placeholder> placeholderList = Arrays.asList(placeholders);
        placeholderList.add(new Placeholder("{prefix}", getPrefix()));

        String header = Text.c(Placeholder.apply(titleMessage.getHeader(), placeholderList.toArray(new Placeholder[0])));
        String footer = Text.c(Placeholder.apply(titleMessage.getFooter(), placeholderList.toArray(new Placeholder[0])));

        player.sendTitle(header, footer, 1, titleMessage.getDuration(), 1);
    }

    default void broadcastTitle(Placeholder... placeholders) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sentTitle(player, placeholders);
        }
    }


}
