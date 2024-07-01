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
import org.de.eloy.fnaf.game.objects.animatronics.fnaf1.Bonnie;
import org.de.eloy.fnaf.game.objects.animatronics.fnaf1.Chica;
import org.de.eloy.fnaf.game.objects.animatronics.fnaf1.Foxy;
import org.de.eloy.fnaf.game.objects.animatronics.fnaf1.Freddy;
import org.de.eloy.fnaf.gui.system.setup.arenas.guis.AnimatronicsMenuGUI;

import java.util.ArrayList;

public class AnimatronicListListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String title = player.getOpenInventory().getTitle();
        ItemStack item = event.getCurrentItem();

        Arena arena = null;

        ArenaDAO arenaDAO = new ArenaDAO();
        ArrayList<Arena> arenas = arenaDAO.getAll();

        for (Arena a : arenas) {
            String arenaName = "§6§l" + a.getName() + "'s animatronics list";
            if (arenaName.equalsIgnoreCase(title)) arena = a;
        }

        if (arena == null || item == null) return;
        event.setCancelled(true);

        String itemName = "";
        if (item.getItemMeta() != null) itemName = item.getItemMeta().getDisplayName();

        AnimatronicDAO animatronicDAO = new AnimatronicDAO();
        ArrayList<Animatronic> animatronics = animatronicDAO.getAllByArena(arena.getId());

        if (animatronics != null) {
            if (animatronics.size() >= 20) {
                player.sendMessage(FNAF.getMessages().getPREFIX()+" §cThere can't be more than 20 animatronics.");
                player.playSound(player.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1, 0.5f);
                return;
            }
        }

        switch (itemName.toLowerCase()) {
            case "§6freddy":
                Freddy freddy = new Freddy(player.getLocation());
                freddy.setArenaIndex(arena.getId());
                animatronicDAO.insert(freddy);

                animatronicMessage(player, arena);
                player.openInventory(AnimatronicsMenuGUI.getGUI(arena));
                break;

            case "§echica":
                Chica chica = new Chica(player.getLocation());
                chica.setArenaIndex(arena.getId());
                animatronicDAO.insert(chica);

                animatronicMessage(player, arena);
                player.openInventory(AnimatronicsMenuGUI.getGUI(arena));
                break;
            case "§cfoxy":
                Foxy foxy = new Foxy(player.getLocation());
                foxy.setArenaIndex(arena.getId());
                animatronicDAO.insert(foxy);

                animatronicMessage(player, arena);
                player.openInventory(AnimatronicsMenuGUI.getGUI(arena));
                break;
            case "§bbonnie":
                Bonnie bonnie = new Bonnie(player.getLocation());
                bonnie.setArenaIndex(arena.getId());
                animatronicDAO.insert(bonnie);

                animatronicMessage(player, arena);
                player.openInventory(AnimatronicsMenuGUI.getGUI(arena));
                break;
        }

        Material itemType = item.getType();
        switch (itemType) {
            case ARROW:
                player.openInventory(AnimatronicsMenuGUI.getGUI(arena));
                break;
            case RED_STAINED_GLASS_PANE:
                player.closeInventory();
                break;
        }
    }
    private void animatronicMessage(Player player, Arena arena) {
        player.sendMessage(FNAF.getMessages().getPREFIX()+" " +
                "§aYou added a new animatronic in §6"+arena.getName()+"§e " +
                "(§f"+player.getLocation().getBlockX()+"," +
                "§f"+player.getLocation().getBlockY()+"," +
                "§f"+player.getLocation().getBlockZ()+"§e).");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.5f);
    }
    
}