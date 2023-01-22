package org.stellardev.wavecore.gui.menu.listeners;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.stellardev.wavecore.gui.menu.Button;
import org.stellardev.wavecore.gui.menu.Menu;

public class MenuListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        Menu menu = Menu.getMenus().get(player.getUniqueId());

        if (menu == null)
            return;

        if (player.getOpenInventory().getTopInventory().equals(inventory)) {
            Button button = menu.getButton(event.getRawSlot());
            if (button == null || button.getClickAction() == null) {
                event.setCancelled(true);
                return;
            }

            button.getClickAction().click(player, event);
        } else if (player.getOpenInventory().getBottomInventory().equals(inventory)) {
            if (menu.getBottomClickAction() != null)
                menu.getBottomClickAction().click(player, event);
            else if (menu.isBottomCancelled())
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Menu menu = Menu.getMenus().remove(player.getUniqueId());
        if (menu == null)
            return;

        if (menu.getCloseAction() != null)
            menu.getCloseAction().close(player, event);
    }

}
