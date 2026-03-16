package com.app.quantitymeasurement.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.quantity.Quantity;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.unit.LengthUnit;

public class QuantityMeasurementServiceTest {

    private IQuantityMeasurementRepository repositoryMock;
    private QuantityMeasurementServiceImpl service;

    @BeforeEach
    void setUp() {
        repositoryMock = mock(IQuantityMeasurementRepository.class);
        service = new QuantityMeasurementServiceImpl(repositoryMock);
    }

    @Test
    void testServiceWithDatabaseRepository_Integration() {
        // Simulating COMPARE operation (operation = 1)
        // User input for the second quantity: 1 (FEET), 1 (Value)
        String input = "1\n1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);

        assertDoesNotThrow(() -> {
            service.performOperation(scanner, 1, q1, LengthUnit.values());
        });

        // Verify that the repository's save method was called exactly once to persist the comparison fact
        verify(repositoryMock).save(any(QuantityMeasurementEntity.class));
    }

    @Test
    void testServiceWithCacheRepository_Integration() {
        // Testing that the architecture correctly allows dependency injection of the cache repo vs DB repo
        IQuantityMeasurementRepository cacheRepo = com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository.getInstance();
        QuantityMeasurementServiceImpl cacheService = new QuantityMeasurementServiceImpl(cacheRepo);

        String input = "1\n12\n"; // 1=FEET, 12 value
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);

        cacheService.performOperation(scanner, 1, q1, LengthUnit.values());
        
        // Cache initially empty, just did 1 save, should have 1 item
        org.junit.jupiter.api.Assertions.assertEquals(1, cacheRepo.getTotalCount());
    }

    @Test
    void testService_AddOperation() {
        // ADD operation (operation = 3)
        // User input for second quantity: 2 (INCHES), 12 (Value)
        String input = "2\n12\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);

        service.performOperation(scanner, 3, q1, LengthUnit.values());

        // Verify save was called to store the addition result
        verify(repositoryMock).save(any(QuantityMeasurementEntity.class));
    }
}
