package org.de.eloy.fnaf.game.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.de.eloy.fnaf.game.manager.GameManager;
import org.de.eloy.fnaf.game.manager.GameState;
import org.de.eloy.fnaf.game.objects.Animatronic;
import org.de.eloy.fnaf.game.objects.Guard;

public class StartCinematicTask extends BukkitRunnable {
    private final GameManager gameManager;
    private int timeLeftInTicks = 60;
    private int guardCinematicTick = 0;
    private int animatronicCinematicTick = 0;

    public StartCinematicTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    private void guardCinematic(Player player) {
        Guard guard = (Guard) gameManager.getRoleByPlayer(player);
        player.teleport(guard.getSpawn());

        String fullText = "Survive until 6AM";

        if (guardCinematicTick >= fullText.length()) {
            player.sendTitle(getTitleFromTicks(), "§4§n"+fullText, 0, 5, 0);
        } else {
            String text = fullText.substring(0, guardCinematicTick);
            player.sendTitle(getTitleFromTicks(), "§f"+text, 0, 5, 0);
        }

        guardCinematicTick++;
    }

    private void animatronicCinematic(Player player) {
        Animatronic animatronic = (Animatronic) gameManager.getRoleByPlayer(player);
        player.teleport(animatronic.getSpawn());

        String fullText = "Kill the guard before 6AM";

        if (animatronicCinematicTick >= fullText.length()) {
            player.sendTitle(getTitleFromTicks(), "§4§n"+fullText, 0, 5, 0);
        } else {
            String text = fullText.substring(0, animatronicCinematicTick);
            player.sendTitle(getTitleFromTicks(), "§f"+text, 0, 5, 0);
        }

        animatronicCinematicTick++;
    }

    private String getTitleFromTicks() {
        int timeLeftInSeconds = timeLeftInTicks / 20;

        switch (timeLeftInSeconds) {
            case 2:
                return "§c§l3";
            case 1:
                return "§6§l2";
            case 0:
                return "§e§l1";
            default:
                return null;
        }
    }


    @Override
    public void run() {
        timeLeftInTicks--;
        if (timeLeftInTicks <= 0) {
            cancel();
            gameManager.setGameState(GameState.INGAME);
        }

        guardCinematic(gameManager.getGuard());

        for (Player player : gameManager.getAnimatronics()) {
            animatronicCinematic(player);
        }

        if (timeLeftInTicks == 60) {
            for (Player player : gameManager.getAnimatronics()) {
                //TODO Scoreboard
            }
            //TODO Scoreboard
        }
    }
}
