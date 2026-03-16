/**
 * DatabaseConfig.java
 *
 * This utility class is responsible for initializing the database schema at application startup.
 * It reads the required SQL schema definitions (e.g., table creation and indexes) from the
 * 'schema.sql' resource file and executes them using a connection from the ConnectionPool.
 * This guarantees that the necessary database table structures exist before the application
 * fully loads and accepts any incoming measurement or history tracking operations.
 *
 * @author Developer
 * @version 16.0
 * @since 16.0
 */
package com.app.quantitymeasurement.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Logger;

public class DatabaseConfig {

    private static final Logger logger =
            Logger.getLogger(DatabaseConfig.class.getName());

    public static void initializeDatabase() {

        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {

            InputStream inputStream =
                    DatabaseConfig.class.getClassLoader()
                            .getResourceAsStream("db/schema.sql");

            try (Scanner scanner = new Scanner(inputStream).useDelimiter(";")) {
                while (scanner.hasNext()) {
                    String sql = scanner.next().trim();
                    if (!sql.isEmpty()) {
                        statement.execute(sql);
                    }
                }
            }

            logger.info("Database schema initialized successfully");

        } catch (Exception e) {
            logger.severe("Database initialization failed: " + e.getMessage());
        }
    }
}