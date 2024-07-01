package org.de.eloy.fnaf.game.objects;

import org.bukkit.Location;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.messages.Message;

import java.util.ArrayList;
import java.util.List;

public abstract class Animatronic {
    private int id;
    private String name;
    private Location spawn;
    private ArrayList<Skin> skins;
    private int arenaIndex;
    protected Ability ability1;
    protected Ability ability2;
    protected Ability ultimate;

    protected int damage;

    public Animatronic(Location spawn) {
        this.spawn = spawn;
    }

    public Animatronic(int id, Location spawn) {
        this.id = id;
        this.spawn = spawn;
    }

    public abstract void hability1Task(FNAF plugin);

    public abstract void hability2Task(FNAF plugin);

    public abstract void ultimateTask(FNAF plugin);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public int getArenaIndex() {
        return arenaIndex;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Skin> getSkins() {
        return skins;
    }

    public void setSkins(ArrayList<Skin> skins) {
        this.skins = skins;
    }
    public Ability getAbility1() {
        return ability1;
    }
    public Ability getAbility2() {
        return ability2;
    }
    public Ability getUltimate() {
        return ultimate;
    }
    public void setArenaIndex(int arenaIndex) {
        this.arenaIndex = arenaIndex;
    }
    protected void setAbility1(Ability ability1) {
        this.ability1 = ability1;
    }
    protected void setAbility2(Ability ability2) {
        this.ability2 = ability2;
    }
    protected void setUltimate(Ability ultimate) {
        this.ultimate = ultimate;
    }
    public int getDamage() {
        return damage;
    }
    public static String getAnimatronicTitle(String animatronicName) {
        final Message MSG = FNAF.getMessages();
        switch (animatronicName.toLowerCase()) {
            case "freddy":
                return MSG.getFREDDY_TITLE();
            case "chica":
                return MSG.getCHICA_TITLE();
            case "foxy":
                return MSG.getFOXY_TITLE();
            case "bonnie":
                return MSG.getBONNIE_TITLE();
            case "guard":
                return MSG.getGUARD_TITLE();
        }
        return null;
    }

    public static List<String> getAnimatronicLore(String animatronicName) {
        final Message MSG = FNAF.getMessages();
        switch (animatronicName.toLowerCase()) {
            case "freddy":
                return MSG.getFREDDY_SUBTITLE();
            case "chica":
                return MSG.getCHICA_SUBTITLE();
            case "foxy":
                return MSG.getFOXY_SUBTITLE();
            case "bonnie":
                return MSG.getBONNIE_SUBTITLE();
            case "guard":
                return MSG.getGUARD_SUBTITLE();
        }
        return null;
    }
}

