package org.de.eloy.fnaf.gui.system.setup.arenas.guis;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.de.eloy.fnaf.game.objects.Arena;
import org.de.eloy.fnaf.gui.system.GUIManager;

import java.util.ArrayList;
import java.util.List;

public class CamerasMenuGUI {
    public static Inventory getGUI(Arena arena) {
        Inventory inventory = GUIManager.getGuiInitialized("§6§l"+arena.getName()+"'s cameras", 3);
        inventory.setItem(13, GUIManager.createItem(Material.REDSTONE_TORCH, "§e§lAdd camera", getAddCameraLore()));
        inventory.setItem(21, GUIManager.createItem(Material.BARRIER, "§c§lRemove all cameras", null));
        inventory.setItem(23, GUIManager.createItem(Material.ARROW, "§b§lBack", null));
        inventory.setItem(8, GUIManager.createItem(Material.RED_STAINED_GLASS_PANE, "§c§lX", null));
        return inventory;
    }

    private static List<String> getAddCameraLore() {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§f§oAdd cameras to make guard's life");
        lore.add("§f§omuch easier. Remember to add at least");
        lore.add("§f§o1 camera to make the arena work");
        lore.add("");
        lore.add("§e§lCLICK: §6You add a new camera on your §ncurrent§6 location");
        return lore;
    }

}