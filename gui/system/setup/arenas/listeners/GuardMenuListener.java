package org.de.eloy.fnaf.gui.system.setup.arenas.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.database.dao.ArenaDAO;
import org.de.eloy.fnaf.database.dao.GuardDAO;
import org.de.eloy.fnaf.game.objects.Arena;
import org.de.eloy.fnaf.game.objects.Guard;
import org.de.eloy.fnaf.gui.system.setup.arenas.guis.ArenasListGUI;
import org.de.eloy.fnaf.gui.system.setup.arenas.guis.GuardMenuGUI;

import java.util.ArrayList;

public class GuardMenuListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String title = player.getOpenInventory().getTitle();
        ItemStack item = event.getCurrentItem();

        Arena arena = null;

        ArenaDAO arenaDAO = new ArenaDAO();
        ArrayList<Arena> arenas = arenaDAO.getAll();

        for (Arena a : arenas) {
            String arenaName = "§6§l" + a.getName() + "'s guard";
            if (arenaName.equalsIgnoreCase(title)) arena = a;
        }

        if (arena == null || item == null) return;
        event.setCancelled(true);

        Material itemType = item.getType();

        switch (itemType) {
            case HEART_POTTERY_SHERD:
                manageHP(player,arena,event.getClick());
                player.openInventory(GuardMenuGUI.getGUI(arena));
                break;
            case ENDER_PEARL:
                setGuardSpawn(player, arena);
                player.closeInventory();
                break;
            case BARRIER:
                removeGuard(player, arena);
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

    public void setGuardSpawn(Player player, Arena arena) {
        GuardDAO guardDAO = new GuardDAO();

        Guard guard = guardDAO.getById(arena.getId());

        if (guard == null) guardDAO.insert(new Guard(player.getLocation(), arena.getId()));
        else {
            guard.setSpawn(player.getLocation());
            guardDAO.edit(guard.getId(), guard);
        }

        player.sendMessage(FNAF.getMessages().getPREFIX()+" " +
                "§aYou have set guard's spawn in §6"+arena.getName()+"§e " +
                "(§f"+player.getLocation().getBlockX()+"," +
                "§f"+player.getLocation().getBlockY()+"," +
                "§f"+player.getLocation().getBlockZ()+"§e).");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.5f);
    }

    public void manageHP(Player player, Arena arena, ClickType clickType) {
        GuardDAO guardDAO = new GuardDAO();

        Guard guard = guardDAO.getById(arena.getId());

        if (guard == null) guardDAO.insert(new Guard(null, arena.getId()));
        else {
            if (clickType.isRightClick()) guard.setHp(addHP(guard.getHp()));
            else guard.setHp(removeHP(guard.getHp()));
            guardDAO.edit(guard.getId(), guard);
        }
        player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1.5f);
    }

    private int addHP(int currentHP) {
        if (currentHP == -1) return 10;
        else return currentHP+2;
    }

    private int removeHP(int currentHP) {
        if (currentHP <= 2) return 2;
        else return currentHP-2;
    }
    public void removeGuard(Player player, Arena arena) {
        GuardDAO guardDAO = new GuardDAO();
        Guard guard = guardDAO.getById(arena.getId());

        if (guard == null) {
            player.sendMessage(FNAF.getMessages().getPREFIX() + " §cThere's no guard to remove");
            player.playSound(player.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1, 0.5f);
            return;
        }

        guardDAO.delete(guard.getId());

        player.sendMessage(FNAF.getMessages().getPREFIX() + " §aYou removed §l"+arena.getName()+"'s guard.");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.5f);
    }
}