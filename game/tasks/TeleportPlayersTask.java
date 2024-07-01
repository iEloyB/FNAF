package org.de.eloy.fnaf.game.tasks;

import org.bukkit.entity.Player;
import org.de.eloy.fnaf.game.manager.GameManager;
import org.de.eloy.fnaf.game.objects.Animatronic;
import org.de.eloy.fnaf.game.objects.Guard;

public class TeleportPlayersTask {
    private final GameManager gameManager;
    public TeleportPlayersTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }
    public void teleportAll() {
        for (Player player : gameManager.getAnimatronics()) {
            Animatronic animatronic = (Animatronic) gameManager.getRoleByPlayer(player);

            player.teleport(animatronic.getSpawn());
        }

        Guard guard = gameManager.getArena().getGuard();
        gameManager.getGuard().teleport(guard.getSpawn());
    }

    public void teleport(Player player) {
        Object role = gameManager.getRoleByPlayer(player);

        if (role instanceof Animatronic) player.teleport(((Animatronic) role).getSpawn());
        else player.teleport(((Guard) role).getSpawn());
    }
}