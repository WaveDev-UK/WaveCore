package org.stellardev.wavecore.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.stellardev.wavecore.configuration.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public class EnchantmentWrapper implements Serializable {

    private final Enchantment enchantment;
    private final int level;

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        Method namespacedKeyMethod;
        try{
           namespacedKeyMethod = enchantment.getClass().getMethod("getKey");
            NamespacedKey namespacedKey = enchantment.getKey();
            map.put("enchantment", namespacedKey.getKey());
        }catch (NoSuchMethodException e){
            map.put("enchantment", enchantment.getName());
        }

        map.put("level", level);
        return map;
    }
}