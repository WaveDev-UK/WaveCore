package org.stellardev.wavecore.gui.menu.buttons;


import org.bukkit.inventory.ItemStack;
import org.stellardev.wavecore.gui.menu.Button;
import org.stellardev.wavecore.gui.menu.types.PagedMenu;
import org.stellardev.wavecore.item.Item;

import java.util.Collections;

public class NextButton extends Button {

//    private static final ItemStack NEXT_BUTTON = new ItemBuilder("&c&lNext Page", SkullCreator.getSkull(ItemUtils.RIGHT_ARROW_BASE))
//            .appendLore("&7Click to view the next page")
//            .build();


    private static final ItemStack NEXT_BUTTON = Item.builder()
            .name("&c&lNext Page")
            .lore(Collections.singletonList("&7Click to view the next page"))
            .skull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWI2Zj" +
                    "FhMjViNmJjMTk5OTQ2NDcyYWVkYjM3MDUyMjU4NGZmNmY0ZTgzMjIxZTU5NDZiZDJlNDFiNWNhMTNiIn19fQ==")
            .build().toItemStack();

    public NextButton(ItemStack item, PagedMenu menu) {
        super(item, (player, event) -> {
            event.setCancelled(true);

            if (menu.getNext() != null) {
                menu.getNext().open(player);
                menu.setCurrentPageNumber(menu.getCurrentPageNumber() + 1);
            }
        });
    }

    public NextButton(PagedMenu menu) {
        this(NEXT_BUTTON, menu);
    }

}
