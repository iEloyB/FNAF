package org.de.eloy.fnaf.game.objects;

import org.bukkit.Particle;

public class Skin {
    private String name;
    private String playerHeadName;
    private String chestplateColor;
    private String leggingsColor;
    private String bootsColor;
    private Particle particle;
    private String rarity;

    private int animatronic_id;

    public Skin(String name, String playerHeadName, String chestplateColor, String leggingsColor, String bootsColor, Particle particle, String rarity, int skin_animatronic_id) {
        this.name = name;
        this.playerHeadName = playerHeadName;
        this.chestplateColor = chestplateColor;
        this.leggingsColor = leggingsColor;
        this.bootsColor = bootsColor;
        this.particle = particle;
        this.rarity = rarity;
        this.animatronic_id = skin_animatronic_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlayerHeadName() {
        return playerHeadName;
    }

    public void setPlayerHeadName(String playerHeadName) {
        this.playerHeadName = playerHeadName;
    }

    public String getChestplateColor() {
        return chestplateColor;
    }

    public void setChestplateColor(String chestplateColor) {
        this.chestplateColor = chestplateColor;
    }

    public String getLeggingsColor() {
        return leggingsColor;
    }

    public void setLeggingsColor(String leggingsColor) {
        this.leggingsColor = leggingsColor;
    }

    public String getBootsColor() {
        return bootsColor;
    }

    public void setBootsColor(String bootsColor) {
        this.bootsColor = bootsColor;
    }

    public Particle getParticle() {
        return particle;
    }

    public void setParticle(Particle particle) {
        this.particle = particle;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public int getAnimatronic_id() {
        return animatronic_id;
    }

    public void setAnimatronic_id(int animatronic_id) {
        this.animatronic_id = animatronic_id;
    }
}