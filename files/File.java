package org.de.eloy.fnaf.files;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class File {
    public MessagesFile getMessagesFile() {
        return messagesFile;
    }

    public ConfigFile getConfigFile() {
        return configFile;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    private MessagesFile messagesFile;
    private ConfigFile configFile;
    private Plugin plugin;
    public File(Plugin plugin) {
        this.plugin = plugin;
        loadAll();
    }

    private void loadAll() {
        messagesFile = new MessagesFile((JavaPlugin) plugin);
        configFile = new ConfigFile((JavaPlugin) plugin);
    }

    private void saveAll() {
        messagesFile.save();
        configFile.save();
    }
}
