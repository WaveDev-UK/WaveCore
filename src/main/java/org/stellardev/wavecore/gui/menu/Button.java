package org.stellardev.wavecore.gui.menu;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.stellardev.wavecore.gui.menu.actions.ClickAction;

@Getter
@Setter
public class Button {

    private ItemStack itemStack;
    private ClickAction clickAction;

    public Button(ItemStack itemStack, ClickAction clickAction) {
        this.itemStack = itemStack;
        this.clickAction = clickAction;
    }

    public Button(ItemStack itemStack) {
        this(itemStack, null);
    }

}
