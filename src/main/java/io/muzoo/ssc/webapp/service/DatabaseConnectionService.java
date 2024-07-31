package io.muzoo.ssc.webapp.service;

import com.zaxxer.hikari.HikariDataSource;
import io.muzoo.ssc.webapp.utilities.ConfigLoader;
import io.muzoo.ssc.webapp.utilities.ConfigProperties;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionService {

    private final HikariDataSource ds;
    private static DatabaseConnectionService service;

    private DatabaseConnectionService() {
        ds = new HikariDataSource();
        ds.setMaximumPoolSize(20);
        ConfigProperties cp = ConfigLoader.load();
        if (cp == null){
            throw new RuntimeException("cannot access config.properties");
        }
        ds.setDriverClassName("org.mariadb.jdbc.Driver");
        ds.setJdbcUrl(cp.getDatabaseConnectionUrl());
        ds.addDataSourceProperty("user", cp.getDatabaseUsername());
        ds.addDataSourceProperty("password", cp.getDatabasePassword()) ;
        ds.setAutoCommit(false);
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static DatabaseConnectionService getInstance() {
        if (service == null) {
            service = new DatabaseConnectionService();
        }
        return service;
    }
}

