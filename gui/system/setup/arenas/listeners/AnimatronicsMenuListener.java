package org.de.eloy.fnaf.gui.system.setup.arenas.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.database.dao.AnimatronicDAO;
import org.de.eloy.fnaf.database.dao.ArenaDAO;
import org.de.eloy.fnaf.game.objects.Animatronic;
import org.de.eloy.fnaf.game.objects.Arena;
import org.de.eloy.fnaf.gui.system.setup.arenas.guis.AnimatronicListGUI;
import org.de.eloy.fnaf.gui.system.setup.arenas.guis.EditArenaGUI;

import java.util.ArrayList;

public class AnimatronicsMenuListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String title = player.getOpenInventory().getTitle();
        ItemStack item = event.getCurrentItem();

        Arena arena = null;

        ArenaDAO arenaDAO = new ArenaDAO();
        ArrayList<Arena> arenas = arenaDAO.getAll();

        for (Arena a : arenas) {
            String arenaName = "§6§l" + a.getName() + "'s animatronics";
            if (arenaName.equalsIgnoreCase(title)) arena = a;
        }

        if (arena == null || item == null) return;
        event.setCancelled(true);

        switch (item.getType()) {
            case SKELETON_SKULL:
                //TODO change animatronic position
                break;

            case EMERALD_BLOCK:
                player.openInventory(AnimatronicListGUI.getGUI(arena));
                break;

            case RED_STAINED_GLASS_PANE:
                player.closeInventory();
                break;

            case BARRIER:
                removeAllAnimatronics(player,arena);
                break;

            case ARROW:
                player.openInventory(EditArenaGUI.getGUI(arena));
                break;

            default:
                break;
        }
    }

    public void removeAllAnimatronics(Player player, Arena arena) {
        AnimatronicDAO animatronicDAO = new AnimatronicDAO();
        ArrayList<Animatronic> animatronics = animatronicDAO.getAllByArena(arena.getId());

        if (animatronics == null) {
            player.sendMessage(FNAF.getMessages().getPREFIX() + " §cThere are no animatronics to remove");
            player.playSound(player.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1, 0.5f);
            return;
        }

        for (Animatronic animatronic : animatronics) {
            animatronicDAO.delete(animatronic.getId());
            player.getInventory().remove(Material.REDSTONE_LAMP);
        }

        player.sendMessage(FNAF.getMessages().getPREFIX() + " §aYou removed §lall§a the animatronics from §l" + arena.getName());
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.5f);
    }
}
