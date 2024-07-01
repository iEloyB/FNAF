package org.de.eloy.fnaf.database.dao;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.de.eloy.fnaf.database.DatabaseConfig;
import org.de.eloy.fnaf.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public interface GenericDAO<T> {
    Properties PROPERTIES = new DatabaseConfig().getProperties();
    Connection connection = new DatabaseConnection(PROPERTIES).getConnection();
    T getById(int id);
    ArrayList<T> getAll();
    void insert(T entity);
    void delete(int id);

    default String getStringFromLocation(Location location) {
        if (location == null) return null;
        if (location.getWorld() == null) return null;

        String world = location.getWorld().getUID().toString();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();

        return (world+";"+x+";"+y+";"+z+";"+yaw+";"+pitch);
    }

    default Location getLocationFromString(String locationString) {
        if (locationString == null) return null;

        String[] parts = locationString.split(";");
        if (parts.length != 6) return null;

        String worldUUID = parts[0];
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);
        float yaw = Float.parseFloat(parts[4]);
        float pitch = Float.parseFloat(parts[5]);

        return new Location(Bukkit.getWorld(java.util.UUID.fromString(worldUUID)), x, y, z, yaw, pitch);
    }

    default void executeCommand(String command) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(command);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    default ResultSet executeQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}