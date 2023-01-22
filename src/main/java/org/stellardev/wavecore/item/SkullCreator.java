package org.stellardev.wavecore.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.stellardev.wavecore.util.BukkitVersion;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.UUID;

public class SkullCreator {

    public static ItemStack getSkull(String base64) {
        ItemStack item;
        if(BukkitVersion.getCurrent().isLessThan(new BukkitVersion(1, 13))) {
            item = new ItemStack(Material.getMaterial("SKULL_ITEM"), 1, (byte) 3);
        } else {
            item = new ItemStack(Material.PLAYER_HEAD, 1);
        }

        UUID hashAsId = new UUID(base64.hashCode(), base64.hashCode());
        return Bukkit.getUnsafe().modifyItemStack(item,
                "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + base64 + "\"}]}}}"
        );
    }

    public static ItemStack getSkullFromUrl(String url) {
        return getSkull(urlToBase64(url));
    }

    public static String urlToBase64(String url) {
        URI actualUrl;

        try {
            actualUrl = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        String toEncode = "{\"textures\":{\"SKIN\":{\"url\":\"" + actualUrl.toString() + "\"}}}";
        return Base64.getEncoder().encodeToString(toEncode.getBytes());
    }

    public static GameProfile getNonPlayerProfile(String base64) {
        UUID hashAsId = new UUID(base64.hashCode(), base64.hashCode());

        GameProfile newSkinProfile = new GameProfile(hashAsId, null);
        newSkinProfile.getProperties().put("textures", new Property("textures",
                Base64Coder.encodeString("{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + base64 + "\"}]}}}")));
        return newSkinProfile;
    }


}
