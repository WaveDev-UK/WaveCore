package dev.wave.wavecore.gui.menu.actions;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

public interface CloseAction {

    void close(Player player, InventoryCloseEvent event);

}
