/**
 * IQuantityMeasurementRepository.java
 *
 * This interface defines the contract for the repository layer in the Quantity Measurement
 * application. It specifies the standard Create, Read, Update, Delete (CRUD) abstract operations
 * and specific querying methods required to manage QuantityMeasurementEntity records.
 * Implementations of this interface support different data storage mechanisms, such as
 * in-memory caching or a persistent relational database, allowing for flexible dependency injection 
 * and varying application configurations while keeping the service layer decoupled from storage specifics.
 *
 * @author Developer
 * @version 16.0
 * @since 16.0
 */
package com.app.quantitymeasurement.repository;

import java.util.List;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

public interface IQuantityMeasurementRepository {

    void save(QuantityMeasurementEntity entity);

    List<QuantityMeasurementEntity> getAllMeasurements();

    List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation);

    List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType);

    void deleteAll();

    long getTotalCount();

    default String getPoolStatistics() {
        return "Pool statistics not available";
    }

    default void releaseResources() {
        // Default no-op implementation
    }
}