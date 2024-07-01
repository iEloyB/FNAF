package org.de.eloy.fnaf.game.objects.animatronics.fnaf1;

import org.bukkit.Location;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.game.objects.Animatronic;
import org.de.eloy.fnaf.game.objects.Ability;

public class Freddy extends Animatronic {
    public Freddy(Location spawn) {
        super(spawn);
        setName("freddy");
        setAbility1(new Ability("Brute Force",true));
        setAbility2(new Ability("Energy charge",true));
        setUltimate(new Ability("Energy Discharge",true));

        damage = 2;
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
