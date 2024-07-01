package org.de.eloy.fnaf.game.objects.animatronics.fnaf1;

import org.bukkit.Location;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.game.objects.Animatronic;
import org.de.eloy.fnaf.game.objects.Ability;

public class Chica extends Animatronic {
    public Chica(Location spawn) {
        super(spawn);
        setName("chica");
        setAbility1(new Ability("Poisoned Cupcake",true));
        setAbility2(new Ability("Jumpscare",true));
        setUltimate(new Ability("? ? ?",true));

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
