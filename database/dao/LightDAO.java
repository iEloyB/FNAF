package org.de.eloy.fnaf.database.dao;

import org.bukkit.Location;
import org.de.eloy.fnaf.game.objects.Light;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LightDAO implements GenericDAO<Light>{

    private Light createLightFromResultSet(ResultSet resultSet) throws SQLException {
        int light_id = resultSet.getInt("li_id");
        String light_locationSTRING = resultSet.getString("li_location");
        Location light_location = getLocationFromString(light_locationSTRING);

        int li_arena_id = resultSet.getInt("li_arena_id");

        Light light = new Light(light_location,li_arena_id);
        light.setId(light_id);

        return light;
    }

    @Override
    public Light getById(int li_id) {
        Light light = null;
        try {
            ResultSet resultSet = executeQuery("SELECT * FROM LIGHT WHERE li_id = " + li_id);
            if (resultSet.next()) {
                light = createLightFromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return light;
    }

    @Override
    public ArrayList<Light> getAll() {
        ArrayList<Light> lights = new ArrayList<>();

        try {
            ResultSet resultSet = executeQuery("SELECT * FROM LIGHT");

            while (resultSet.next()) {
                Light light = createLightFromResultSet(resultSet);

                lights.add(light);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (lights.size() == 0) return null;

        return lights;
    }

    public ArrayList<Light> getAllByArena(int arena_id) {
        ArrayList<Light> lights = new ArrayList<>();

        try {
            ResultSet resultSet = executeQuery("SELECT * FROM LIGHT WHERE li_arena_id = "+arena_id);

            while (resultSet.next()) {
                Light light = createLightFromResultSet(resultSet);

                lights.add(light);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (lights.size() == 0) return null;

        return lights;
    }

    @Override
    public void insert(Light light) {
        executeCommand("INSERT INTO LIGHT (li_location, li_arena_id) VALUES ('"+getStringFromLocation(light.getLocation())+"', "+light.getArenaIndex()+")");
    }

    @Override
    public void delete(int id) {
        executeCommand("DELETE FROM LIGHT WHERE li_id = "+id);
    }
}