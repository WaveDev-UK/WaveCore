package org.stellardev.wavecore.gui.menu.types;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.stellardev.wavecore.gui.menu.Button;
import org.stellardev.wavecore.gui.menu.Menu;
import org.stellardev.wavecore.gui.menu.Partition;
import org.stellardev.wavecore.gui.menu.Template;
import org.stellardev.wavecore.gui.menu.buttons.NextButton;
import org.stellardev.wavecore.gui.menu.buttons.PreviousButton;

@Getter
public class PagedMenu extends Menu {

    private PagedMenu current = this, previous, next;
    private Button nextButton, previousButton;
    @Setter private int currentPageNumber = 1, maxPages = 1;
    private Template template;

    public PagedMenu(String name, int rows, Template template) {
        super(name, rows);
        if (template != null)
            applyTemplate(template);
    }

    public PagedMenu(String name, int rows) {
        this(name, rows, null);
    }

    public void appendButton(Button button, int takeAmount) {
        appendButton(button, new Partition(0, (super.getSize() - takeAmount) - 1));
    }

    public void appendButton(Button button) {
        appendButton(button, 9);
    }

    public void appendButton(Button button, Partition partition) {
        for (int i = 0; i < super.getSize(); i++) {
            if (!partition.contains(i))
                continue;

            Button currentButton = current.getButton(i);
            if (currentButton != null && !isNullOrAir(currentButton.getItemStack()))
                continue;

            current.setButton(i, button);
            return;
        }

        appendPage();
        current.appendButton(button, partition);
    }

    public void addNextButton(int slot) {
        current.setButton(slot, nextButton = new NextButton(this));
    }

    public void addNextButton() {
        addNextButton(getSize() - 4);
    }

    public void addNextButton(int slot, ItemStack itemStack) {
        current.setButton(slot, nextButton = new NextButton(itemStack, this));
    }

    public void addNextButton(ItemStack itemStack) {
        addNextButton(getSize() - 4, itemStack);
    }

    public void addPreviousButton(int slot) {
        current.setButton(slot, previousButton = new PreviousButton(this));
    }

    public void addPreviousButton() {
        addPreviousButton(getSize() - 6);
    }

    public void addPreviousButton(int slot, ItemStack itemStack) {
        current.setButton(slot, previousButton = new PreviousButton(itemStack, this));
    }

    public void addPreviousButton(ItemStack itemStack) {
        addPreviousButton(getSize() - 6, itemStack);
    }

    public PagedMenu appendPage() {
        PagedMenu newPage = new PagedMenu(super.getName(), super.getRows());

        newPage.previous = current;
        current.next = newPage;
        current = newPage;

        if (template != null)
            newPage.applyTemplate(template);

        setMaxPages(getMaxPages() + 1);

        return newPage;
    }

    public void applyTemplate(Template template) {
        this.template = template;
        Button[] buttons = template.getButtons();

        for (int i = 0; i < super.getSize(); i++) {
            Button button = buttons[i];
            if (button == null)
                continue;
            if (!isNullOrAir(super.getButton(i).getItemStack()))
                continue;

            super.setButton(i, button);
        }

        template.set(this);
    }

    public boolean hasNext() {
        return next != null;
    }

    public boolean hasPrevious() {
        return previous != null;
    }

    public void refresh(Player player) {
        super.refresh(player);

        if (template != null)
            applyTemplate(template);
    }

    private boolean isNullOrAir(ItemStack itemStack) {
        return itemStack == null || itemStack.getType() == Material.AIR;
    }
}
