package org.de.eloy.fnaf.gui.system.setup.arenas.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.database.dao.ArenaDAO;
import org.de.eloy.fnaf.database.dao.DoorDAO;
import org.de.eloy.fnaf.database.dao.LightDAO;
import org.de.eloy.fnaf.game.objects.Arena;
import org.de.eloy.fnaf.game.objects.Door;
import org.de.eloy.fnaf.game.objects.Light;
import org.de.eloy.fnaf.gui.system.setup.arenas.guis.ArenasListGUI;
import org.de.eloy.fnaf.messages.Message;

import java.util.ArrayList;

public class ConfirmRemoveAllListener implements Listener {

    private static final Message MESSAGES = FNAF.getMessages();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String inventoryTitle = event.getView().getOriginalTitle();
        ItemStack item = event.getCurrentItem();

        if (!inventoryTitle.equalsIgnoreCase("§4§lClear §nALL§4§l arenas") || item == null) return;
        event.setCancelled(true);

        if (item.getType().equals(Material.EMERALD_BLOCK)) {
            ArenaDAO arenaDAO = new ArenaDAO();
            ArrayList<Arena> arenas = arenaDAO.getAll();

            for (Arena arena : arenas) {
                arenaDAO.delete(arena.getId());
                deleteBlocks(arena.getId());
            }

            player.sendMessage(MESSAGES.getPREFIX() + "§aAll arenas were removed");
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.5f);
            player.openInventory(ArenasListGUI.getGUI());
        }

        if (item.getType().equals(Material.REDSTONE_BLOCK)) {
            player.openInventory(ArenasListGUI.getGUI());
        }
    }

    public void deleteBlocks(int id) {
        ArrayList<Light> lights = new LightDAO().getAllByArena(id);
        if (lights != null) {
            for (Light light : lights) {
                light.getLocation().getBlock().setType(Material.AIR);
            }
        }

        ArrayList<Door> doors = new DoorDAO().getAllByArena(id);
        if (doors != null) {
            for (Door door : doors) {
                door.getLocation().getBlock().setType(Material.AIR);
                door.getLeverLocation().getBlock().setType(Material.AIR);
                door.getLocation().add(0, 1, 0).getBlock().setType(Material.AIR);
            }
        }
    }
}
