package com.app.quantitymeasurement.repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.util.ConnectionPool;
import com.app.quantitymeasurement.util.DatabaseConfig;

public class QuantityMeasurementDatabaseRepositoryTest {

    private static QuantityMeasurementDatabaseRepository repository;

    @BeforeAll
    static void setupDatabaseConfig() {
        System.setProperty("db.url", "jdbc:h2:mem:testdb_repo;DB_CLOSE_DELAY=-1");
        System.setProperty("repository.type", "database");

        ConnectionPool.initializeConnectionPool();
        DatabaseConfig.initializeDatabase();
        repository = new QuantityMeasurementDatabaseRepository();
    }

    @BeforeEach
    void cleanTables() {
        repository.deleteAll();
    }

    @AfterAll
    static void tearDownDatabase() {
        repository.releaseResources();
        ConnectionPool.closePool();
    }

    @Test
    void testDatabaseRepository_SaveEntity() {
        QuantityMeasurementEntity entity = createDummyEntity();
        assertDoesNotThrow(() -> repository.save(entity));

        List<QuantityMeasurementEntity> list = repository.getAllMeasurements();
        assertEquals(1, list.size());
        assertEquals(1.0, list.get(0).getThisValue());
    }

    @Test
    void testDatabaseRepository_RetrieveAllMeasurements() {
        for (int i = 0; i < 3; i++) {
            repository.save(createDummyEntity());
        }
        assertEquals(3, repository.getAllMeasurements().size());
    }

    @Test
    void testDatabaseRepository_QueryByOperation() {
        QuantityMeasurementEntity e1 = createDummyEntity();
        e1.setOperation("ADD");
        repository.save(e1);

        QuantityMeasurementEntity e2 = createDummyEntity();
        e2.setOperation("COMPARE");
        repository.save(e2);

        List<QuantityMeasurementEntity> addRecords = repository.getMeasurementsByOperation("ADD");
        assertEquals(1, addRecords.size());
        assertEquals("ADD", addRecords.get(0).getOperation());
    }

    @Test
    void testDatabaseRepository_QueryByMeasurementType() {
        QuantityMeasurementEntity e1 = createDummyEntity();
        e1.setThisMeasurementType("WEIGHT");
        repository.save(e1);

        QuantityMeasurementEntity e2 = createDummyEntity();
        e2.setThisMeasurementType("LENGTH");
        repository.save(e2);

        List<QuantityMeasurementEntity> weightRecords = repository.getMeasurementsByType("WEIGHT");
        assertEquals(1, weightRecords.size());
        assertEquals("WEIGHT", weightRecords.get(0).getThisMeasurementType());
    }

    @Test
    void testDatabaseRepository_CountMeasurements() {
        assertEquals(0, repository.getTotalCount());
        repository.save(createDummyEntity());
        repository.save(createDummyEntity());
        assertEquals(2, repository.getTotalCount());
    }

    @Test
    void testDatabaseRepository_DeleteAll() {
        repository.save(createDummyEntity());
        repository.save(createDummyEntity());
        assertEquals(2, repository.getTotalCount());
        repository.deleteAll();
        assertEquals(0, repository.getTotalCount());
    }

    @Test
    void testDatabaseSchema_TablesCreated() throws SQLException {
        try (Connection connection = ConnectionPool.getConnection();
             ResultSet rs = connection.getMetaData().getTables(null, null, "QUANTITY_MEASUREMENT_ENTITY", null)) {
            assertTrue(rs.next(), "Table QUANTITY_MEASUREMENT_ENTITY should exist");
        }
    }

    @Test
    void testH2TestDatabase_IsolationBetweenTests() {
        // Since cleanTables runs before every test, this should always be 0 initially
        assertEquals(0, repository.getTotalCount());
        repository.save(createDummyEntity());
    }

    @Test
    void testDatabaseRepositoryPoolStatistics() {
        String stats = repository.getPoolStatistics();
        assertNotNull(stats);
        assertTrue(stats.contains("Active Connections:") || stats.equals("Pool statistics not available"));
    }

    @Test
    void testDatabaseRepository_ConcurrentAccess() throws InterruptedException {
        int threads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);

        for (int i = 0; i < threads; i++) {
            executorService.execute(() -> {
                QuantityMeasurementEntity entity = createDummyEntity();
                entity.setOperation("CONCURRENT");
                repository.save(entity);
                latch.countDown();
            });
        }

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        
        // Count should match the threads exactly as save is synchronized by DB transactions
        assertEquals(10, repository.getTotalCount());
    }

    @Test
    void testBatchInsert_MultipleEntities() {
        for (int i = 0; i < 50; i++) {
            QuantityMeasurementEntity entity = createDummyEntity();
            entity.setOperation("BATCH");
            repository.save(entity);
        }
        assertEquals(50, repository.getTotalCount());
    }

    @Test
    void testResourceCleanup_ConnectionClosed() throws SQLException {
        Connection capturedConnection;
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT 1")) {
            capturedConnection = conn;
            try (ResultSet rs = stmt.executeQuery()) {
                assertTrue(rs.next());
            }
        }
        // Since Hikari proxies connections, the proxy is 'closed' (returned to pool)
        assertTrue(capturedConnection.isClosed());
    }

    private QuantityMeasurementEntity createDummyEntity() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        entity.setThisValue(1.0);
        entity.setThisUnit("FEET");
        entity.setThisMeasurementType("LENGTH");
        entity.setOperation("CONVERT");
        entity.setResultString("12.0 INCHES");
        entity.setError(false);
        return entity;
    }
}
