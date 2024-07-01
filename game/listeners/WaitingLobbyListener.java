package org.de.eloy.fnaf.game.listeners;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.game.Game;
import org.de.eloy.fnaf.game.manager.GameState;
import org.de.eloy.fnaf.gui.system.game.gui.guis.SelectRolesGUI;

import java.util.ArrayList;

public class WaitingLobbyListener implements Listener {

    private final FNAF FNAF;
    private final Game game;

    public WaitingLobbyListener(FNAF FNAF, Game game) {
        this.FNAF = FNAF;
        this.game = game;
    }

    @EventHandler
    public void onItemClicked(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = event.getItem();

        if (game.getGameManager().gameState != GameState.WAITING && game.getGameManager().gameState != GameState.STARTING) return;
        if (!game.getGameManager().getArena().getServerPort().equals(String.valueOf(player.getServer().getPort()))) return;

        if (itemStack == null || itemStack.getItemMeta() == null) return;

        String itemTitle = itemStack.getItemMeta().getDisplayName();
        String selectRolesTitle = org.de.eloy.fnaf.FNAF.getMessages().getSELECT_ROLES_TITLE();
        String goBackTitle = org.de.eloy.fnaf.FNAF.getMessages().getGO_BACK_TITLE();
        if (itemTitle.equalsIgnoreCase(selectRolesTitle)) player.openInventory(SelectRolesGUI.getGUI(game));

        String server_name = org.de.eloy.fnaf.FNAF.getFiles().getConfigFile().getMessage("default_lobby_server");
        if (itemTitle.equalsIgnoreCase(goBackTitle)) FNAF.movePlayerToServer(player,server_name);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (game.getGameManager().gameState != GameState.WAITING && game.getGameManager().gameState != GameState.STARTING) return;
        if (!game.getGameManager().getArena().getServerPort().equals(String.valueOf(player.getServer().getPort()))) return;

        if (getGameFromPlayer(player) == null) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onItemMove(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (game.getGameManager().gameState != GameState.WAITING && game.getGameManager().gameState != GameState.STARTING) return;
        if (!game.getGameManager().getArena().getServerPort().equals(String.valueOf(player.getServer().getPort()))) return;

        if (getGameFromPlayer(player) == null) return;

        event.setCancelled(true);
    }

    private Game getGameFromPlayer(Player player) {
        ArrayList<Game> games = org.de.eloy.fnaf.FNAF.getPlayableGames();
        Game game = null;
        World gameWorld = this.game.getGameManager().getArena().getWaitingLobbySpawn().getWorld();

        if (gameWorld == null) return null;

        for (Game g : games) {
            if (gameWorld.equals(player.getWorld())) {
                game = g;
                break;
            }
        }

        return game;
    }

    @EventHandler
    public void onChangeHands(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();

        if (!game.getGameManager().getArena().getServerPort().equals(String.valueOf(player.getServer().getPort()))) return;

        event.setCancelled(true);
    }
}