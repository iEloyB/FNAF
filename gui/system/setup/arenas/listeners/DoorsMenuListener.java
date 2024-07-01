package org.de.eloy.fnaf.gui.system.setup.arenas.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.database.dao.ArenaDAO;
import org.de.eloy.fnaf.database.dao.DoorDAO;
import org.de.eloy.fnaf.game.objects.Arena;
import org.de.eloy.fnaf.game.objects.Door;
import org.de.eloy.fnaf.gui.system.setup.arenas.guis.ArenasListGUI;

import java.util.ArrayList;

public class DoorsMenuListener implements Listener {

    private Location doorLocation;
    private Location leverLocation;

    public DoorsMenuListener() {
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String title = player.getOpenInventory().getTitle();
        ItemStack item = event.getCurrentItem();

        Arena arena = null;

        ArenaDAO arenaDAO = new ArenaDAO();
        ArrayList<Arena> arenas = arenaDAO.getAll();

        for (Arena a : arenas) {
            String arenaName = "§6§l" + a.getName() + "'s doors";
            if (arenaName.equalsIgnoreCase(title)) arena = a;
        }

        if (arena == null || item == null) return;
        event.setCancelled(true);

        doorLocation = null;
        leverLocation = null;

        Material itemType = item.getType();

        switch (itemType) {
            case IRON_DOOR:
                player.getInventory().remove(Material.IRON_DOOR);
                player.getInventory().remove(Material.LEVER);
                giveDoor(player, arena);
                break;
            case BARRIER:
                removeAllDoors(player, arena);
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

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        Player player = event.getPlayer();
        Block block = event.getBlock();
        ItemMeta itemMeta = item.getItemMeta();

        if (!block.getType().equals(Material.IRON_DOOR) && !block.getType().equals(Material.LEVER)) return;
        if (itemMeta == null) return;
        if (!itemMeta.hasCustomModelData()) return;

        int arenaId = itemMeta.getCustomModelData();
        Arena arena = new ArenaDAO().getById(arenaId);

        if (arena == null) return;
        if (block.getType().equals(Material.IRON_DOOR)) {
            doorLocation = block.getLocation();

            player.sendMessage(FNAF.getMessages().getPREFIX() + " §aYou added a door, now, please add the lever");
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.5f);
            player.getInventory().remove(Material.IRON_DOOR);
            giveLever(player, arena);
        } else {
            leverLocation = block.getLocation();

            DoorDAO doorDAO = new DoorDAO();
            doorDAO.insert(new Door(doorLocation, leverLocation,Material.IRON_BLOCK,arenaId));
            player.sendMessage(FNAF.getMessages().getPREFIX() + " §aYou added a lever, the "+arena.getName()+" door is saved");
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.5f);
            player.getInventory().remove(Material.LEVER);
        }
    }

    private void giveDoor(Player player, Arena arena) {
        ItemStack door = new ItemStack(Material.IRON_DOOR);
        ItemMeta doorMeta = door.getItemMeta();

        assert doorMeta != null;
        doorMeta.setDisplayName("§6Door §e(" + arena.getName() + ")");
        doorMeta.setCustomModelData(arena.getId());
        door.setItemMeta(doorMeta);

        player.sendMessage(FNAF.getMessages().getPREFIX() + " §aYou got the door for the arena §l" + arena.getName());
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.5f);

        player.getInventory().addItem(door);
    }

    private void giveLever(Player player, Arena arena) {
        ItemStack lever = new ItemStack(Material.LEVER);
        ItemMeta leverMeta = lever.getItemMeta();

        assert leverMeta != null;
        leverMeta.setDisplayName("§6Lever §e(" + arena.getName() + ")");
        leverMeta.setCustomModelData(arena.getId());
        lever.setItemMeta(leverMeta);

        player.sendMessage(FNAF.getMessages().getPREFIX() + " §aYou got the lever for the arena §l" + arena.getName());
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.5f);

        player.getInventory().addItem(lever);
    }

    public void removeAllDoors(Player player, Arena arena) {
        DoorDAO doorDAO = new DoorDAO();
        ArrayList<Door> doors = doorDAO.getAllByArena(arena.getId());

        if (doors == null) {
            player.sendMessage(FNAF.getMessages().getPREFIX() + " §cThere are no doors to remove");
            player.playSound(player.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1, 0.5f);
            return;
        }

        for (Door door : doors) {
            doorDAO.delete(door.getId());
        }

        player.sendMessage(FNAF.getMessages().getPREFIX() + " §aYou removed §lall§a the doors from §l" + arena.getName());
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.5f);
    }
}