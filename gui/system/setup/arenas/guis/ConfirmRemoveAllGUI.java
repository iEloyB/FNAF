package org.de.eloy.fnaf.gui.system.setup.arenas.guis;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.de.eloy.fnaf.gui.system.GUIManager;

public class ConfirmRemoveAllGUI {
    public static Inventory getGUI() {
        Inventory inventory = GUIManager.getGuiInitialized("§4§lClear §nALL§4§l arenas", 3);
        inventory.setItem(14, GUIManager.createItem(Material.EMERALD_BLOCK, "§2§lConfirm", null));
        inventory.setItem(12, GUIManager.createItem(Material.REDSTONE_BLOCK, "§c§lCancel", null));

        return inventory;
    }
}
