package org.de.eloy.fnaf.database.dao;

import org.bukkit.Particle;
import org.de.eloy.fnaf.game.objects.Skin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SkinDAO implements GenericDAO<Skin>{

    private Skin createSkinFromResultSet(ResultSet resultSet) throws SQLException {
        String sk_name = resultSet.getString("sk_name");
        String sk_playerHeadName = resultSet.getString("sk_playerHeadName");
        String sk_chestColor = resultSet.getString("sk_chestColor");
        String sk_leggColor = resultSet.getString("sk_leggColor");
        String sk_bootsColor = resultSet.getString("sk_bootsColor");

        String sk_particleSTRING = resultSet.getString("sk_bootsColor");
        Particle sk_particle = (sk_particleSTRING != null) ? Particle.valueOf(sk_particleSTRING) : null;

        String sk_rarity = resultSet.getString("sk_rarity");

        int sk_animatronic_id = resultSet.getInt("sk_animatronic_id");

        return new Skin(sk_name,sk_playerHeadName,sk_chestColor,sk_leggColor,sk_bootsColor,sk_particle,sk_rarity,sk_animatronic_id);
    }
    @Override
    public Skin getById(int sk_id) {
        Skin skin = null;
        try {
            ResultSet resultSet = executeQuery("SELECT * FROM SKIN WHERE sk_id = " + sk_id);
            if (resultSet.next()) {
                skin = createSkinFromResultSet(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return skin;
    }

    @Override
    public ArrayList<Skin> getAll() {
        ArrayList<Skin> skins = new ArrayList<>();

        try {
            ResultSet resultSet = executeQuery("SELECT * FROM SKIN");

            while (resultSet.next()) {
                Skin skin = createSkinFromResultSet(resultSet);
                skins.add(skin);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return skins;
    }

    @Override
    public void insert(Skin skin) {
        executeCommand("INSERT INTO SKIN (sk_name, sk_playerHeadName, sk_chestColor, sk_leggColor, sk_bootsColor, sk_particle, sk_rarity, sk_animatronic_id) " +
                "VALUES ('"+skin.getName()+"', " +
                "'"+skin.getPlayerHeadName()+"', " +
                "'"+skin.getChestplateColor()+"', " +
                "'"+skin.getLeggingsColor()+"', " +
                "'"+skin.getBootsColor()+"', " +
                "'"+skin.getParticle()+"', " +
                "'"+skin.getRarity()+"', " +
                ""+skin.getAnimatronic_id()+");");
    }

    @Override
    public void delete(int id) {
        executeCommand("DELETE FROM SKIN WHERE sk_id = "+id);
    }
}