/**
 * QuantityMeasurementApp.java
 * This is the main entry point of the Quantity Measurement Application.
 * It is responsible for bootstrapping the application by initializing the configuration,
 * setting up the database connection pool, determining the appropriate repository implementation
 * (cache vs database based on application properties), and starting the user interaction
 * via the QuantityMeasurementController. It orchestrates the lifecycle of the application,
 * including clean up of resources when the application terminates.
 *
 * @author Developer
 * @version 16.0
 * @since 16.0
 */
package com.app.quantitymeasurement;

import java.util.List;
import java.util.logging.Logger;

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

    public static void main(String[] args) {

        logger.info("Starting Quantity Measurement Application...");

        try {
        	
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
        }
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