package org.de.eloy.fnaf.game.objects;

import org.bukkit.Location;

import java.util.ArrayList;

public class Arena {
    private String serverPort;
    private int id;
    private String name;
    private Location waitingLobbySpawn;
    private ArrayList<Door> doors;
    private ArrayList<Light> lights;
    private ArrayList<Camera> cameras;
    private ArrayList<Animatronic> animatronics;
    private Guard guard;
    private Location postGameSpawn;

    @Override
    public String toString() {
        return "Arena{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", waitingLobbySpawn=" + waitingLobbySpawn +
                ", doors=" + doors +
                ", lights=" + lights +
                ", cameras=" + cameras +
                ", animatronics=" + animatronics +
                ", guard=" + guard +
                ", postGameSpawn=" + postGameSpawn +
                '}';
    }

    public Arena(String name, Location waitingLobbySpawn, Location postGameSpawn, String serverPort) {
        this.name = name;
        this.waitingLobbySpawn = waitingLobbySpawn;
        this.postGameSpawn = postGameSpawn;
        this.doors = new ArrayList<>();
        this.lights = new ArrayList<>();
        this.cameras = new ArrayList<>();
        this.animatronics = new ArrayList<>();
        this.serverPort = serverPort;
    }

    public boolean isReady() {
        if (waitingLobbySpawn == null) return false;
        if (postGameSpawn == null) return false;
        if (doors == null) return false;
        if (lights == null) return false;
        if (cameras == null) return false;
        if (animatronics == null) return false;
        if (guard == null) return false;
        if (guard.getHp() == -1 || guard.getSpawn() == null) return false;

        return true;
    }

    public Arena(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getWaitingLobbySpawn() {
        return waitingLobbySpawn;
    }

    public void setWaitingLobbySpawn(Location waitingLobbySpawn) {
        this.waitingLobbySpawn = waitingLobbySpawn;
    }

    public ArrayList<Door> getDoors() {
        return doors;
    }

    public void setDoors(ArrayList<Door> doors) {
        this.doors = doors;
    }
    public ArrayList<Light> getLights() {
        return lights;
    }

    public void setLights(ArrayList<Light> lights) {
        this.lights = lights;
    }
    public ArrayList<Camera> getCameras() {
        return cameras;
    }

    public void setCameras(ArrayList<Camera> cameras) {
        this.cameras = cameras;
    }

    public ArrayList<Animatronic> getAnimatronics() {
        return animatronics;
    }

    public void setAnimatronics(ArrayList<Animatronic> animatronics) {
        this.animatronics = animatronics;
    }

    public Guard getGuard() {
        return guard;
    }

    public void setGuard(Guard guard) {
        this.guard = guard;
    }

    public Location getPostGameSpawn() {
        return postGameSpawn;
    }

    public void setPostGameSpawn(Location postGameSpawn) {
        this.postGameSpawn = postGameSpawn;
    }
    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }
}