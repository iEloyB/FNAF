package org.de.eloy.fnaf.game.objects;

import org.bukkit.Location;

public class Guard {
    private int id;
    private Location spawn;
    private int hp;
    private boolean onCameras;
    private int arenaIndex;

    public Guard(Location spawn, int arenaIndex) {
        this.spawn = spawn;
        this.onCameras = false;
        this.hp = -1;
        this.arenaIndex = arenaIndex;
    }

    public void enterCameras() {
        onCameras = !onCameras;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isOnCameras() {
        return onCameras;
    }

    public void setOnCameras(boolean onCameras) {
        this.onCameras = onCameras;
    }

    public int getArenaIndex() {
        return arenaIndex;
    }

    public void setArenaIndex(int arenaIndex) {
        this.arenaIndex = arenaIndex;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}