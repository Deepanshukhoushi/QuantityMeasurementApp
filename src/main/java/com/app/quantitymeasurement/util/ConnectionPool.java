/**
 * ConnectionPool.java
 *
 * This utility class is responsible for initializing and managing the HikariCP database
 * connection pool. It establishes a pool of reusable database connections based on properties
 * defined in the application configuration, significantly improving performance by avoiding
 * the overhead of establishing new connections for every database query. It provides thread-safe
 * methods to acquire active connections and properly shutdown the pool when the application stops.
 *
 * @author Developer
 * @version 16.0
 * @since 16.0
 */
package com.app.quantitymeasurement.util;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionPool {

    private static final Logger logger =
            Logger.getLogger(ConnectionPool.class.getName());

    private static HikariDataSource dataSource;

    static {
        initializeConnectionPool();
    }

    public static void initializeConnectionPool() {

        try {
            HikariConfig config = new HikariConfig();

            String jdbcUrl = ApplicationConfig.getDbUrl();
            logger.info("JDBC URL from application.properties: " + jdbcUrl);
            config.setJdbcUrl(jdbcUrl);
            
            config.setUsername(ApplicationConfig.getDbUsername());
            config.setPassword(ApplicationConfig.getDbPassword());
            config.setDriverClassName(ApplicationConfig.getDbDriver());

            config.setMaximumPoolSize(
                    Integer.parseInt(ApplicationConfig.getProperty("db.hikari.maximum-pool-size")));

            config.setMinimumIdle(
                    Integer.parseInt(ApplicationConfig.getProperty("db.hikari.minimum-idle")));

            config.setConnectionTimeout(
                    Long.parseLong(ApplicationConfig.getProperty("db.hikari.connection-timeout")));

            config.setIdleTimeout(
                    Long.parseLong(ApplicationConfig.getProperty("db.hikari.idle-timeout")));

            config.setMaxLifetime(
                    Long.parseLong(ApplicationConfig.getProperty("db.hikari.max-lifetime")));

            config.setPoolName(ApplicationConfig.getProperty("db.hikari.pool-name"));

            config.setConnectionTestQuery(
                    ApplicationConfig.getProperty("db.hikari.connection-test-query"));

            dataSource = new HikariDataSource(config);

            logger.info("Database connection pool initialized successfully.");

        } catch (Exception e) {
            logger.severe("Failed to initialize connection pool: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void closePool() {
        if (dataSource != null) {
            dataSource.close();
            logger.info("Connection pool closed.");
        }
    }
}