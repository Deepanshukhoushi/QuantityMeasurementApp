/**
 * ApplicationConfig.java
 *
 * This utility class is responsible for loading the 'application.properties' file
 * and providing a centralized mechanism for accessing application-wide configuration settings.
 * It abstracts out the reading of environment-specific properties such as repository types,
 * database URLs, and credentials. It allows system property overrides via -D arguments
 * during JVM execution, adapting the application behavior dynamically at runtime.
 *
 * @author Developer
 * @version 16.0
 * @since 16.0
 */
package com.app.quantitymeasurement.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class ApplicationConfig {

    private static final Logger logger =
            Logger.getLogger(ApplicationConfig.class.getName());

    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    public static void loadProperties() {

        try (InputStream input = ApplicationConfig.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new RuntimeException("Unable to find application.properties");
            }

            properties.load(input);

            logger.info("Application properties loaded successfully");

        } catch (IOException e) {
            logger.severe("Error loading application.properties: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key) {
        String sysProp = System.getProperty(key);
        return sysProp != null ? sysProp : properties.getProperty(key);
    }

    public static String getRepositoryType() {
        return getProperty("repository.type");
    }

    public static String getEnvironment() {
        return getProperty("app.env");
    }

    public static String getDbUrl() {
        return getProperty("db.url");
    }

    public static String getDbUsername() {
        return getProperty("db.username");
    }

    public static String getDbPassword() {
        return getProperty("db.password");
    }

    public static String getDbDriver() {
        return getProperty("db.driver");
    }
}