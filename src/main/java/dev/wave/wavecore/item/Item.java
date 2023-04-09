package dev.wave.wavecore.item;

import dev.wave.wavecore.configuration.Serializable;
import dev.wave.wavecore.util.Pair;
import dev.wave.wavecore.util.Placeholder;
import dev.wave.wavecore.util.Text;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import dev.wave.wavecore.file.YamlFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Getter
public class Item implements Serializable {

    private String material;
    private Byte data;
    private String name;
    private List<String> lore;
    private List<Pair<Enchantment, Integer>> enchantments;
    private List<ItemFlag> itemFlags;
    private int amount;

    private String skull;
    private int modelData;

    public ItemStack toItemStack(Placeholder... placeholders) {
        if (material == null && skull == null) {
            return null;
        }


        ItemStack itemStack = skull == null? new ItemStack(Material.valueOf(material), amount, data == null? 0: data): SkullCreator.getSkull(skull);

        if(itemStack == null){
            return null;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();

        assert itemMeta != null;
        if (name != null) {
            itemMeta.setDisplayName(Text.c(Placeholder.apply(name, placeholders)));
        }

        if(modelData > -1){
            try {
                itemMeta.setCustomModelData(modelData);
            }catch (NoSuchMethodError e){

            }
        }

        if (lore != null) {
            List<String> loreLines = new ArrayList<>();
            for (String loreLine : lore) {
                loreLines.add(Text.c(Placeholder.apply(loreLine, placeholders)));
            }
            itemMeta.setLore(loreLines);
        }

        if (itemFlags != null) {
            itemMeta.addItemFlags(itemFlags.toArray(new ItemFlag[0]));
        }

        if (enchantments != null) {
            for (Pair<Enchantment, Integer> enchantment : enchantments) {
                itemStack.addUnsafeEnchantment(enchantment.getKey(), enchantment.getValue());
            }
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        if(material != null) {
            map.put("id", material);
        }
        if(skull != null){
            map.put("skull", skull);
        }
        if (data != null) {
            map.put("data", data.intValue());
        }
        if (name != null) {
            map.put("name", name);
        }
        if (lore != null) {
            map.put("lore", lore);
        }
        map.put("amount", amount <= 0 ? 1 : amount);
        if (itemFlags != null) {
            List<String> itemFlags = new ArrayList<>();
            for (ItemFlag itemFlag : getItemFlags()) {
                itemFlags.add(itemFlag.name());
            }
            map.put("flags", itemFlags);
        }
        if (enchantments != null) {
            for (Pair<Enchantment, Integer> pair : enchantments) {
                map.put("enchantments." + pair.getKey().getName(), pair.getValue());
            }
        }

        if(modelData != -1){
            map.put("model-data", modelData);
        }

        return map;
    }



    public static Item deserialize(YamlFile yamlFile, String path) {
        YamlConfiguration c = yamlFile.getConfig();
        ItemBuilder build = Item.builder();
        if(!c.contains(path + ".id") && !c.contains(path + ".skull")){
            return null;
        }

        if(c.contains(path + ".id")) {
            build.material(c.getString(path + ".id"));
        }

        if(c.contains(path + ".skull")){
            build.skull(c.getString(path + ".skull"));
        }

        build.material(c.getString(path + ".id"));
        if (c.contains(path + ".data")) {
            build.data((byte) c.getInt(path + ".data"));
        }
        if (c.contains(path + ".name")) {
            build.name(c.getString(path + ".name"));
        }
        if (c.contains(path + ".lore")) {
            build.lore(c.getStringList(path + ".lore"));
        }

        if (c.contains(path + ".flags")) {
            List<ItemFlag> itemFlags = new ArrayList<>();
            for (String flagString : c.getStringList(path + ".flags")) {
                try {
                    ItemFlag itemFlag = ItemFlag.valueOf(flagString.toUpperCase());
                    itemFlags.add(itemFlag);
                } catch (IllegalArgumentException e) {

                }
            }
            build.itemFlags(itemFlags);
        }

        if (c.contains(path + ".enchantments")) {
            List<Pair<Enchantment, Integer>> enchantList = new ArrayList<>();
            for (String enchantKey : c.getConfigurationSection(path + ".enchantments").getKeys(false)) {
                Enchantment enchantment = Enchantment.getByName(enchantKey.toUpperCase());
                if (enchantment == null) {
                    continue;
                }

                enchantList.add(new Pair<>(enchantment, c.getInt(path + ".enchantments." + enchantKey)));
            }
            build.enchantments(enchantList);
        }

        if (c.contains(path + ".amount")) {
            build.amount(c.getInt(path + ".amount"));
        }

        if(c.contains(path + ".model-data")){
            build.modelData(c.getInt(path + ".model-data"));
        }

        return build.build();
    }

}
