package org.de.eloy.fnaf.gui.system.setup.arenas.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.database.dao.ArenaDAO;
import org.de.eloy.fnaf.game.objects.Arena;
import org.de.eloy.fnaf.gui.system.setup.arenas.guis.ConfirmRemoveAllGUI;
import org.de.eloy.fnaf.gui.system.setup.arenas.guis.EditArenaGUI;

public class ArenasListListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String inventoryTitle = event.getView().getOriginalTitle();
        ItemStack item = event.getCurrentItem();

        if (!inventoryTitle.equalsIgnoreCase("§6§lArenas List") || item == null) return;

        event.setCancelled(true);

        switch (item.getType()) {
            case COMMAND_BLOCK:
                openArenaInventory(player, item);
                break;

            case EMERALD_BLOCK:
                sendCreateArenaMessage(player);
                break;

            case RED_STAINED_GLASS_PANE:
                player.closeInventory();
                break;

            case BARRIER:
                player.openInventory(ConfirmRemoveAllGUI.getGUI());
                break;

            default:
                break;
        }
    }

    private static void openArenaInventory(Player player, ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();

        if (itemMeta == null) return;
        if (!itemMeta.hasCustomModelData()) return;
        if (!item.getType().equals(Material.COMMAND_BLOCK)) return;

        int arenaId = itemMeta.getCustomModelData();
        Arena arena = new ArenaDAO().getById(arenaId);
        if (arena != null) {
            player.openInventory(EditArenaGUI.getGUI(arena));
        }
    }

    private static void sendCreateArenaMessage(Player player) {
        player.closeInventory();
        player.sendMessage(FNAF.getMessages().getPREFIX() + " §fTo create an arena, use the command §e/fnaf arena create [name]§f.");
    }
}
