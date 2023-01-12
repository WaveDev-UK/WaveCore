package org.stellardev.wavecore.item;

import lombok.Builder;
import org.bukkit.enchantments.Enchantment;
import org.stellardev.wavecore.configuration.Serializable;

import java.util.Map;

@Builder
public class Item implements Serializable {

    private String name;
    private String material;
    private int amount;
    private Map<Enchantment, Integer> enchantments;


    @Override
    public Map<String, Object> serialize() {
        return null;
    }

}
