package org.de.eloy.fnaf.game.objects.animatronics.fnaf1;

import org.bukkit.Location;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.game.objects.Animatronic;
import org.de.eloy.fnaf.game.objects.Ability;

public class Bonnie extends Animatronic {
    public Bonnie(Location spawn) {
        super(spawn);
        setName("bonnie");
        setAbility1(new Ability("Sharpen guitar",true));
        setAbility2(new Ability("Hard rock",true));
        setUltimate(new Ability("Endoskelleton",true));

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
