package org.de.eloy.fnaf.files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ConfigFile {
    private FileConfiguration config;
    private Plugin plugin;

    public ConfigFile(JavaPlugin plugin) {
        java.io.File configFile = new java.io.File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        this.config = YamlConfiguration.loadConfiguration(configFile);
        this.plugin = plugin;
    }
    public void save() {
        try {
            config.save(new File(plugin.getDataFolder(), "config.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[] getMysqlConfig() {
        String[] configurations = new String[3];
        configurations[0] = config.getString("url");
        configurations[1] = config.getString("username");
        configurations[2] = config.getString("password");

        return configurations;
    }

    public String getAnimatronicSkin(String animatronic) {
        animatronic = animatronic.toLowerCase();

        return config.getString(animatronic+"_default_skin");
    }

    public String getMessage(String key) {
        return config.getString(key);
    }

    public int getInt(String key) {
        return config.getInt(key);
    }
}