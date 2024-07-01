package org.de.eloy.fnaf.game.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.de.eloy.fnaf.FNAF;
import org.de.eloy.fnaf.game.objects.*;
import org.de.eloy.fnaf.game.tasks.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameManager {
    private final FNAF plugin;
    public GameState gameState = GameState.WAITING;
    private CountdownTask countdownTask;
    private HoursTask hoursTask;
    private final HashMap<Player, Object> playerRoles = new HashMap<>();
    private final int GAME_DURATION = FNAF.getFiles().getConfigFile().getInt("game_duration");
    private final Arena arena;
    private double battery = 100;
    private int usage = 0;
    private int hour = -1;
    private int closedDoors = 0;
    private boolean isWatchingCamera = false;
    private Camera lastCamera;
    private int guardHP;
    private PreparePlayersTask preparePlayersTask;
    private ShowInfoTask showInfoTask;
    private final CamerasTask camerasTask;
    private int countdownTimeLeft = -1;

    public GameManager(FNAF plugin, Arena arena) {
        this.plugin = plugin;
        this.arena = arena;

        this.lastCamera = arena.getCameras().get(0);
        this.camerasTask = new CamerasTask(this);
        this.showInfoTask = new ShowInfoTask(this);
    }

    public void setGameState(GameState gameState) {
        if (this.gameState == gameState) return;
        if (this.gameState == GameState.INGAME && gameState == GameState.STARTING) return;
        if (this.gameState == GameState.ENDING && gameState == GameState.STARTING) return;
        if (this.gameState == GameState.INGAME && gameState == GameState.WAITING) return;
        if (this.gameState == GameState.ENDING && gameState == GameState.WAITING) return;

        this.gameState = gameState;

        switch (gameState) {
            case WAITING:
                resetCountdowns();
                break;

            case STARTING:
                resetCountdowns();

                countdownTask = new CountdownTask(this);
                countdownTask.runTaskTimer(getPlugin(), 0, 20);
                break;

            case PREGAME:
                preparePlayersTask = new PreparePlayersTask(this);
                preparePlayersTask.removeItems();

                PrepareObjectsTask prepareObjectsTask = new PrepareObjectsTask(this);
                prepareObjectsTask.prepare();
                preparePlayersTask.setGuardHp();

                StartCinematicTask startCinematicTask = new StartCinematicTask(this);
                startCinematicTask.runTaskTimer(getPlugin(),0,2);
                break;

            case INGAME:
                resetCountdowns();
                TeleportPlayersTask teleportPlayersTask = new TeleportPlayersTask(this);
                teleportPlayersTask.teleportAll();

                preparePlayersTask = new PreparePlayersTask(this);
                preparePlayersTask.giveItems();

                hoursTask = new HoursTask(this);
                hoursTask.runTaskTimer(plugin, 0, 20);

                // TODO Setup people skins
                break;

            case ENDING:
                resetCountdowns();

                if (getGuardHP() <= 2 || hour != 6) Bukkit.broadcastMessage("Animatronics won");
                else Bukkit.broadcastMessage("Guard won");

                preparePlayersTask = new PreparePlayersTask(this);
                preparePlayersTask.removeItems();

                EndingCelebrationTask endingCelebrationTask = new EndingCelebrationTask(this);
                endingCelebrationTask.runTaskTimer(getPlugin(),0,20);
                // TODO who won title
                break;

            case RESETING:
                getPlugin().getServer().shutdown();
                // TODO upload the game in the database
                break;
        }
    }

    public void updateDoorCount() {
        int closedDoors = 0;
        for (Door door : getArena().getDoors()) {
            if (door.isClosed()) closedDoors ++;
        }
        this.closedDoors = closedDoors;
    }
    public void updateUsage() {
        int usage = closedDoors + 1;
        if (isGuardOnCameras()) usage ++;
        setUsage(usage);
    }
    public void updateBattery() {
        setBattery(getBattery() - getUsage() * (75d / (double) GAME_DURATION));

        if (getBattery() <= 0) {
            PrepareObjectsTask closeDoors = new PrepareObjectsTask(this);
            closeDoors.turnAllOff();

            camerasTask.leaveCameras();

            setBattery(-1);
            setUsage(1);
        }
    }

    public void updateScoreboards() {
        showInfoTask.showScoreboardInfo();
    }

    private void resetCountdowns() {
        if (this.countdownTask != null) this.countdownTask.cancel();
        if (this.hoursTask != null) this.hoursTask.cancel();
    }

    public void addPlayer(Player player) {
        playerRoles.put(player, null);
    }

    public void removePlayer(Player player) {
        playerRoles.remove(player);
    }

    public boolean assignRole(Player player, Object role) {
        if (!(role instanceof Animatronic || role instanceof Guard)) {
            return false;
        }

        for (Object existingRole : playerRoles.values()) {
            if (existingRole != null && existingRole.equals(role)) {
                return false;
            }
        }

        playerRoles.put(player, role);
        return true;
    }

    public boolean roleAlreadyPicked(Object role) {
        for (Object existingRole : playerRoles.values()) {
            if (existingRole != null && existingRole.equals(role)) {
                return true;
            }
        }
        return false;
    }
    public Object getRoleByPlayer(Player player) {
        return playerRoles.get(player);
    }
    public ArrayList<Player> getPlayers() {
        return new ArrayList<>(playerRoles.keySet());
    }
    public HashMap<Player, Object> getPlayerRoles() {
        return playerRoles;
    }

    public Arena getArena() {
        return arena;
    }

    public FNAF getPlugin() {
        return plugin;
    }

    public double getBattery() {
        return battery;
    }

    public void setBattery(double battery) {
        this.battery = battery;
    }

    public int getUsage() {
        return usage;
    }

    public void setUsage(Integer usage) {
        this.usage = usage;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public boolean isGuardOnCameras() {
        return isWatchingCamera;
    }

    public int getGameDuration() {
        return GAME_DURATION;
    }

    public ArrayList<Player> getAnimatronics() {
        ArrayList<Player> animatronicsList = new ArrayList<>();
        for (Map.Entry<Player, Object> entry : getPlayerRoles().entrySet()) {
            Player player = entry.getKey();
            Object role = entry.getValue();

            if (role instanceof Animatronic) animatronicsList.add(player);
        }

        if (animatronicsList.size() == 0) animatronicsList = null;

        return animatronicsList;
    }

    public Player getGuard() {
        Player guardPlayer = null;
        for (Map.Entry<Player, Object> entry : getPlayerRoles().entrySet()) {
            Player player = entry.getKey();
            Object role = entry.getValue();

            if (role instanceof Guard) guardPlayer = player;
        }

        return guardPlayer;
    }
    public int getGuardHP() {
        return guardHP;
    }

    public void setGuardHP(int guardHP) {
        this.guardHP = guardHP;
    }
    public Camera getLastCamera() {
        return lastCamera;
    }

    public void setLastCamera(Camera lastCamera) {
        this.lastCamera = lastCamera;
    }
    public void setWatchingCamera(boolean watchingCamera) {
        isWatchingCamera = watchingCamera;
    }
    public CamerasTask getCamerasTask() {
        return camerasTask;
    }
    public ShowInfoTask getShowInfoTask() {
        return showInfoTask;
    }
    public int getCountdownTimeLeft() {
        return countdownTimeLeft;
    }
    public void setCountdownTimeLeft(int countdownTimeLeft) {
        this.countdownTimeLeft = countdownTimeLeft;
    }
}
