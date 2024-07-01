package org.de.eloy.fnaf.game.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.game.Game;
import org.de.eloy.fnaf.game.manager.GameState;
import org.de.eloy.fnaf.game.tasks.GiveWaitingLobbyItemsTask;
import org.de.eloy.fnaf.scoreboard.system.boards.SelectRolesBoard;

public class PlayerJoinLeaveListeners implements Listener {
    private final Game game;

    public PlayerJoinLeaveListeners(Game game) {
        this.game = game;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage("");

        if (game.getGameManager().gameState != GameState.WAITING && game.getGameManager().gameState != GameState.STARTING) {
            player.sendMessage(FNAF.getMessages().getPREFIX() + " " + FNAF.getMessages().getARENA_FULL());

            String defaultServer = FNAF.getFiles().getConfigFile().getMessage("default_lobby_server");
            game.getGameManager().getPlugin().movePlayerToServer(player, defaultServer);
            return;
        }

        if (!game.getGameManager().getArena().getServerPort().equals(String.valueOf(player.getServer().getPort())))
            return;

        String prefix = FNAF.getMessages().getPREFIX();
        String onlinePlayers = String.valueOf(Bukkit.getOnlinePlayers().size());
        String maxPlayers = String.valueOf(game.getGameManager().getArena().getAnimatronics().size() + 1);
        String joinMSG = FNAF.getMessages().getJOIN_MESSAGE()
                .replace("{player}", player.getName())
                .replace("{current}", onlinePlayers)
                .replace("{max}", maxPlayers);
        event.setJoinMessage(prefix + " " + joinMSG);
        game.getGameManager().addPlayer(player);

        GameState gameState = game.getGameManager().gameState;
        if (gameState == GameState.WAITING || gameState == GameState.STARTING) {
            playerJoinEvents(player);
        }

        player.setHealth(20);
        player.setFoodLevel(100);
        player.removePotionEffect(PotionEffectType.INVISIBILITY);

        game.getGameManager().getShowInfoTask().showSelectRolesInfo(player);

        game.checkGameReadyToPlay();
    }

    private void playerJoinEvents(Player player) {
        player.teleport(game.getGameManager().getArena().getWaitingLobbySpawn());
        GiveWaitingLobbyItemsTask giveWaitingLobbyItemsTask = new GiveWaitingLobbyItemsTask();

        player.getInventory().clear();
        giveWaitingLobbyItemsTask.giveItems(player);

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1f);

        SelectRolesBoard selectRolesBoard = new SelectRolesBoard(player, game.getGameManager());
        selectRolesBoard.createScoreboard();
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (!game.getGameManager().getArena().getServerPort().equals(String.valueOf(player.getServer().getPort())))
            return;

        String prefix = FNAF.getMessages().getPREFIX();
        String onlinePlayers = String.valueOf(Bukkit.getOnlinePlayers().size() - 1);
        String maxPlayers = String.valueOf(game.getGameManager().getArena().getAnimatronics().size() + 1);
        String quitMSG = FNAF.getMessages().getLEAVE_MESSAGE()
                .replace("{player}", player.getName())
                .replace("{current}", onlinePlayers)
                .replace("{max}", maxPlayers);
        event.setQuitMessage(prefix + " " + quitMSG);

        game.getGameManager().removePlayer(player);
        game.checkGameReadyToPlay();
    }
}