package org.stellardev.wavecore.gui.menu.buttons;


import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.stellardev.wavecore.gui.menu.Button;
import org.stellardev.wavecore.gui.menu.types.PagedMenu;
import org.stellardev.wavecore.item.Item;
;import java.util.Collections;

public class RefreshButton extends Button {

//    private static final ItemStack REFRESH_BUTTON = new ItemBuilder("&c&lRefresh", Material.PAPER)
//            .appendLore("&7Click to refresh the current page")
//            .build();

    private static final ItemStack REFRESH_BUTTON = Item.builder()
            .name("&c&lRefresh")
            .lore(Collections.singletonList("&7Click to refresh the current page"))
            .material("PAPER")
            .build().toItemStack();
    public RefreshButton(ItemStack item, PagedMenu menu) {
        super(item, (player, event) -> {
            event.setCancelled(true);

            player.closeInventory();
            menu.getCurrent().refresh(player);
        });
    }

    public RefreshButton(PagedMenu menu) {
        this(REFRESH_BUTTON, menu);
    }

}
