package org.de.eloy.fnaf.gui.system.setup.arenas.guis;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.de.eloy.fnaf.game.objects.Arena;
import org.de.eloy.fnaf.gui.system.GUIManager;

import java.util.ArrayList;
import java.util.List;

public class DoorsMenuGUI {
    public static Inventory getGUI(Arena arena) {
        Inventory inventory = GUIManager.getGuiInitialized("§6§l"+arena.getName()+"'s doors", 3);
        inventory.setItem(13, GUIManager.createItem(Material.IRON_DOOR, "§e§lAdd door", getAddDoorLore()));
        inventory.setItem(21, GUIManager.createItem(Material.BARRIER, "§c§lRemove all doors", null));
        inventory.setItem(23, GUIManager.createItem(Material.ARROW, "§b§lBack", null));
        inventory.setItem(8, GUIManager.createItem(Material.RED_STAINED_GLASS_PANE, "§c§lX", null));
        return inventory;
    }
    private static List<String> getAddDoorLore() {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§f§oYou get a door, when you place");
        lore.add("§f§oit, you will get the lever.");
        lore.add("§f§othat opens that door. ");
        lore.add("");
        lore.add("§e§lCLICK: §6You get a door item.");
        return lore;
    }

}