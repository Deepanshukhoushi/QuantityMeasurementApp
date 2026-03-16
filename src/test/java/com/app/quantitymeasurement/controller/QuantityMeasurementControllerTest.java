package com.app.quantitymeasurement.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.quantity.Quantity;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.app.quantitymeasurement.unit.LengthUnit;

public class QuantityMeasurementControllerTest {

    private IQuantityMeasurementService serviceMock;
    private QuantityMeasurementController controller;

    @BeforeEach
    void setUp() {
        serviceMock = mock(IQuantityMeasurementService.class);
        controller = new QuantityMeasurementController(serviceMock);
    }

    @Test
    void testController_RunOperation() {
        // Mock the service to do nothing on performOperation
        doNothing().when(serviceMock).performOperation(any(Scanner.class), anyInt(), any(Quantity.class), any());

        // Category: 1 (Length)
        // Operation: 1 (Compare)
        // Unit choice: 1 (FEET)
        // Value: 12.0
        String simulatedInput = "1\n1\n1\n12.0\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        
        // Temporarily swap System.in content as startApplication creates a NEW scanner wrapper around System.in
        java.io.InputStream sysInBackup = System.in;
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        
        try {
            assertDoesNotThrow(() -> {
                controller.startApplication();
            });
            // Verify our mock was interacted with
            verify(serviceMock).performOperation(any(Scanner.class), anyInt(), any(Quantity.class), any());
        } finally {
            System.setIn(sysInBackup);
        }
    }
}