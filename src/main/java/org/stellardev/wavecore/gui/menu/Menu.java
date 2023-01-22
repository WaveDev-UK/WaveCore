package org.stellardev.wavecore.gui.menu;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.stellardev.wavecore.gui.menu.actions.ClickAction;
import org.stellardev.wavecore.gui.menu.actions.CloseAction;
import org.stellardev.wavecore.util.Text;

import java.util.Map;
import java.util.UUID;

@Getter
public class Menu {

    @Getter private static final Map<UUID, Menu> menus = Maps.newConcurrentMap();

    private Inventory inventory;
    private String name;
    private int rows;
    private InventoryType inventoryType;
    @Setter private ClickAction bottomClickAction;
    @Setter private CloseAction closeAction;
    private Map<Integer, Button> buttonListeners;
    @Setter private boolean bottomCancelled = true, clicked = false;

    public Menu(String name, int rows) {
        this.inventory = Bukkit.createInventory(null, Math.max(0, Math.min(rows * 9, 6 * 9)), Text.c(name));
        this.name = name;
        this.rows = rows;
        this.buttonListeners = Maps.newHashMap();
    }

    public Menu(String name, InventoryType inventoryType) {
        this.inventory = Bukkit.createInventory(null, inventoryType, Text.c(name));
        this.name = name;
        this.inventoryType = inventoryType;
        this.buttonListeners = Maps.newHashMap();
    }

    public boolean hasClicked() {
        return clicked;
    }

    public Button getButton(int index) {
        return buttonListeners.get(index);
    }

    public void setButton(int index, Button button) {
        inventory.setItem(index, button.getItemStack());
        buttonListeners.put(index, button);
    }

    public void removeButton(int index) {
        inventory.setItem(index, new ItemStack(Material.AIR));
        buttonListeners.remove(index);
    }

    public void addBackButton(ItemStack item, ClickAction action) {
        setButton(getSize() - 1, new Button(item, action));
    }

    /*
    public void addBackButton(ClickAction action) {
        addBackButton(ItemUtils.buildSkullBackButton(), action);
    }

    public void addBackButton(int slot, ClickAction action) {
        setButton(slot, new Button(ItemUtils.buildSkullBackButton(), action));
    }
    */

    public void addBackButton(int slot, ItemStack item, ClickAction action) {
        setButton(slot, new Button(item, action));
    }

    public void fillMenu(Button button) {
        for (int i = 0; i < getSize(); i++)
            setButton(i, button);
    }

    public void fillMenu(ItemStack itemStack, ClickAction clickAction) {
        fillMenu(new Button(itemStack, clickAction));
    }

    public void fillMenu(ItemStack itemStack) {
        fillMenu(itemStack, null);
    }

    public void open(Player player, boolean make) {
        if (make)
            make(player);

        onFirstOpen(player);
        player.openInventory(inventory);
        menus.put(player.getUniqueId(), this);
    }

    public void open(Player player) {
        open(player, false);
    }

    public void refresh(Player player) {
        buttonListeners.clear();

        for (int i = 0; i < getSize(); i++)
            inventory.setItem(i, null);

        make(player);

        for (Map.Entry<Integer, Button> entry : buttonListeners.entrySet())
            this.inventory.setItem(entry.getKey(), entry.getValue().getItemStack());

    }

    public void update(Player player) {
        Inventory inventory = player.getOpenInventory().getTopInventory();

        buttonListeners.forEach((slot, button) ->
                inventory.setItem(slot, button.getItemStack()));
    }

    public void onFirstOpen(Player player) {}
    public void make(Player player) {}

    public int getSize() {
        return rows * 9;
    }
}
