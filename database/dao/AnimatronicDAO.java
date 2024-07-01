package org.de.eloy.fnaf.database.dao;

import org.bukkit.Location;
import org.de.eloy.fnaf.game.objects.Animatronic;
import org.de.eloy.fnaf.game.objects.animatronics.fnaf1.Bonnie;
import org.de.eloy.fnaf.game.objects.animatronics.fnaf1.Chica;
import org.de.eloy.fnaf.game.objects.animatronics.fnaf1.Foxy;
import org.de.eloy.fnaf.game.objects.animatronics.fnaf1.Freddy;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AnimatronicDAO implements GenericDAO<Animatronic> {

    private Animatronic createAnimatronicFromResultSet(ResultSet resultSet) throws SQLException {
        int an_id = resultSet.getInt("an_id");

        String an_name = resultSet.getString("an_name");

        String an_spawnString = resultSet.getString("an_spawn");
        Location an_spawn = getLocationFromString(an_spawnString);

        int an_arena_id = resultSet.getInt("an_arena_id");

        Animatronic animatronic = null;
        switch (an_name.toLowerCase()) {
            case "bonnie":
                animatronic = new Bonnie(an_spawn);
                animatronic.setId(an_id);
                break;

            case "chica":
                animatronic = new Chica(an_spawn);
                animatronic.setId(an_id);
                break;

            case "foxy":
                animatronic = new Foxy(an_spawn);
                animatronic.setId(an_id);
                break;

            case "freddy":
                animatronic = new Freddy(an_spawn);
                animatronic.setId(an_id);
                break;
        }

        if (animatronic != null) animatronic.setArenaIndex(an_arena_id);

        return animatronic;
    }

    @Override
    public Animatronic getById(int an_id) {
        Animatronic animatronic = null;
        try {
            ResultSet resultSet = executeQuery("SELECT * FROM ANIMATRONIC WHERE an_id = " + an_id);
            if (resultSet.next()) {
                animatronic = createAnimatronicFromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return animatronic;
    }

    @Override
    public ArrayList<Animatronic> getAll() {
        ArrayList<Animatronic> animatronics = new ArrayList<>();

        try {
            ResultSet resultSet = executeQuery("SELECT * FROM ANIMATRONIC");

            while (resultSet.next()) {
                Animatronic animatronic = createAnimatronicFromResultSet(resultSet);
                animatronics.add(animatronic);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (animatronics.size() == 0) return null;

        return animatronics;
    }

    public ArrayList<Animatronic> getAllByArena(int arena_id) {
        ArrayList<Animatronic> animatronics = new ArrayList<>();

        try {
            ResultSet resultSet = executeQuery("SELECT * FROM ANIMATRONIC WHERE an_arena_id = "+arena_id);

            while (resultSet.next()) {
                Animatronic camera = createAnimatronicFromResultSet(resultSet);
                animatronics.add(camera);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (animatronics.size() == 0) return null;

        return animatronics;
    }

    @Override
    public void insert(Animatronic animatronic) {
        String an_name = animatronic.getName();
        String an_spawn = getStringFromLocation(animatronic.getSpawn());
        int an_arena_id = animatronic.getArenaIndex();
        executeCommand("INSERT INTO ANIMATRONIC (an_name, an_spawn, an_arena_id) VALUES ('" + an_name + "', '" + an_spawn + "', " + an_arena_id + ")");
    }

    @Override
    public void delete(int id) {
        executeCommand("DELETE FROM ANIMATRONIC WHERE an_id = "+id);
    }
}
