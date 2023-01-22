package org.stellardev.wavecore.gui.menu;

import lombok.Getter;
import org.stellardev.wavecore.gui.menu.types.PagedMenu;


public abstract class Template {

    @Getter private final Button[] buttons = new Button[54];

    public void setButton(int index, Button button) {
        buttons[index] = button;
    }

    public abstract void set(PagedMenu menu);

}
