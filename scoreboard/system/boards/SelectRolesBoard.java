package org.de.eloy.fnaf.scoreboard.system.boards;

import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.game.manager.GameManager;
import org.de.eloy.fnaf.game.objects.Animatronic;
import org.de.eloy.fnaf.game.objects.Guard;
import org.de.eloy.fnaf.game.tasks.CountdownTask;
import org.de.eloy.fnaf.scoreboard.system.ScoreBoardBuilder;

import java.util.List;

public class SelectRolesBoard extends ScoreBoardBuilder {

    private final List<String> SCOREBOARD_DATA = FNAF.getMessages().getSELECTROLES_SCOREBOARD();
    private final GameManager gameManager;
    private int timeLeft;
    public SelectRolesBoard(Player player, GameManager gameManager) {
        super(player);
        this.gameManager = gameManager;

        timeLeft = getTimeLeft();
        createScoreboard();
    }

    @Override
    public void createScoreboard() {
        setDisplayName(SCOREBOARD_DATA.get(0));

        int counter = SCOREBOARD_DATA.size()-1;
        for (int i = 0; i<SCOREBOARD_DATA.size()-1; i++) {
            String line = SCOREBOARD_DATA.get(counter-i)
                    .replace("{role}",getRole())
                    .replace("{time}", String.valueOf(timeLeft));

            setScore(line, i);
        }
    }

    @Override
    public void update() {
        timeLeft = getTimeLeft()-1;
    }

    private String getRole() {
        Object role = gameManager.getRoleByPlayer(getPlayer());

        if (role == null) return "";
        if (role instanceof Guard) return Animatronic.getAnimatronicTitle("guard");
        return Animatronic.getAnimatronicTitle(((Animatronic) role).getName());
    }

    private int getTimeLeft() {
        if (gameManager.getCountdownTimeLeft() == -1) return FNAF.getFiles().getConfigFile().getInt("starting_game_Time");
        else return gameManager.getCountdownTimeLeft();
    }
}
