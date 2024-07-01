package org.de.eloy.fnaf.files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public class MessagesFile {
    private FileConfiguration config;
    private Plugin plugin;

    public MessagesFile(JavaPlugin plugin) {
        java.io.File configFile = new java.io.File(plugin.getDataFolder(), "messages.yml");
        if (!configFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        this.config = YamlConfiguration.loadConfiguration(configFile);
        this.plugin = plugin;
    }

    public void save() {
        try {
            config.save(new File(plugin.getDataFolder(), "messages.yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getPrefix() {
        return config.getString("prefix");
    }

    public String getMessage(String key) {
        return config.getString(key);
    }

    public List<String> getDesc(String key) {
        return config.getStringList(key);
    }

    public FileConfiguration getConfig() {
        return config;
    }
}