package org.de.eloy.fnaf.scoreboard.system.boards;

import org.bukkit.entity.Player;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.game.manager.GameManager;
import org.de.eloy.fnaf.game.objects.Animatronic;
import org.de.eloy.fnaf.scoreboard.system.ScoreBoardBuilder;

import java.util.List;

public class AnimatronicBoard extends ScoreBoardBuilder {

    private final List<String> SCOREBOARD_DATA = FNAF.getMessages().getANIMATRONIC_SCOREBOARD();
    private final GameManager gameManager;
    private final Animatronic animatronic;

    public AnimatronicBoard(Player player, GameManager gameManager) {
        super(player);
        this.gameManager = gameManager;
        this.animatronic = (Animatronic) gameManager.getRoleByPlayer(player);
        createScoreboard();
    }
    @Override
    public void createScoreboard() {
        setDisplayName(SCOREBOARD_DATA.get(0));
        int counter = SCOREBOARD_DATA.size()-1;
        for (int i = 0; i<SCOREBOARD_DATA.size()-1; i++) {
            String line = SCOREBOARD_DATA.get(counter-i)
                    .replace("{battery}",String.format("%.1f", getBattery()))
                    .replace("{time}",getTime())
                    .replace("{ability1}",getHability1())
                    .replace("{ab1_status}",getHability1Status())
                    .replace("{ability2}",getHability2())
                    .replace("{ab2_status}",getHability2Status())
                    .replace("{ultimate}",getUltimate())
                    .replace("{ult_status}",getUltimateStatus());

            setScore(line, i);
        }
    }

    @Override
    public void update() {

    }

    private String getHability1() {
        return animatronic.getAbility1().getName();
    }

    private String getHability2() {
        return animatronic.getAbility2().getName();
    }

    private String getHability1Status() {
        if (animatronic.getAbility1().isReady()) return "§aReady";
        else return "§cNot ready";
    }

    private String getHability2Status() {
        if (animatronic.getAbility2().isReady()) return "§aReady";
        else return "§cNot ready";
    }

    private String getUltimate() {
        return animatronic.getUltimate().getName();
    }

    private String getUltimateStatus() {
        if (animatronic.getUltimate().isReady()) return "§aReady";
        else return "§cNot ready";
    }
    private String getTime() {
        String timeAlias;
        if (gameManager.getHour() == 12) timeAlias = "PM";
        else timeAlias = "AM";

        return gameManager.getHour() + " " + timeAlias;
    }

    private double getBattery() {
        double battery = gameManager.getBattery();
        if (battery == -1) battery = 0;

        return battery;
    }

}
