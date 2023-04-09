package dev.wave.wavecore.gui.menu;

import dev.wave.wavecore.gui.menu.types.PagedMenu;
import lombok.Getter;


public abstract class Template {

    @Getter private final Button[] buttons = new Button[54];

    public void setButton(int index, Button button) {
        buttons[index] = button;
    }

    public abstract void set(PagedMenu menu);

}
