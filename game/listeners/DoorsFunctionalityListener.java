package org.de.eloy.fnaf.game.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.game.manager.GameManager;
import org.de.eloy.fnaf.game.manager.GameState;
import org.de.eloy.fnaf.game.objects.Door;
import org.de.eloy.fnaf.game.objects.Guard;

public class DoorsFunctionalityListener implements Listener {
    private final GameManager gameManager;
    public DoorsFunctionalityListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onLeverInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.getWorld() != gameManager.getArena().getGuard().getSpawn().getWorld()) return;
        if (!gameManager.getArena().getServerPort().equals(String.valueOf(player.getServer().getPort()))) return;
        event.setCancelled(true);

        if (!(gameManager.getRoleByPlayer(player) instanceof Guard)) return;
        if (gameManager.getGuard() == null) return;
        if (gameManager.getBattery() <= 0) return;
        if (gameManager.gameState != GameState.INGAME) return;

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null) return;
        if (event.getClickedBlock().getType() != Material.LEVER) return;

        if (gameManager.isGuardOnCameras()) return;
        Player guardPlayer = gameManager.getGuard();
        if (!player.equals(guardPlayer)) return;

        event.setCancelled(false);
        Location blockLocation = event.getClickedBlock().getLocation();

        Door door = getDoorByLocation(blockLocation);

        assert door != null;
        if (door.isInCooldown()) {
            event.setCancelled(true);
            player.sendMessage(FNAF.getMessages().getPREFIX() + " "+FNAF.getMessages().getDOOR_COOLDOWN());
        }
        else {
            door.toggle(gameManager.getPlugin());
            gameManager.updateDoorCount();
            gameManager.updateUsage();
            gameManager.updateScoreboards();
        }

    }

    private Door getDoorByLocation(Location location) {
        for (Door door : gameManager.getArena().getDoors()) {
            if (door.getLeverLocation().equals(location)) {
                return door;
            }
        }
        return null;
    }
}
