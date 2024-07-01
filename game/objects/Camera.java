package org.de.eloy.fnaf.game.objects;

import org.bukkit.Location;

import java.util.Timer;
import java.util.TimerTask;

public class Camera {
    private int id;
    private Location location;
    private boolean broken;
    private float cooldown;
    private int arenaIndex;

    public Camera(Location location, int arenaIndex) {
        this.location = location;
        this.broken = false;
        this.cooldown = 0.0f;
        this.arenaIndex = arenaIndex;
    }

    public void breakCamera(int seconds) {
        broken = true;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                broken = false;
                System.out.println("camara restaurada");
                timer.cancel();
            }
        }, seconds * 1000L);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    public float getCooldown() {
        return cooldown;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
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
