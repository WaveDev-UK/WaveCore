package dev.wave.wavecore.gui.menu.buttons;


import dev.wave.wavecore.item.Item;
import org.bukkit.inventory.ItemStack;
import dev.wave.wavecore.gui.menu.Button;
import dev.wave.wavecore.gui.menu.types.PagedMenu;
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
