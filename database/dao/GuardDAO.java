package org.de.eloy.fnaf.database.dao;

import org.bukkit.Location;
import org.de.eloy.fnaf.game.objects.Guard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GuardDAO implements GenericDAO<Guard> {

    private Guard createGuardFromResultSet(ResultSet resultSet) throws SQLException {
        int gu_id = resultSet.getInt("gu_id");

        String guard_locationSTRING = resultSet.getString("gu_spawn");
        Location guard_location = getLocationFromString(guard_locationSTRING);

        int gu_hp = resultSet.getInt("gu_hp");

        int gu_arena_id = resultSet.getInt("gu_arena_id");

        Guard guard = new Guard(guard_location, gu_arena_id);

        guard.setHp(gu_hp);
        guard.setId(gu_id);

        return guard;
    }

    @Override
    public Guard getById(int ar_id) {
        Guard guard = null;
        try {
            ResultSet resultSet = executeQuery("SELECT * FROM GUARD WHERE gu_arena_id = " + ar_id);
            if (resultSet.next()) {
                guard = createGuardFromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return guard;
    }

    @Override
    public ArrayList<Guard> getAll() {
        ArrayList<Guard> guards = new ArrayList<>();

        try {
            ResultSet resultSet = executeQuery("SELECT * FROM GUARD");

            while (resultSet.next()) {
                Guard guard = createGuardFromResultSet(resultSet);
                guards.add(guard);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return guards;
    }

    @Override
    public void insert(Guard guard) {
        executeCommand("INSERT INTO GUARD (gu_spawn, gu_hp, gu_arena_id) VALUES ('" + getStringFromLocation(guard.getSpawn()) + "', " + guard.getHp() + ", '" + guard.getArenaIndex() + "')");
    }

    public void edit(int guard_id, Guard guard) {
        executeCommand("UPDATE GUARD SET gu_spawn = '" + getStringFromLocation(guard.getSpawn()) + "', gu_hp = " + guard.getHp() + ", gu_arena_id = '" + guard.getArenaIndex() + "' WHERE gu_id = " + guard_id);
    }


    @Override
    public void delete(int id) {
        executeCommand("DELETE FROM GUARD WHERE gu_id = " + id);
    }
}