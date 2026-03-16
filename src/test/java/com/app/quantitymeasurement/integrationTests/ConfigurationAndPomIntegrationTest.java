package com.app.quantitymeasurement.integrationTests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.exception.DatabaseException;
import com.app.quantitymeasurement.util.ApplicationConfig;

public class ConfigurationAndPomIntegrationTest {

    @Test
    void testPackageStructure_AllLayersPresent() {
        String baseDir = "src/main/java/com/app/quantitymeasurement";
        assertTrue(new File(baseDir + "/controller").exists(), "Controller package should exist");
        assertTrue(new File(baseDir + "/service").exists(), "Service package should exist");
        assertTrue(new File(baseDir + "/repository").exists(), "Repository package should exist");
        assertTrue(new File(baseDir + "/entity").exists(), "Entity package should exist");
    }

    @Test
    void testPomDependencies_JDBCDriversIncluded() throws Exception {
        Path pomPath = Paths.get("pom.xml");
        assertTrue(Files.exists(pomPath), "pom.xml should exist");
        
        String pomContent = Files.readString(pomPath);
        assertTrue(pomContent.contains("<artifactId>h2</artifactId>"), "H2 driver should be in pom.xml");
        assertTrue(pomContent.contains("<artifactId>junit-jupiter</artifactId>"), "JUnit should be in pom.xml");
        assertTrue(pomContent.contains("<artifactId>mockito-core</artifactId>"), "Mockito should be in pom.xml");
    }

    @Test
    void testDatabaseConfiguration_LoadedFromProperties() {
        // Validate properties load
        assertNotNull(ApplicationConfig.getDbUrl());
        assertNotNull(ApplicationConfig.getDbUsername());
        assertNotNull(ApplicationConfig.getDbDriver());
        assertEquals("org.h2.Driver", ApplicationConfig.getDbDriver());
    }

    @Test
    void testPropertiesConfiguration_EnvironmentOverride() {
        // Validating the modification we made earlier to check System.getProperty as an override mechanism
        System.setProperty("repository.type", "cache");
        assertEquals("cache", ApplicationConfig.getRepositoryType());
        
        System.setProperty("repository.type", "database");
        assertEquals("database", ApplicationConfig.getRepositoryType());
    }

    @Test
    void testDatabaseException_CustomException() {
        DatabaseException exception = DatabaseException.queryFailed("Test query failure", new RuntimeException("Root cause"));
        assertEquals("Query execution failed: Test query failure", exception.getMessage());
        assertNotNull(exception.getCause());
        assertEquals("Root cause", exception.getCause().getMessage());
    }
}
