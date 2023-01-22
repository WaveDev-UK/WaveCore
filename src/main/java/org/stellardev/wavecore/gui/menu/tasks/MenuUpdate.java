package org.stellardev.wavecore.gui.menu.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.stellardev.wavecore.gui.menu.Menu;
import org.stellardev.wavecore.gui.menu.types.UpdatingMenu;

public class MenuUpdate implements Runnable {

    private long last = System.currentTimeMillis();

    @Override
    public void run() {
        Menu.getMenus().forEach((uuid, menu) -> {
            if (!(menu instanceof UpdatingMenu))
                return;

            Player player = Bukkit.getPlayer(uuid);
            if (player == null || !player.isOnline())
                return;

            UpdatingMenu updatingMenu = (UpdatingMenu) menu;
            updatingMenu.onUpdate(player, last);
            updatingMenu.update(player);

            last = System.currentTimeMillis();
        });
    }
}
