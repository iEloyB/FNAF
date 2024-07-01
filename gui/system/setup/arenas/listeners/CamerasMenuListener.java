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
import org.de.eloy.fnaf.database.dao.CameraDAO;
import org.de.eloy.fnaf.game.objects.Arena;
import org.de.eloy.fnaf.game.objects.Camera;
import org.de.eloy.fnaf.gui.system.setup.arenas.guis.ArenasListGUI;

import java.util.ArrayList;

public class CamerasMenuListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String title = player.getOpenInventory().getTitle();
        ItemStack item = event.getCurrentItem();

        Arena arena = null;

        ArenaDAO arenaDAO = new ArenaDAO();
        ArrayList<Arena> arenas = arenaDAO.getAll();

        for (Arena a : arenas) {
            String arenaName = "§6§l" + a.getName() + "'s cameras";
            if (arenaName.equalsIgnoreCase(title)) arena = a;
        }

        if (arena == null || item == null) return;
        event.setCancelled(true);

        Material itemType = item.getType();

        switch (itemType) {
            case REDSTONE_TORCH:
                addCamera(player, arena);
                player.closeInventory();
                break;
            case BARRIER:
                removeAllCameras(player, arena);
                player.openInventory(ArenasListGUI.getGUI());
                break;
            case ARROW:
                player.openInventory(ArenasListGUI.getGUI());
                break;
            case RED_STAINED_GLASS_PANE:
                player.closeInventory();
                break;
        }
    }

    public void addCamera(Player player, Arena arena) {
        CameraDAO cameraDAO = new CameraDAO();
        cameraDAO.insert(new Camera(player.getLocation(), arena.getId()));

        player.sendMessage(FNAF.getMessages().getPREFIX()+" " +
                "§aYou added a new camera in §6"+arena.getName()+"§e " +
                "(§f"+player.getLocation().getBlockX()+"," +
                "§f"+player.getLocation().getBlockY()+"," +
                "§f"+player.getLocation().getBlockZ()+"§e).");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.5f);
    }
    public void removeAllCameras(Player player, Arena arena) {
        CameraDAO cameraDAO = new CameraDAO();
        ArrayList<Camera> cameras = cameraDAO.getAllByArena(arena.getId());

        if (cameras == null) {
            player.sendMessage(FNAF.getMessages().getPREFIX() + " §cThere are no cameras to remove");
            player.playSound(player.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1, 0.5f);
            return;
        }

        for (Camera camera : cameras) {
            cameraDAO.delete(camera.getId());
        }

        player.sendMessage(FNAF.getMessages().getPREFIX() + " §aYou removed §lall§a the cameras from §l" + arena.getName());
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.5f);
    }
}