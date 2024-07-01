package org.de.eloy.fnaf.gui.system.setup.arenas.guis;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.de.eloy.fnaf.game.objects.Arena;
import org.de.eloy.fnaf.gui.system.GUIManager;

import java.util.ArrayList;
import java.util.List;

public class LightsMenuGUI {
    public static Inventory getGUI(Arena arena) {
        Inventory inventory = GUIManager.getGuiInitialized("§6§l"+arena.getName()+"'s lights", 3);
        inventory.setItem(13, GUIManager.createItem(Material.REDSTONE_TORCH, "§e§lAdd lights", getAddLightsLore()));
        inventory.setItem(21, GUIManager.createItem(Material.BARRIER, "§c§lRemove all lights", null));
        inventory.setItem(23, GUIManager.createItem(Material.ARROW, "§b§lBack", null));
        inventory.setItem(8, GUIManager.createItem(Material.RED_STAINED_GLASS_PANE, "§c§lX", null));
        return inventory;
    }
    private static List<String> getAddLightsLore() {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§f§oDon't use other lights in the");
        lore.add("§f§opizzeria! Use these instead.");
        lore.add("");
        lore.add("§e§lCLICK: §6You get the light block (place it where the lights goes)");
        return lore;
    }

}