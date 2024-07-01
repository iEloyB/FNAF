package org.de.eloy.fnaf.gui.system.setup.arenas.guis;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.de.eloy.fnaf.game.objects.Arena;
import org.de.eloy.fnaf.game.objects.Guard;
import org.de.eloy.fnaf.gui.system.GUIManager;

import java.util.ArrayList;
import java.util.List;

public class GuardMenuGUI {
    public static Inventory getGUI(Arena arena) {
        Inventory inventory = GUIManager.getGuiInitialized("§6§l"+arena.getName()+"'s guard", 3);
        inventory.setItem(12, GUIManager.createItem(Material.HEART_POTTERY_SHERD, "§e§lGuard hp", getGuardHpLore(arena.getGuard())));
        inventory.setItem(14, GUIManager.createItem(Material.ENDER_PEARL, "§e§lGuard spawn", getGuardSpawnLore(arena.getGuard())));
        inventory.setItem(21, GUIManager.createItem(Material.BARRIER, "§c§lRemove guard", null));
        inventory.setItem(23, GUIManager.createItem(Material.ARROW, "§b§lBack", null));
        inventory.setItem(8, GUIManager.createItem(Material.RED_STAINED_GLASS_PANE, "§c§lX", null));
        return inventory;
    }

    private static List<String> getGuardHpLore(Guard guard) {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§e§lCurrent guard hp:");
        if (guard == null || guard.getHp() == -1) lore.add("§b§l➤ §cUnconfigured hearts");
        else lore.add("§b§l➤ §3" + guard.getHp() + "§c❤ §3hearts");
        lore.add("");
        lore.add("§e§lRIGHT-CLICK: §6You add 1 heart");
        lore.add("§e§lLEFT-CLICK: §6You remove 1 heart");
        return lore;
    }

    private static List<String> getGuardSpawnLore(Guard guard) {
        List<String> lore = new ArrayList<>();
        lore.add("§e§lCurrent guard location:");
        if (guard == null || guard.getSpawn() == null) lore.add("§b§l➤ §cUnconfigured spawn");
        else {
            Location GUARD_SPAWN = guard.getSpawn();
            lore.add("§b§l➤ §3" + GUARD_SPAWN.getBlockX()+" "+GUARD_SPAWN.getBlockY()+" "+GUARD_SPAWN.getBlockZ());
        }
        lore.add("");
        lore.add("§e§lCLICK: §6You set the guard's spawn on your §ncurrent§6 location");
        return lore;
    }

}