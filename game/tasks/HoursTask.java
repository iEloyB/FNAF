package org.de.eloy.fnaf.game.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.de.eloy.fnaf.game.manager.GameManager;
import org.de.eloy.fnaf.game.manager.GameState;

public class HoursTask extends BukkitRunnable {
    private int currentTimeSeconds = 0;
    private int lastHour = -1;
    private int SECONDS_PER_HOUR;
    private static final int START_HOUR = 12;
    private final GameManager gameManager;
    public HoursTask(GameManager gameManager) {
        this.gameManager = gameManager;
        calculateSecondsPerHour();
    }
    private void calculateSecondsPerHour() {
        SECONDS_PER_HOUR = Math.round((float) gameManager.getGameDuration() / 6);
    }

    @Override
    public void run() {
        if (currentTimeSeconds >= gameManager.getGameDuration()) {
            gameManager.setGameState(GameState.ENDING);
            cancel();
            return;
        }

        int currentHour = (START_HOUR + (currentTimeSeconds / SECONDS_PER_HOUR)) % 12;

        if (currentHour == 0) currentHour = 12;

        if (currentHour != lastHour) {
            lastHour = currentHour;
            gameManager.setHour(currentHour);
        }

        if (gameManager.getBattery() != -1) {
            gameManager.updateBattery();
            gameManager.updateUsage();
        }

        gameManager.updateScoreboards();

        currentTimeSeconds++;
    }
}
