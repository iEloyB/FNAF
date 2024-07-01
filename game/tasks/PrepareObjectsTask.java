package org.de.eloy.fnaf.game.tasks;

import org.de.eloy.fnaf.game.manager.GameManager;
import org.de.eloy.fnaf.game.objects.Door;
import org.de.eloy.fnaf.game.objects.Light;

public class PrepareObjectsTask {
    private final GameManager gameManager;

    public PrepareObjectsTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void prepare() {
        prepareDoors();
        prepareLights();
    }

    public void turnAllOff() {
        for (Door door : gameManager.getArena().getDoors()) {
            door.open(gameManager.getPlugin());
        }

        for (Light light : gameManager.getArena().getLights()) {
            light.turnOff();
        }
    }

    private void prepareDoors() {
        for (Door door : gameManager.getArena().getDoors()) {
            door.open(gameManager.getPlugin());
        }
    }

    private void prepareLights() {
        for (Light light : gameManager.getArena().getLights()) {
            light.turnOn();
        }
    }
}
