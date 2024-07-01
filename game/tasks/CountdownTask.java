package org.de.eloy.fnaf.game.tasks;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.game.manager.GameManager;
import org.de.eloy.fnaf.game.manager.GameState;
import org.de.eloy.fnaf.scoreboard.system.boards.SelectRolesBoard;

public class CountdownTask extends BukkitRunnable {
    private final GameManager gameManager;
    private int timeLeft = FNAF.getFiles().getConfigFile().getInt("starting_game_Time");
    private final String PREFIX = FNAF.getMessages().getPREFIX();
    private final String GAME_STARTING = FNAF.getMessages().getGAME_STARTING();

    public CountdownTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        if (timeLeft <= 0) {
            cancel();

            for (Player player : gameManager.getPlayers()) {
                System.out.println(player.getName());
            }

            gameManager.setGameState(GameState.PREGAME);
        }

        switch (timeLeft) {
            case 30:
            case 20:
            case 10:
            case 9:
            case 8:
            case 7:
            case 6:
                playSound();
                break;
            case 5:
                playSoundWithVolume(0.5f);
                break;
            case 4:
                playSoundWithVolume(0.6f);
                break;
            case 3:
                playSoundWithVolume(0.7f);
                break;
            case 2:
                playSoundWithVolume(0.8f);
                break;
            case 1:
                playSoundWithVolume(0.9f);
                break;
            case 0:
                playSoundWithVolume(1f);
                break;
        }

        gameManager.setCountdownTimeLeft(timeLeft);
        for (Player player : gameManager.getPlayers()) {
            if (timeLeft != 0) player.sendMessage(PREFIX + " " + GAME_STARTING.replace("{seconds}", String.valueOf(timeLeft)));
            gameManager.getShowInfoTask().showSelectRolesInfo(player);
        }

        timeLeft--;
    }

    private void playSound() {
        for (Player player : gameManager.getPlayers()) {
            player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1, 1f);
        }
    }

    private void playSoundWithVolume(float volume) {
        for (Player player : gameManager.getPlayers()) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, volume);
        }
    }

    public int getTimeLeft() {
        return timeLeft;
    }
}