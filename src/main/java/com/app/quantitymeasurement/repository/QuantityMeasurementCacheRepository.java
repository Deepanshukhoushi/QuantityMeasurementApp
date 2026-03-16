/**
 * QuantityMeasurementCacheRepository.java
 *
 * This class implements the IQuantityMeasurementRepository interface to provide an
 * in-memory storage solution for QuantityMeasurementEntity records. It utilizes a centralized
 * Java List as an ephemeral cache to store measurement operation results. It is designed as a
 * singleton to ensure state consistency across the application when a database is not active.
 * This repository is particularly useful for environments where persistent storage is either
 * unavailable, unnecessary (such as unit testing execution), or when running the application
 * purely in-memory.
 *
 * @author Developer
 * @version 16.0
 * @since 16.0
 */
package com.app.quantitymeasurement.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

public class QuantityMeasurementCacheRepository
        implements IQuantityMeasurementRepository {

    private static final Logger logger =
            Logger.getLogger(QuantityMeasurementCacheRepository.class.getName());

    private static QuantityMeasurementCacheRepository instance;

    private final List<QuantityMeasurementEntity> storage = new ArrayList<>();

    private QuantityMeasurementCacheRepository() {
        logger.info("QuantityMeasurementCacheRepository initialized");
    }

    public static QuantityMeasurementCacheRepository getInstance() {

        if (instance == null) {
            logger.info("Creating new repository instance");
            instance = new QuantityMeasurementCacheRepository();
        }

        return instance;
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {
        storage.add(entity);
        logger.info("Entity saved to repository: " + entity);
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        logger.info("Fetching all entities from repository");
        return new ArrayList<>(storage);
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
        logger.info("Fetching entities by operation: " + operation);
        List<QuantityMeasurementEntity> filtered = new ArrayList<>();
        for (QuantityMeasurementEntity entity : storage) {
            if (operation.equalsIgnoreCase(entity.getOperation())) {
                filtered.add(entity);
            }
        }
        return filtered;
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType) {
        logger.info("Fetching entities by type: " + measurementType);
        List<QuantityMeasurementEntity> filtered = new ArrayList<>();
        for (QuantityMeasurementEntity entity : storage) {
            if (measurementType.equalsIgnoreCase(entity.getThisMeasurementType())) {
                filtered.add(entity);
            }
        }
        return filtered;
    }

    @Override
    public void deleteAll() {
        logger.info("Deleting all measurements from cache repository");
        storage.clear();
    }

    @Override
    public long getTotalCount() {
        logger.info("Counting measurements in cache repository");
        return storage.size();
    }
}