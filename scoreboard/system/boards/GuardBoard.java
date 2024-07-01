package org.de.eloy.fnaf.scoreboard.system.boards;

import org.bukkit.entity.Player;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.game.manager.GameManager;
import org.de.eloy.fnaf.scoreboard.system.ScoreBoardBuilder;

import java.util.List;

public class GuardBoard extends ScoreBoardBuilder {

    private final List<String> SCOREBOARD_DATA = FNAF.getMessages().getGUARD_SCOREBOARD();
    private final GameManager gameManager;

    public GuardBoard(Player player, GameManager gameManager) {
        super(player);
        this.gameManager = gameManager;
        createScoreboard();
    }
    @Override
    public void createScoreboard() {
        setDisplayName(SCOREBOARD_DATA.get(0));
        int counter = SCOREBOARD_DATA.size()-1;
        for (int i = 0; i<SCOREBOARD_DATA.size()-1; i++) {
            String line = SCOREBOARD_DATA.get(counter-i)
                    .replace("{battery}",String.format("%.1f ", getBattery()))
                    .replace("{time}",getTime())
                    .replace("{usage}",getUsageNumber())
                    .replace("{usageIcons}",getUsageIcons());

            setScore(line, i);
        }
    }

    @Override
    public void update() {

    }

    private String getUsageIcons() {
        String usageIcons;
        switch (gameManager.getUsage()) {
            case 1:
                usageIcons = "§f§l▃§r";
                break;
            case 2:
                usageIcons = "§f§l▃§e§l▅§r";
                break;
            case 3:
                usageIcons = "§f§l▃§e§l▅§c§l▇§r";
                break;
            default:
                usageIcons = "§f§l▃§e§l▅§c§l▇§4§l▇§r";
        }
        return usageIcons;
    }

    private String getUsageNumber() {
        return gameManager.getUsage() + "x";
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
