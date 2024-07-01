package org.de.eloy.fnaf.game.objects;

public class Ability {
    private String name;
    private boolean ready;
    private int cooldown;

    public Ability(String name, boolean status) {
        this.name = name;
        this.ready = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean status) {
        this.ready = status;
    }
    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
}
