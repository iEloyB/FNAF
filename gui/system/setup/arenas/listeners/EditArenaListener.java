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
import org.de.eloy.fnaf.gui.system.setup.arenas.guis.*;

import java.util.ArrayList;

public class EditArenaListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String title = player.getOpenInventory().getTitle();
        ItemStack item = event.getCurrentItem();

        Arena arena = null;

        ArenaDAO arenaDAO = new ArenaDAO();
        ArrayList<Arena> arenas = arenaDAO.getAll();

        for (Arena a : arenas) {
            String arenaName = "§6§l"+a.getName();
            if (arenaName.equalsIgnoreCase(title)) arena = a;
        }

        if (arena == null || item == null) return;

        event.setCancelled(true);

        Material itemType = item.getType();

        switch (itemType) {
            case ENDER_EYE:
                setPostGameLocation(player, arena);
                player.openInventory(EditArenaGUI.getGUI(arena));
                break;
            case TORCH:
                player.openInventory(LightsMenuGUI.getGUI(arena));
                break;
            case IRON_BLOCK:
                player.openInventory(DoorsMenuGUI.getGUI(arena));
                break;
            case ENDER_PEARL:
                setWaitingLobbyLocation(player, arena);
                player.openInventory(EditArenaGUI.getGUI(arena));
                break;
            case OBSERVER:
                player.openInventory(CamerasMenuGUI.getGUI(arena));
                break;
            case PLAYER_HEAD:
                player.openInventory(GuardMenuGUI.getGUI(arena));
                break;
            case SKELETON_SKULL:
                player.openInventory(AnimatronicsMenuGUI.getGUI(arena));
                break;
            case BARRIER:
                arenaDAO.delete(arena.getId());
                deleteBlocks(arena.getId());
                player.sendMessage(FNAF.getMessages().getPREFIX() + "§aYou removed the arena §l"+arena.getName());
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.5f);
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


    private void setPostGameLocation(Player player, Arena arena) {
        ArenaDAO arenaDAO = new ArenaDAO();
        arena.setPostGameSpawn(player.getLocation());
        arenaDAO.update(arena.getId(), arena);

        player.sendMessage(FNAF.getMessages().getPREFIX()+" " +
                "§aYou have configured the arena post game lobby §6"+arena.getName()+"§e " +
                "(§f"+player.getLocation().getBlockX()+"," +
                "§f"+player.getLocation().getBlockY()+"," +
                "§f"+player.getLocation().getBlockZ()+"§e).");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.5f);
    }

    private void setWaitingLobbyLocation(Player player, Arena arena) {
        ArenaDAO arenaDAO = new ArenaDAO();
        arena.setWaitingLobbySpawn(player.getLocation());
        arenaDAO.update(arena.getId(), arena);

        player.sendMessage(FNAF.getMessages().getPREFIX()+" " +
                "§aYou have configured the arena waiting lobby §6"+arena.getName()+"§e " +
                "(§f"+player.getLocation().getBlockX()+"," +
                "§f"+player.getLocation().getBlockY()+"," +
                "§f"+player.getLocation().getBlockZ()+"§e).");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.5f);
    }
}
