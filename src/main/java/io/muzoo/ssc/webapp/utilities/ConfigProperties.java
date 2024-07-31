package io.muzoo.ssc.webapp.utilities;


public class ConfigProperties {
    private String databaseConnectionUrl;
    private String databaseUsername;
    private String databaseDriverClass;
    private String databasePassword;

    public String getDatabaseDriverClass() {
        return databaseDriverClass;
    }

    public void setDatabaseDriverClass(String databaseDriverClass) {
        this.databaseDriverClass = databaseDriverClass;
    }

    public String getDatabaseConnectionUrl() {
        return databaseConnectionUrl;
    }

    public void setDatabaseConnectionUrl(String databaseConnectionUrl) {
        this.databaseConnectionUrl = databaseConnectionUrl;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public void setDatabaseUsername(String databaseUsername) {
        this.databaseUsername = databaseUsername;
    }

}
