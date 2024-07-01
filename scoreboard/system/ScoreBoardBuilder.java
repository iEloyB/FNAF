package org.de.eloy.fnaf.scoreboard.system;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public abstract class ScoreBoardBuilder {

    private final Scoreboard scoreboard;
    private final Objective objective;
    private String displayName = "no display name";
    private final Player player;

    public ScoreBoardBuilder(Player player) {
        this.player = player;

        if (player.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard())) {
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
        this.scoreboard = player.getScoreboard();
        if (this.scoreboard.getObjective("player_view") != null) this.scoreboard.getObjective("player_view").unregister();
        this.objective = this.scoreboard.registerNewObjective("player_view", Criteria.DUMMY, this.displayName);
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public abstract void createScoreboard();

    public abstract void update();

    public void setScore(String data, int score) {
        this.objective.getScore(data).setScore(score);
    }

    public void removeScore(String data) {
        this.scoreboard.resetScores(data);
    }

    public Player getPlayer() {
        return player;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        this.objective.setDisplayName(displayName);
    }

}
