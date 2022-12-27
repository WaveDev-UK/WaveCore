package org.stellardev.wavecore.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.stellardev.wavecore.configuration.Serializable;
import org.stellardev.wavecore.file.YamlFile;

import java.util.HashMap;
import java.util.Map;

@Getter
public class TitleMessage implements Serializable {

    private final String header;
    private final String footer;

    @Setter
    private int duration = 20;

    public TitleMessage(String header, String footer) {
        this.header = header;
        this.footer = footer;
    }
    public TitleMessage(String header, String footer, int duration) {
        this(header, footer);
        this.duration = duration;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("header", header);
        map.put("footer", footer);
        if (duration != 20) {
            map.put("duration", duration);
        }
        return map;
    }

    public static TitleMessage deserialize(YamlFile yamlFile, String path) {
        YamlConfiguration c = yamlFile.getConfig();
        String header = c.getString(path + ".header");
        String footer = c.getString(path + ".footer");
        TitleMessage titleMessage = new TitleMessage(header, footer);
        if (c.contains(path + ".duration")) {
            titleMessage.setDuration(c.getInt(path + ".duration"));
        }
        return titleMessage;
    }

}
