package org.de.eloy.fnaf.gui.system.setup.arenas.guis;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.de.eloy.fnaf.database.dao.ArenaDAO;
import org.de.eloy.fnaf.game.objects.Arena;
import org.de.eloy.fnaf.gui.system.GUIManager;

import java.util.ArrayList;
import java.util.List;

public class ArenasListGUI {
    public static Inventory getGUI() {
        Inventory inventory = GUIManager.getGuiInitialized("§6§lArenas List", 6);

        inventory.setItem(50, GUIManager.createItem(Material.EMERALD_BLOCK, "§a§lCreate Arena", null));
        inventory.setItem(48, GUIManager.createItem(Material.BARRIER, "§c§lRemove §nall§c§l the arenas", null));
        inventory.setItem(8, GUIManager.createItem(Material.RED_STAINED_GLASS_PANE, "§c§lX", null));

        ArenaDAO arenaDAO = new ArenaDAO();
        ArrayList<Arena> arenasList = arenaDAO.getAll();

        if (arenasList.isEmpty()) {
            inventory.setItem(31, GUIManager.createItem(Material.BEDROCK, "§cNo arenas", null));
            return inventory;
        }

        addArenas(inventory, arenasList);

        return inventory;
    }

    private static void addArenas(Inventory inventory, List<Arena> arenasList) {
        int slot = 0;
        for (Arena arena : arenasList) {
            while (slot < inventory.getSize()) {
                ItemStack item = inventory.getItem(slot);

                if (item == null || item.getType() == Material.AIR) {
                    ItemStack arenaItem = GUIManager.createItem(Material.COMMAND_BLOCK, "§6§l" + arena.getName(), createArenaLore(arena));
                    ItemMeta arenaItemMeta = arenaItem.getItemMeta();
                    assert arenaItemMeta != null;
                    arenaItemMeta.setCustomModelData(arena.getId());
                    arenaItem.setItemMeta(arenaItemMeta);
                    inventory.setItem(slot, arenaItem);
                    break;
                }

                slot++;
            }
        }
    }

    private static List<String> createArenaLore(Arena arena) {
        List<String> arenaLore = new ArrayList<>();

        arenaLore.add("§e§l-----------§8§l[ §4§l* §8§l]§e§l-----------");
        arenaLore.add("");

        if (arena.getPostGameSpawn() == null) arenaLore.add("§7➤ §8Post-Game Spawn: §cUnconfigured");
        else arenaLore.add("§d➤ §fPost-Game Spawn: §a"+arena.getPostGameSpawn().getBlockX()+" "+arena.getPostGameSpawn().getBlockY()+" "+arena.getPostGameSpawn().getBlockZ());

        arenaLore.add("");

        if (arena.getWaitingLobbySpawn() == null) arenaLore.add("§7➤ §8Waiting Lobby Spawn: §cUnconfigured");
        else arenaLore.add("§d➤ §fWaiting Lobby Spawn: §a"+arena.getWaitingLobbySpawn().getBlockX()+" "+arena.getWaitingLobbySpawn().getBlockY()+" "+arena.getWaitingLobbySpawn().getBlockZ());

        arenaLore.add("");

        // Check if the lights list is empty
        if (arena.getLights() == null || arena.getLights().isEmpty()) arenaLore.add("§7➤ §8Lights: §c0");
        else arenaLore.add("§d➤ §fLights: §a" + arena.getLights().size());

        arenaLore.add("");

        if (arena.getGuard() == null || arena.getGuard().getHp() == -1 && arena.getGuard().getSpawn() == null) arenaLore.add("§7➤ §8Guard: §cNot ready");
        else arenaLore.add("§d➤ §fGuard: §aReady");

        arenaLore.add("");

        if (arena.getDoors() == null) arenaLore.add("§7➤ §8Doors: §c0");
        else arenaLore.add("§d➤ §fDoors: §a" + arena.getDoors().size());

        arenaLore.add("");

        if (arena.getCameras() == null) arenaLore.add("§7➤ §8Cameras: §c0");
        else arenaLore.add("§d➤ §fCameras: §a" + arena.getCameras().size());

        arenaLore.add("");

        if (arena.getAnimatronics() == null) arenaLore.add("§7➤ §8Animatronics: §c0");
        else arenaLore.add("§d➤ §fAnimatronics: §a" + arena.getAnimatronics().size());

        arenaLore.add("");
        arenaLore.add("§e§l-----------§8§l[ §4§l* §8§l]§e§l-----------");

        return arenaLore;
    }

}
