package org.de.eloy.fnaf.game.objects;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.Lightable;

public class Light {
    private int id;
    private Location location;
    private boolean on;
    private int arenaIndex;
    public Light(Location location, int arenaIndex) {
        this.location = location;
        this.on = false;
        this.arenaIndex = arenaIndex;
    }

    public void switchPower() {
        if (on) turnOff();
        else turnOn();
    }
    public void turnOn() {
        if (!on) {
            on = true;
            final Block lampBlock = location.getBlock();
            Lightable lightable = (Lightable) lampBlock.getBlockData();
            lightable.setLit(true);
            lampBlock.setBlockData(lightable);
        }
    }
    public void turnOff() {
        if (on) {
            on = false;
            final Block lampBlock = location.getBlock();
            Lightable lightable = (Lightable) lampBlock.getBlockData();
            lightable.setLit(false);
            lampBlock.setBlockData(lightable);
        }
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
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

    @Override
    public String toString() {
        return "Light{" +
                "id=" + id +
                ", location=" + location +
                ", on=" + on +
                ", arenaIndex=" + arenaIndex +
                '}';
    }
}