package org.de.eloy.fnaf.database.dao;

import org.bukkit.Location;
import org.bukkit.Material;
import org.de.eloy.fnaf.game.objects.Door;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DoorDAO implements GenericDAO<Door>{

    private Door createDoorFromResultSet(ResultSet resultSet) throws SQLException {
        int door_id = resultSet.getInt("door_id");

        String door_locationSTRING = resultSet.getString("door_location");
        Location door_location = getLocationFromString(door_locationSTRING);

        String door_leverLocationSTRING = resultSet.getString("door_leverLocation");
        Location door_leverLocation = getLocationFromString(door_leverLocationSTRING);

        Material door_material = Material.getMaterial(resultSet.getString("door_material"));

        int door_arena_id = resultSet.getInt("door_arena_id");

        Door door = new Door(door_location,door_leverLocation,door_material,door_arena_id);
        door.setId(door_id);
        return door;
    }
    @Override
    public Door getById(int door_id) {
        Door door = null;
        try {
            ResultSet resultSet = executeQuery("SELECT * FROM DOOR WHERE door_id = " + door_id);
            if (resultSet.next()) {
                door = createDoorFromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return door;
    }

    @Override
    public ArrayList<Door> getAll() {
        ArrayList<Door> doors = new ArrayList<>();

        try {
            ResultSet resultSet = executeQuery("SELECT * FROM DOOR");

            while (resultSet.next()) {
                Door door = createDoorFromResultSet(resultSet);
                doors.add(door);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (doors.size() == 0) return null;

        return doors;
    }

    public ArrayList<Door> getAllByArena(int arena_id) {
        ArrayList<Door> doors = new ArrayList<>();

        try {
            ResultSet resultSet = executeQuery("SELECT * FROM DOOR WHERE door_arena_id = "+arena_id);

            while (resultSet.next()) {
                Door door = createDoorFromResultSet(resultSet);
                doors.add(door);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (doors.size() == 0) return null;

        return doors;
    }

    @Override
    public void insert(Door door) {

        String door_location = getStringFromLocation(door.getLocation());
        String door_leverLocation = getStringFromLocation(door.getLeverLocation());
        String door_material = door.getDoorMaterial().toString();
        int door_arena_id = door.getArenaIndex();

        executeCommand("INSERT INTO DOOR (door_location, door_leverLocation, door_material, door_arena_id) " +
                "VALUES ('"+door_location+"', " +
                "'"+door_leverLocation+"', " +
                "'"+door_material+"', " +
                ""+door_arena_id+");");
    }

    @Override
    public void delete(int id) {
        executeCommand("DELETE FROM DOOR WHERE door_id = "+id);
    }
}
