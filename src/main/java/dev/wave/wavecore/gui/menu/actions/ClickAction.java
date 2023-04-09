package dev.wave.wavecore.gui.menu.actions;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface ClickAction {

    void click(Player clicker, InventoryClickEvent event);

}
