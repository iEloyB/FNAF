package org.de.eloy.fnaf.gui.system.game.gui.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.game.Game;
import org.de.eloy.fnaf.game.objects.Animatronic;
import org.de.eloy.fnaf.gui.system.game.gui.guis.SelectRolesGUI;
import org.de.eloy.fnaf.scoreboard.system.boards.SelectRolesBoard;

public class SelectRolesListener implements Listener {

    private final Game game;
    public SelectRolesListener(Game game) {
        this.game = game;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (!game.getGameManager().getArena().getServerPort().equals(String.valueOf(player.getServer().getPort()))) return;

        event.setCancelled(true);

        boolean roleAssigned = false;
        String roleName = null;

        if (item == null) return;
        if (item.getItemMeta() == null) return;
        if (!item.getItemMeta().hasCustomModelData()) return;
        int itemId = item.getItemMeta().getCustomModelData();

        if (itemId == 99999999) {
            roleAssigned = game.getGameManager().assignRole(player, game.getGameManager().getArena().getGuard());
            roleName = "Guard";
        }

        for (Animatronic animatronic : game.getGameManager().getArena().getAnimatronics()) {
            if (animatronic.getId() == itemId) {
                int animatronicId = animatronic.getId();
                roleAssigned = game.getGameManager().assignRole(player, getAnimatronic(animatronicId,game));
                roleName = animatronic.getName().toUpperCase();
                break;
            }
        }

        if (!roleAssigned) {
            assert roleName != null;
            player.sendMessage(FNAF.getMessages().getROLE_ALREADY_ASSINED().replace("{role}",roleName));
            player.playSound(player.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1, 0.5f);
        } else {
            player.sendMessage(FNAF.getMessages().getASSIGNED_ROLE().replace("{role}",roleName));
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1.5f);
        }

        game.checkGameReadyToPlay();

        player.openInventory(SelectRolesGUI.getGUI(game));
        game.getGameManager().getShowInfoTask().showSelectRolesInfo(player);

        SelectRolesBoard selectRolesBoard = new SelectRolesBoard(player, game.getGameManager());
        selectRolesBoard.update();

        checkAndReopenInventory();
    }

    private Animatronic getAnimatronic(int id, Game game) {
        for (Animatronic animatronic : game.getGameManager().getArena().getAnimatronics()) {
            if (animatronic.getId() == id) return animatronic;
        }
        return null;
    }

    public void checkAndReopenInventory() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getOpenInventory();
            Bukkit.getScheduler().runTaskLater(game.getGameManager().getPlugin(), () -> {
                player.openInventory(SelectRolesGUI.getGUI(game));
            }, 1L);
        }

    }
}