package org.de.eloy.fnaf.gui.system.setup.arenas.listeners;

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
import org.de.eloy.fnaf.database.dao.LightDAO;
import org.de.eloy.fnaf.game.objects.Arena;
import org.de.eloy.fnaf.game.objects.Light;
import org.de.eloy.fnaf.gui.system.setup.arenas.guis.ArenasListGUI;

import java.util.ArrayList;

public class LightsMenuListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String title = player.getOpenInventory().getTitle();
        ItemStack item = event.getCurrentItem();

        Arena arena = null;

        ArenaDAO arenaDAO = new ArenaDAO();
        ArrayList<Arena> arenas = arenaDAO.getAll();

        for (Arena a : arenas) {
            String arenaName = "§6§l" + a.getName() + "'s lights";
            if (arenaName.equalsIgnoreCase(title)) arena = a;
        }

        if (arena == null || item == null) return;
        event.setCancelled(true);

        Material itemType = item.getType();

        switch (itemType) {
            case REDSTONE_TORCH:
                giveAddLightsBlock(player, arena);
                player.closeInventory();
                break;
            case BARRIER:
                removeAllLights(player, arena);
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
    public void onLightAdded(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        Player player = event.getPlayer();
        Block block = event.getBlock();
        ItemMeta itemMeta = item.getItemMeta();

        if (!block.getType().equals(Material.REDSTONE_LAMP)) return;
        if (itemMeta == null) return;
        if (!itemMeta.hasCustomModelData()) return;

        int arenaId = itemMeta.getCustomModelData();
        Arena arena = new ArenaDAO().getById(arenaId);
        if (arena != null) {
            LightDAO lightDAO = new LightDAO();
            lightDAO.insert(new Light(block.getLocation(), arenaId));

            player.sendMessage(FNAF.getMessages().getPREFIX() + " §aYou added a new light in §l" + arena.getName());
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.5f);
        } else {
            player.sendMessage(FNAF.getMessages().getPREFIX() + " §cThere was an error adding the light");
            player.playSound(player.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1, 0.5f);
        }
    }
    private void giveAddLightsBlock(Player player, Arena arena) {
        ItemStack light = new ItemStack(Material.REDSTONE_LAMP);
        ItemMeta lightMeta = light.getItemMeta();

        assert lightMeta != null;
        lightMeta.setDisplayName("§6Add lights §e(" + arena.getName() + ")");
        lightMeta.setCustomModelData(arena.getId());
        light.setItemMeta(lightMeta);

        player.sendMessage(FNAF.getMessages().getPREFIX() + " §aYou got the add-lights block for the arena §l" + arena.getName());
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.5f);

        player.getInventory().addItem(light);
    }

    public void removeAllLights(Player player, Arena arena) {
        LightDAO lightDAO = new LightDAO();
        ArrayList<Light> lights = lightDAO.getAllByArena(arena.getId());

        if (lights == null) {
            player.sendMessage(FNAF.getMessages().getPREFIX() + " §cThere are no lights to remove");
            player.playSound(player.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1, 0.5f);
            return;
        }

        for (Light light : lights) {
            lightDAO.delete(light.getId());
            player.getInventory().remove(Material.REDSTONE_LAMP);
        }

        player.sendMessage(FNAF.getMessages().getPREFIX() + " §aYou removed §lall§a the lights from §l" + arena.getName());
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.5f);
    }
}