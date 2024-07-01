package org.de.eloy.fnaf.game;

import org.bukkit.entity.Player;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.game.manager.GameManager;
import org.de.eloy.fnaf.game.manager.GameState;
import org.de.eloy.fnaf.game.objects.Animatronic;
import org.de.eloy.fnaf.game.objects.Arena;
import org.de.eloy.fnaf.game.objects.Guard;

public class Game {
    private int id;
    private final GameManager gameManager;

    public Game(FNAF instance, Arena arena) {gameManager = new GameManager(instance, arena);}
    public boolean isGameReady() {
        boolean hasGuard = false;
        boolean hasAnimatronic = false;

        for (Player player : gameManager.getPlayerRoles().keySet()) {
            Object role = gameManager.getPlayerRoles().get(player);

            if (role instanceof Guard) hasGuard = true;
            else if (role instanceof Animatronic) hasAnimatronic = true;


            if (hasGuard && hasAnimatronic) return true;
        }

        return false;
    }

    public void checkGameReadyToPlay() {
        GameState gameState = getGameManager().gameState;

        switch (gameState) {
            case WAITING:
                if (isGameReady()) getGameManager().setGameState(GameState.STARTING);
            case STARTING:
                if (!isGameReady()) getGameManager().setGameState(GameState.WAITING);
                break;

            case INGAME:
                if (!isGameReady()) getGameManager().setGameState(GameState.ENDING);
                break;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public GameManager getGameManager() {
        return gameManager;
    }
}
