package com.app.quantitymeasurement.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

public class QuantityMeasurementCacheRepositoryTest {

    private QuantityMeasurementCacheRepository repository;

    @BeforeEach
    void setUp() {
        repository = QuantityMeasurementCacheRepository.getInstance();
        // Clear the singleton cache before each test to ensure isolation
        repository.deleteAll();
    }

    @Test
    void testSingletonInstance() {
        QuantityMeasurementCacheRepository anotherInstance = QuantityMeasurementCacheRepository.getInstance();
        assertEquals(repository, anotherInstance, "Instances should be the exact same singleton instance");
    }

    @Test
    void testSaveAndRetrieveAll() {
        QuantityMeasurementEntity entity1 = createEntity("COMPARE", "LENGTH");
        QuantityMeasurementEntity entity2 = createEntity("CONVERT", "WEIGHT");

        repository.save(entity1);
        repository.save(entity2);

        List<QuantityMeasurementEntity> results = repository.getAllMeasurements();
        assertEquals(2, results.size(), "Should retrieve all 2 saved entities");
        assertTrue(results.contains(entity1));
        assertTrue(results.contains(entity2));
    }

    @Test
    void testGetMeasurementsByOperation() {
        repository.save(createEntity("COMPARE", "LENGTH"));
        repository.save(createEntity("ADD", "LENGTH"));
        repository.save(createEntity("COMPARE", "WEIGHT"));

        List<QuantityMeasurementEntity> compareResults = repository.getMeasurementsByOperation("COMPARE");
        assertEquals(2, compareResults.size(), "Should only retrieve entities with COMPARE operation");

        List<QuantityMeasurementEntity> addResults = repository.getMeasurementsByOperation("ADD");
        assertEquals(1, addResults.size(), "Should only retrieve entities with ADD operation");
    }

    @Test
    void testGetMeasurementsByType() {
        repository.save(createEntity("COMPARE", "LENGTH"));
        repository.save(createEntity("ADD", "WEIGHT"));
        repository.save(createEntity("COMPARE", "VOLUME"));

        List<QuantityMeasurementEntity> lengthResults = repository.getMeasurementsByType("LENGTH");
        assertEquals(1, lengthResults.size(), "Should only retrieve entities with LENGTH type");

        List<QuantityMeasurementEntity> missingResults = repository.getMeasurementsByType("TEMPERATURE");
        assertTrue(missingResults.isEmpty(), "Should retrieve empty list when type is not found");
    }

    @Test
    void testTotalCount() {
        assertEquals(0, repository.getTotalCount());
        repository.save(createEntity("COMPARE", "LENGTH"));
        repository.save(createEntity("ADD", "LENGTH"));
        assertEquals(2, repository.getTotalCount());
    }

    @Test
    void testDeleteAll() {
        repository.save(createEntity("COMPARE", "LENGTH"));
        assertEquals(1, repository.getTotalCount());
        
        repository.deleteAll();
        assertEquals(0, repository.getTotalCount());
        assertTrue(repository.getAllMeasurements().isEmpty());
    }

    private QuantityMeasurementEntity createEntity(String operation, String type) {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setOperation(operation);
        entity.setThisMeasurementType(type);
        entity.setResultString("Dummy Result");
        return entity;
    }
}
