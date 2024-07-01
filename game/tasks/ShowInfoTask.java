package org.de.eloy.fnaf.game.tasks;

import org.bukkit.entity.Player;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.game.manager.GameManager;
import org.de.eloy.fnaf.game.manager.GameState;
import org.de.eloy.fnaf.game.objects.Animatronic;
import org.de.eloy.fnaf.game.objects.Guard;
import org.de.eloy.fnaf.scoreboard.system.boards.AnimatronicBoard;
import org.de.eloy.fnaf.scoreboard.system.boards.GuardBoard;
import org.de.eloy.fnaf.scoreboard.system.boards.SelectRolesBoard;

import java.util.List;

public class ShowInfoTask {
    private final GameManager gameManager;

    public ShowInfoTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void showScoreboardInfo() {
        for (Player player : gameManager.getAnimatronics()) {
            showAnimatronicInfo(player);
        }

        showGuardInfo(gameManager.getGuard());
    }

    public void showSelectRolesInfo(Player player) {
        SelectRolesBoard selectRolesBoard = new SelectRolesBoard(player,gameManager);
        selectRolesBoard.update();
    }
    private void showAnimatronicInfo(Player player) {
        AnimatronicBoard animatronicBoard = new AnimatronicBoard(player, gameManager);
        animatronicBoard.update();
    }

    private void showGuardInfo(Player player) {
        GuardBoard guardBoard = new GuardBoard(player, gameManager);
        guardBoard.update();
    }
}

