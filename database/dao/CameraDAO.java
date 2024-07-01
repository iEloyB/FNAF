package org.de.eloy.fnaf.database.dao;

import org.bukkit.Location;
import org.de.eloy.fnaf.game.objects.Camera;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CameraDAO implements GenericDAO<Camera>{

    private Camera createCameraFromResultSet(ResultSet resultSet) throws SQLException {
        int cam_id = resultSet.getInt("cam_id");
        String cam_locationSTRING = resultSet.getString("cam_spawn");
        Location cam_location = getLocationFromString(cam_locationSTRING);
        int cam_arena_id = resultSet.getInt("cam_arena_id");

        Camera camera = new Camera(cam_location,cam_arena_id);
        camera.setId(cam_id);
        return camera;
    }
    @Override
    public Camera getById(int cam_id) {
        Camera camera = null;
        try {
            ResultSet resultSet = executeQuery("SELECT * FROM CAMERA WHERE cam_id = " + cam_id);
            if (resultSet.next()) {
                camera = createCameraFromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return camera;
    }

    @Override
    public ArrayList<Camera> getAll() {
        ArrayList<Camera> cameras = new ArrayList<>();

        try {
            ResultSet resultSet = executeQuery("SELECT * FROM CAMERA");

            while (resultSet.next()) {
                Camera camera = createCameraFromResultSet(resultSet);
                cameras.add(camera);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (cameras.size() == 0) return null;

        return cameras;
    }

    public ArrayList<Camera> getAllByArena(int arena_id) {
        ArrayList<Camera> cameras = new ArrayList<>();

        try {
            ResultSet resultSet = executeQuery("SELECT * FROM CAMERA WHERE cam_arena_id = "+arena_id);

            while (resultSet.next()) {
                Camera camera = createCameraFromResultSet(resultSet);
                cameras.add(camera);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (cameras.size() == 0) return null;

        return cameras;
    }

    @Override
    public void insert(Camera camera) {
        String cam_spawn = getStringFromLocation(camera.getLocation());
        int cam_arena_id = camera.getArenaIndex();
        executeCommand("INSERT INTO CAMERA (cam_spawn, cam_arena_id) VALUES ('"+cam_spawn+"', "+cam_arena_id+")");
    }

    @Override
    public void delete(int id) {
        executeCommand("DELETE FROM CAMERA WHERE cam_id = "+id);
    }
}
