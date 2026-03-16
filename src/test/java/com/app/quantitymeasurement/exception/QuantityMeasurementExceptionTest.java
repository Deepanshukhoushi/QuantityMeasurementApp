package com.app.quantitymeasurement.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class QuantityMeasurementExceptionTest {

    @Test
    void testExceptionMessageOnly() {
        QuantityMeasurementException exception = new QuantityMeasurementException("Custom error message");
        assertEquals("Custom error message", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testExceptionMessageAndCause() {
        Throwable cause = new RuntimeException("Underlying root issue");
        QuantityMeasurementException exception = new QuantityMeasurementException("Top level error message", cause);
        
        assertEquals("Top level error message", exception.getMessage());
        assertEquals(cause, exception.getCause());
        assertEquals("Underlying root issue", exception.getCause().getMessage());
    }
}
