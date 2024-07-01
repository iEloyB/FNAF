package org.de.eloy.fnaf.game.listeners;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.game.manager.GameManager;
import org.de.eloy.fnaf.game.manager.GameState;
import org.de.eloy.fnaf.game.objects.Guard;

public class CamerasListener implements Listener {
    private final GameManager gameManager;

    public CamerasListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onMovingDuringCameras(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!gameManager.getArena().getServerPort().equals(String.valueOf(player.getServer().getPort()))) return;
        if (!(gameManager.gameState.equals(GameState.INGAME))) return;
        if (!gameManager.isGuardOnCameras()) return;
        if (!(gameManager.getRoleByPlayer(player) instanceof Guard)) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void enterCameras(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!gameManager.getArena().getServerPort().equals(String.valueOf(player.getServer().getPort()))) return;
        if (!(gameManager.gameState.equals(GameState.INGAME))) return;
        if (gameManager.isGuardOnCameras()) return;

        if (player.getInventory().getItemInMainHand().getItemMeta() == null) return;
        if (!(gameManager.getRoleByPlayer(player) instanceof Guard)) return;
        if (!(player.getLocation().getPitch() >= 48)) return;

        String enterCamerasItemName = FNAF.getMessages().getENTER_CAMERAS();
        String itemName = player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
        if (!(itemName.equals(enterCamerasItemName))) return;

        gameManager.getCamerasTask().enterCameras();
    }

    @EventHandler
    public void leaveCameras(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (!event.isSneaking()) return;

        if (!gameManager.getArena().getServerPort().equals(String.valueOf(player.getServer().getPort()))) return;
        if (!(gameManager.gameState.equals(GameState.INGAME))) return;
        if (!gameManager.isGuardOnCameras()) return;

        if (!(gameManager.getRoleByPlayer(player) instanceof Guard)) return;

        gameManager.getCamerasTask().leaveCameras();
    }

    @EventHandler
    public void previousCamera(EntityDamageByEntityEvent event) {
        Player player = null;
        if (event.getDamager() instanceof Player) player = (Player) event.getDamager();

        if (player == null) return;
        if (!gameManager.getArena().getServerPort().equals(String.valueOf(player.getServer().getPort()))) return;
        if (!(gameManager.gameState.equals(GameState.INGAME))) return;
        if (!gameManager.isGuardOnCameras()) return;
        if (event.getEntityType() != EntityType.VILLAGER && !(event.getEntity() instanceof Villager)) return;

        Villager villager = (Villager) event.getEntity();
        if (!villager.getName().equals("cams")) return;
        if (!(event.getDamager() instanceof Player)) return;

        event.setCancelled(true);
        gameManager.getCamerasTask().previousCamera();
    }

    @EventHandler
    public void nextCamera(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if (!gameManager.getArena().getServerPort().equals(String.valueOf(player.getServer().getPort()))) return;
        if (!(gameManager.gameState.equals(GameState.INGAME))) return;
        if (!gameManager.isGuardOnCameras()) return;
        if (event.getRightClicked().getType() != EntityType.VILLAGER) return;

        Villager villager = (Villager) event.getRightClicked();
        if (!villager.getName().equals("cams")) return;

        event.setCancelled(true);
        gameManager.getCamerasTask().nextCamera();
    }

    @EventHandler
    public void onCamDamage(EntityDamageEvent event) {

        if (!gameManager.getArena().getServerPort().equals(String.valueOf(event.getEntity().getServer().getPort()))) return;
        if (!(gameManager.gameState.equals(GameState.INGAME))) return;
        if (!gameManager.isGuardOnCameras()) return;

        if (event.getEntity().getType() != EntityType.VILLAGER) return;

        Villager villager = (Villager) event.getEntity();
        if (!villager.getName().equals("cams")) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onDropitem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (!gameManager.getArena().getServerPort().equals(String.valueOf(player.getServer().getPort()))) return;
        if (gameManager.gameState != GameState.INGAME) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (!gameManager.getArena().getServerPort().equals(String.valueOf(player.getServer().getPort()))) return;
        if (gameManager.gameState != GameState.INGAME) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteractArmorStand(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (!gameManager.getArena().getServerPort().equals(String.valueOf(player.getServer().getPort()))) return;

        event.getRightClicked();
        if (!(event.getRightClicked() instanceof ArmorStand)) return;
        ArmorStand armorStand = (ArmorStand) event.getRightClicked();

        if (!armorStand.isCustomNameVisible()) event.setCancelled(true);
    }
}
