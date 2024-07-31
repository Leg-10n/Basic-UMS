package io.muzoo.ssc.webapp.utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigLoader {
    public static ConfigProperties load() {
        String configFilename = "config.properties";
        try (FileInputStream file = new FileInputStream(configFilename)) {
            Properties prop = new Properties();
            prop.load(file);
            // get the property value and print it out
            String driverClass = prop.getProperty("database.driverClassName");
            String connectionURL = prop.getProperty("database.connectionUrl");
            String username = prop.getProperty("database.username");
            String password = prop.getProperty("database.password");

            ConfigProperties configProperties = new ConfigProperties();
            configProperties.setDatabaseDriverClass(driverClass);
            configProperties.setDatabaseConnectionUrl(connectionURL);
            configProperties.setDatabaseUsername(username);
            configProperties.setDatabasePassword(password);

            return configProperties;
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return null;
    }
}
