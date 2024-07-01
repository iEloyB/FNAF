package org.de.eloy.fnaf.game.objects.animatronics.fnaf1;

import org.bukkit.Location;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.game.objects.Animatronic;
import org.de.eloy.fnaf.game.objects.Ability;

public class Foxy extends Animatronic {
    public Foxy(Location spawn) {
        super(spawn);
        setName("foxy");
        this.ability1 = new Ability("Starting show",true);
        this.ability2 = new Ability("Pirate coin",true);
        this.ultimate = new Ability("Bloodlust",true);
        damage = 1;
    }

    @Override
    public void hability1Task(FNAF plugin) {

    }

    @Override
    public void hability2Task(FNAF plugin) {

    }

    @Override
    public void ultimateTask(FNAF plugin) {

    }
}
