package org.de.eloy.fnaf.gui.system.setup.arenas.guis;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.de.eloy.fnaf.game.objects.Arena;
import org.de.eloy.fnaf.gui.system.GUIManager;

import java.util.ArrayList;
import java.util.List;

public class EditArenaGUI {
    public static void setArena(Arena arena) {
        EditArenaGUI.arena = arena;
    }

    private static Arena arena;
    public static Inventory getGUI(Arena arena) {
        setArena(arena);

        Inventory inventory = GUIManager.getGuiInitialized("§6§l"+arena.getName(), 3);

        inventory.setItem(10, GUIManager.createItem(Material.ENDER_EYE, "§e§lPost game Location", getPostGameLocationLore()));
        inventory.setItem(11, GUIManager.createItem(Material.TORCH, "§e§lLights", getLightsLore()));
        inventory.setItem(12, GUIManager.createItem(Material.PLAYER_HEAD, "§e§lGuard", getGuardLore()));
        inventory.setItem(13, GUIManager.createItem(Material.IRON_BLOCK, "§e§lDoors", getDoorsLore()));
        inventory.setItem(14, GUIManager.createItem(Material.ENDER_PEARL, "§e§lWaiting lobby", getWaitingLobbyLore()));
        inventory.setItem(15, GUIManager.createItem(Material.OBSERVER, "§e§lCameras", getCamerasLore()));
        inventory.setItem(16, GUIManager.createItem(Material.SKELETON_SKULL, "§e§lAnimatronics", getAnimatronicsLore()));
        inventory.setItem(21, GUIManager.createItem(Material.BARRIER, "§c§lRemove arena", getRemoveArenaLore()));
        inventory.setItem(23, GUIManager.createItem(Material.ARROW, "§b§lBack", null));
        inventory.setItem(8, GUIManager.createItem(Material.RED_STAINED_GLASS_PANE, "§c§lX", null));

        return inventory;
    }

    private static List<String> getPostGameLocationLore() {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§f§oThe post game location is the place where");
        lore.add("§f§othe players will teleport when the game ends");
        lore.add("");
        lore.add("§e§lCurrent location:");
        if (arena.getPostGameSpawn() == null) lore.add("§b§l➤ §cUnconfigured");
        else {
            Location PGSPAWN = arena.getPostGameSpawn();
            lore.add("§b§l➤ §3" + PGSPAWN.getBlockX()+" "+PGSPAWN.getBlockY()+" "+PGSPAWN.getBlockZ());
        }
        lore.add("");
        lore.add("§e§lCLICK: §6The post game location");
        lore.add("§6will be added where you are");
        return lore;
    }

    private static List<String> getLightsLore() {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§f§oThe lights are all the lighting blocks of the");
        lore.add("§f§opizzeria, they will turn off when the battery runs out");
        lore.add("");
        lore.add("§e§lCurrent lights:");
        if (arena.getLights() == null) lore.add("§b§l➤ §cUnconfigured");
        else lore.add("§b§l➤ §3"+arena.getLights().size());
        lore.add("");
        lore.add("§e§lCLICK: §6The lights menu opens");
        return lore;
    }

    private static List<String> getGuardLore() {
        List<String> lore = new ArrayList<>();

        lore.add("");
        lore.add("§f§oConfigure the pizzeria guard (his spawn and hp)");
        lore.add("");
        lore.add("§e§lCurrent guard status:");
        if (arena.getGuard() == null || arena.getGuard().getSpawn() == null) lore.add("§b§l➤ §cUnconfigured spawn");
        else {
            Location GUARD_SPAWN = arena.getGuard().getSpawn();
            lore.add("§b§l➤ §3" + GUARD_SPAWN.getBlockX()+" "+GUARD_SPAWN.getBlockY()+" "+GUARD_SPAWN.getBlockZ());
        }
        if (arena.getGuard() == null || arena.getGuard().getHp() == -1) lore.add("§b§l➤ §cUnconfigured hearts");
        else lore.add("§b§l➤ §3" + arena.getGuard().getHp() + "§c❤ §3hearts");
        lore.add("");
        lore.add("§e§lCLICK: §6The guard menu opens");
        return lore;
    }

    private static List<String> getDoorsLore() {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§f§oThe doors are in the pizzeria and have");
        lore.add("§f§oa lever that opens or closes them.");
        lore.add("");
        lore.add("§e§lCurrent doors:");
        if (arena.getDoors() == null) lore.add("§b§l➤ §cUnconfigured");
        else lore.add("§b§l➤ §3"+arena.getDoors().size());
        lore.add("");
        lore.add("§e§lCLICK: §6The doors menu opens");
        return lore;
    }

    private static List<String> getWaitingLobbyLore() {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§f§oThe waiting lobby location is the place where");
        lore.add("§f§othe players will teleport before the game starts");
        lore.add("");
        lore.add("§e§lCurrent location:");
        if (arena.getWaitingLobbySpawn() == null) lore.add("§b§l➤ §cUnconfigured");
        else {
            Location WLSPAWN = arena.getWaitingLobbySpawn();
            lore.add("§b§l➤ §3" + WLSPAWN.getBlockX()+" "+WLSPAWN.getBlockY()+" "+WLSPAWN.getBlockZ());
        }
        lore.add("");
        lore.add("§e§lCLICK: §6The waiting lobby spawn location");
        lore.add("§6will be added where you are");
        return lore;
    }

    private static List<String> getCamerasLore() {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§f§oThe cameras are the guard's");
        lore.add("§f§ovision system in the pizzeria");
        lore.add("");
        lore.add("§e§lCurrent cameras:");
        if (arena.getCameras() == null) lore.add("§b§l➤ §cUnconfigured");
        else lore.add("§b§l➤ §3"+arena.getCameras().size());
        lore.add("");
        lore.add("§e§lCLICK: §6The cameras menu opens");
        return lore;
    }

    private static List<String> getAnimatronicsLore() {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§f§oThe animatronics must kill the guard");
        lore.add("§f§obefore 6AM, to do so they must use their abilities");
        lore.add("");
        lore.add("§e§lCurrent animatronics:");
        if (arena.getAnimatronics() == null) lore.add("§b§l➤ §cUnconfigured");
        else lore.add("§b§l➤ §3"+arena.getAnimatronics().size());
        lore.add("");
        lore.add("§e§lCLICK: §6The animatronics menu opens");
        return lore;
    }

    private static List<String> getRemoveArenaLore() {
        List<String> lore = new ArrayList<>();
        lore.add("§7You remove the arena: §d" + arena.getName());
        return lore;
    }
}
