package org.de.eloy.fnaf.database.dao;

import org.bukkit.Location;
import org.de.eloy.fnaf.game.objects.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArenaDAO implements GenericDAO<Arena> {

    private Arena createArenaFromResultSet(ResultSet resultSet) throws SQLException {
        int ar_id = resultSet.getInt("ar_id");

        String ar_name = resultSet.getString("ar_name");

        String ar_server_name = resultSet.getString("ar_server_port");

        String ar_waitingLobbySpawnString = resultSet.getString("ar_waitingLobbySpawn");
        Location ar_waitingLobbySpawn = getLocationFromString(ar_waitingLobbySpawnString);

        String ar_PostGameLocationString = resultSet.getString("ar_postGameLocation");
        Location ar_postGameLocation = getLocationFromString(ar_PostGameLocationString);

        ArrayList<Light> lights = new LightDAO().getAllByArena(ar_id);
        ArrayList<Door> doors = new DoorDAO().getAllByArena(ar_id);
        ArrayList<Animatronic> animatronics = new AnimatronicDAO().getAllByArena(ar_id);
        ArrayList<Camera> cameras = new CameraDAO().getAllByArena(ar_id);
        Guard guard = new GuardDAO().getById(ar_id);


        Arena arena = new Arena(ar_name, ar_waitingLobbySpawn, ar_postGameLocation, ar_server_name);
        arena.setId(ar_id);
        arena.setLights(lights);
        arena.setDoors(doors);
        arena.setAnimatronics(animatronics);
        arena.setCameras(cameras);
        arena.setGuard(guard);

        return arena;
    }

    @Override
    public Arena getById(int ar_id) {
        Arena arena = null;
        try {
            ResultSet resultSet = executeQuery("SELECT * FROM ARENA WHERE ar_id = " + ar_id);
            if (resultSet.next()) {
                arena = createArenaFromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arena;
    }

    public void update(int arenaIndex, Arena arena) {
        Location waitingLobbySpawn = arena.getWaitingLobbySpawn();
        Location postGameLocation = arena.getPostGameSpawn();
        String ar_server_name = arena.getServerPort();

        String query = "UPDATE ARENA SET "
                + "ar_name = '" + arena.getName() + "', "
                + "ar_waitingLobbySpawn = '" + getStringFromLocation(waitingLobbySpawn) + "', "
                + "ar_postGameLocation = '" + getStringFromLocation(postGameLocation) + "', "
                + "ar_server_port = " + (ar_server_name != null ? "'" + ar_server_name + "'" : "NULL")
                + " WHERE ar_id = " + arenaIndex;

        executeCommand(query);
    }


    @Override
    public ArrayList<Arena> getAll() {
        ArrayList<Arena> arenas = new ArrayList<>();

        try {
            ResultSet resultSet = executeQuery("SELECT * FROM ARENA");

            while (resultSet.next()) {
                Arena arena = createArenaFromResultSet(resultSet);
                arenas.add(arena);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arenas;
    }

    @Override
    public void insert(Arena arena) {
        executeCommand("INSERT INTO ARENA (ar_name, ar_server_port) VALUES ('" + arena.getName() + "','"+ arena.getServerPort()+"')");
    }

    @Override
    public void delete(int id) {
        executeCommand("DELETE FROM CAMERA WHERE cam_arena_id = " + id);
        executeCommand("DELETE FROM DOOR WHERE door_arena_id = " + id);
        executeCommand("DELETE FROM SKIN WHERE sk_animatronic_id IN (SELECT an_id FROM ANIMATRONIC WHERE an_arena_id = " + id + ")");
        executeCommand("DELETE FROM ANIMATRONIC WHERE an_arena_id = " + id);
        executeCommand("DELETE FROM GUARD WHERE gu_arena_id = " + id);
        executeCommand("DELETE FROM LIGHT WHERE li_arena_id = " + id);
        executeCommand("DELETE FROM ARENA WHERE ar_id = " + id);
    }

}
