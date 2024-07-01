package org.de.eloy.fnaf.database;

import org.de.eloy.fnaf.FNAF;

import java.util.Properties;

public class DatabaseConfig {
    private static final String[] CONFIG = FNAF.getFiles().getConfigFile().getMysqlConfig();
    private Properties properties;
    public DatabaseConfig() {
        properties = new Properties();

        String dbUrl = CONFIG[0];
        String dbUser = CONFIG[1];
        String dbPass = CONFIG[2];

        properties.setProperty("db.url", dbUrl);
        properties.setProperty("db.user", dbUser);
        properties.setProperty("db.password", dbPass);
    }

    public Properties getProperties() {
        return properties;
    }
}