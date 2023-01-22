package org.stellardev.wavecore.gui.menu.buttons;


import org.bukkit.inventory.ItemStack;
import org.stellardev.wavecore.gui.menu.Button;
import org.stellardev.wavecore.gui.menu.types.PagedMenu;
import org.stellardev.wavecore.item.Item;

import java.util.Collections;

public class PreviousButton extends Button {

//    private static final ItemStack PREVIOUS_BUTTON = new ItemBuilder("&c&lPrevious Page", SkullCreator.getSkull(ItemUtils.LEFT_ARROW_BASE))
//            .appendLore("&7Click to view the previous page")
//            .build();


    private static final ItemStack PREVIOUS_BUTTON = Item.builder()
            .name("&c&lPrevious Page")
            .lore(Collections.singletonList("&7Click to view the previous page"))
            .skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv" +
                    "M2ViZjkwNzQ5NGE5MzVlOTU1YmZjYWRhYjgxYmVhZmI5MGZiOWJlNDljNzAyNmJhOTdkNzk4ZDVmMWEyMyJ9fX0=")
            .build().toItemStack();



    public PreviousButton(ItemStack item, PagedMenu menu) {
        super(item, (player, event) -> {
            event.setCancelled(true);

            if (menu.getPrevious() != null) {
                menu.getPrevious().open(player);
                menu.setCurrentPageNumber(menu.getCurrentPageNumber() - 1);
            }
        });
    }

    public PreviousButton(PagedMenu menu) {
        this(PREVIOUS_BUTTON, menu);
    }

}
