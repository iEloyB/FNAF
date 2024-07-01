package org.de.eloy.fnaf.game.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.de.eloy.fnaf.game.manager.GameManager;
import org.de.eloy.fnaf.game.manager.GameState;
import org.de.eloy.fnaf.game.objects.Animatronic;
import org.de.eloy.fnaf.game.objects.animatronics.fnaf1.Bonnie;
import org.de.eloy.fnaf.game.objects.animatronics.fnaf1.Chica;
import org.de.eloy.fnaf.game.objects.animatronics.fnaf1.Foxy;
import org.de.eloy.fnaf.game.objects.animatronics.fnaf1.Freddy;

public class HealthAndHungerListener implements Listener {
    private final GameManager gameManager;

    public HealthAndHungerListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onHitGuard(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!(event.getDamager() instanceof Player)) return;

        Player damager = (Player) event.getDamager();
        Player damaged = (Player) event.getEntity();

        if (damaged.getWorld() != gameManager.getArena().getWaitingLobbySpawn().getWorld()) return;
        if (gameManager.isGuardOnCameras()) return;

        event.setCancelled(true);
        if (gameManager.gameState != GameState.INGAME) return;
        if (gameManager.isGuardOnCameras()) return;

        Player guardPlayer = gameManager.getGuard();
        if (!damaged.equals(guardPlayer)) return;

        event.setCancelled(false);
        event.setDamage(0);
        Animatronic animatronicPlayer = (Animatronic) gameManager.getRoleByPlayer(damager);

        int damageDone = animatronicPlayer.getDamage();

        if (guardPlayer.getHealth()-damageDone <= 0) {
            guardPlayer.setHealth(1);
            gameManager.setGuardHP(1);
            gameManager.setGameState(GameState.ENDING);
        } else {
            int newGuardHp = (int) (guardPlayer.getHealth()-damageDone);
            guardPlayer.setHealth(newGuardHp);
            gameManager.setGuardHP(newGuardHp);
        }
    }

    @EventHandler
    public void onEntityHeal(EntityRegainHealthEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();
        if (player.getWorld() != gameManager.getArena().getWaitingLobbySpawn().getWorld()) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityHunger(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();
        if (player.getWorld() != gameManager.getArena().getWaitingLobbySpawn().getWorld()) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onFalldamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) event.setCancelled(true);
    }
}
