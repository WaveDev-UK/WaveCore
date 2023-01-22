package org.stellardev.wavecore.armor.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class ArmorUnEquipEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    @Getter private final Player player;
    @Getter private final ItemStack item;
    @Getter @Setter private boolean cancelled = false;

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
