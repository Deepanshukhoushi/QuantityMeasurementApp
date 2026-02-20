package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    @Test
    void testEquality_SameValue() {
        Feet first = new Feet(1.0);
        Feet second = new Feet(1.0);

        assertTrue(first.equals(second),
                "Expected 1.0 ft to be equal to 1.0 ft");
    }

    @Test
    void testEquality_DifferentValue() {
        Feet first = new Feet(1.0);
        Feet second = new Feet(2.0);

        assertFalse(first.equals(second),
                "Expected 1.0 ft to NOT be equal to 2.0 ft");
    }

    @Test
    void testEquality_NullComparison() {
        Feet first = new Feet(1.0);

        assertFalse(first.equals(null),
                "Expected value to NOT be equal to null");
    }

    @Test
    void testEquality_SameReference() {
        Feet first = new Feet(1.0);

        assertTrue(first.equals(first),
                "Expected object to be equal to itself");
    }

    @Test
    void testEquality_DifferentType() {
        Feet first = new Feet(1.0);
        Object nonNumeric = "1.0";

        assertFalse(first.equals(nonNumeric),
                "Expected Feet to NOT be equal to non-Feet object");
    }
}
