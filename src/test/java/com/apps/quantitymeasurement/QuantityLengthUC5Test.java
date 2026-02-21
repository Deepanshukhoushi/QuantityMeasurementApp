package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static com.apps.quantitymeasurement.LengthUnit.*;

class QuantityLengthUC5Test {

    private static final double EPSILON = 1e-6;

    // ===============================
    // BASIC CONVERSIONS
    // ===============================

    @Test
    void testConversion_FeetToInches() {
        double result = QuantityLength.convert(1.0, FEET, INCHES);
        assertEquals(12.0, result, EPSILON);
    }

    @Test
    void testConversion_InchesToFeet() {
        double result = QuantityLength.convert(24.0, INCHES, FEET);
        assertEquals(2.0, result, EPSILON);
    }

    @Test
    void testConversion_YardsToInches() {
        double result = QuantityLength.convert(1.0, YARDS, INCHES);
        assertEquals(36.0, result, EPSILON);
    }

    @Test
    void testConversion_InchesToYards() {
        double result = QuantityLength.convert(72.0, INCHES, YARDS);
        assertEquals(2.0, result, EPSILON);
    }

    @Test
    void testConversion_CentimetersToInches() {
        double result = QuantityLength.convert(2.54, CENTIMETERS, INCHES);
        assertEquals(1.0, result, 1e-4); // slightly relaxed tolerance
    }

    @Test
    void testConversion_FeetToYards() {
        double result = QuantityLength.convert(6.0, FEET, YARDS);
        assertEquals(2.0, result, EPSILON);
    }

    // ===============================
    // ROUND TRIP TEST
    // ===============================

    @Test
    void testConversion_RoundTrip_PreservesValue() {

        double value = 5.0;

        double converted =
                QuantityLength.convert(value, FEET, INCHES);

        double back =
                QuantityLength.convert(converted, INCHES, FEET);

        assertEquals(value, back, EPSILON);
    }

    // ===============================
    // EDGE CASES
    // ===============================

    @Test
    void testConversion_ZeroValue() {
        double result = QuantityLength.convert(0.0, FEET, INCHES);
        assertEquals(0.0, result, EPSILON);
    }

    @Test
    void testConversion_NegativeValue() {
        double result = QuantityLength.convert(-1.0, FEET, INCHES);
        assertEquals(-12.0, result, EPSILON);
    }

    @Test
    void testConversion_SameUnit() {
        double result = QuantityLength.convert(5.0, FEET, FEET);
        assertEquals(5.0, result, EPSILON);
    }

    // ===============================
    // VALIDATION TESTS
    // ===============================

    @Test
    void testConversion_InvalidUnit_Throws() {
        assertThrows(IllegalArgumentException.class,
                () -> QuantityLength.convert(1.0, null, FEET));
    }

    @Test
    void testConversion_NaN_Throws() {
        assertThrows(IllegalArgumentException.class,
                () -> QuantityLength.convert(Double.NaN, FEET, INCHES));
    }

    @Test
    void testConversion_Infinite_Throws() {
        assertThrows(IllegalArgumentException.class,
                () -> QuantityLength.convert(Double.POSITIVE_INFINITY, FEET, INCHES));
    }

    // ===============================
    // EQUALITY TESTS (UC3/UC4 Compatibility)
    // ===============================

    @Test
    void testEquality_YardToFeet() {
        QuantityLength yard = new QuantityLength(1.0, YARDS);
        QuantityLength feet = new QuantityLength(3.0, FEET);
        assertTrue(yard.equals(feet));
    }

    @Test
    void testEquality_CentimeterToInch() {
        QuantityLength cm = new QuantityLength(1.0, CENTIMETERS);
        QuantityLength inch = new QuantityLength(0.393701, INCHES);
        assertTrue(cm.equals(inch));
    }

    @Test
    void testEquality_SameReference() {
        QuantityLength q = new QuantityLength(5.0, FEET);
        assertTrue(q.equals(q));
    }

    @Test
    void testEquality_NullComparison() {
        QuantityLength q = new QuantityLength(5.0, FEET);
        assertFalse(q.equals(null));
    }
}