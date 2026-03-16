/**
 * QuantityMeasurementApp.java
 *
 * Main entry point of the Quantity Measurement Application.
 * Responsible for bootstrapping configuration, database initialization,
 * repository selection, starting the controller, and cleaning up resources.
 *
 * Additionally, it automatically starts the H2 Web Console so the database
 * can be inspected at http://localhost:8082 without manual startup.
 *
 * @author Developer
 * @version 16.0
 * @since 16.0
 */

package com.app.quantitymeasurement;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import org.h2.tools.Server;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.app.quantitymeasurement.util.ApplicationConfig;
import com.app.quantitymeasurement.util.DatabaseConfig;

public class QuantityMeasurementApp {

    private static final Logger logger =
            Logger.getLogger(QuantityMeasurementApp.class.getName());

    private static IQuantityMeasurementRepository repository;

    private static Server webServer;   // H2 console server

    public static void main(String[] args) {

        logger.info("Starting Quantity Measurement Application...");

        try {

            startH2Console(); // Start H2 web console automatically

            ApplicationConfig.loadProperties();

            DatabaseConfig.initializeDatabase();

            repository = initializeRepository();

            QuantityMeasurementServiceImpl service =
                    new QuantityMeasurementServiceImpl(repository);

            QuantityMeasurementController controller =
                    new QuantityMeasurementController(service);

            controller.startApplication();

            reportMeasurements();

        } catch (Exception e) {

            logger.severe("Application error: " + e.getMessage());

        } finally {

            closeResources();
            stopH2Console();
        }
    }

    /**
     * Starts the H2 Web Console automatically.
     */
    
    private static Server web;
    private static Server tcp;
    
    private static void startH2Console() {
        try {

            tcp = Server.createTcpServer(
                    "-tcp",
                    "-tcpAllowOthers",
                    "-tcpPort",
                    "9092").start();

            web = Server.createWebServer(
                    "-web",
                    "-webAllowOthers",
                    "-webPort",
                    "8082").start();

            logger.info("H2 Console started at http://localhost:8082");

        } catch (SQLException e) {
            logger.warning("Unable to start H2 Console: " + e.getMessage());
        }
    }

    /**
     * Stops the H2 Web Console when application shuts down.
     */
    private static void stopH2Console() {

        if (web != null) {
            web.stop();
        }

        if (tcp != null) {
            tcp.stop();
        }

        logger.info("H2 servers stopped.");
    }

    private static IQuantityMeasurementRepository initializeRepository() {

        String repositoryType = ApplicationConfig.getRepositoryType();

        logger.info("Configured repository type: " + repositoryType);

        if ("database".equalsIgnoreCase(repositoryType)) {

            logger.info("Using Database Repository");

            return new QuantityMeasurementDatabaseRepository();

        } else {

            logger.info("Using Cache Repository");

            return QuantityMeasurementCacheRepository.getInstance();
        }
    }

    private static void reportMeasurements() {

        List<QuantityMeasurementEntity> list = repository.getAllMeasurements();

        logger.info("Total measurements stored: " + list.size());

        for (QuantityMeasurementEntity entity : list) {
            logger.info(entity.toString());
        }
    }

    private static void deleteAllMeasurements() {

        logger.info("Deleting all measurements from repository");

        repository.deleteAll();
    }

    private static void closeResources() {

        logger.info("Closing repository resources");

        if (repository != null) {
            repository.releaseResources();
        }
    }
}