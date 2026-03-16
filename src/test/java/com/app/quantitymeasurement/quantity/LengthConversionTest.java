package com.app.quantitymeasurement.quantity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.quantity.Length;
import com.app.quantitymeasurement.unit.LengthUnit;

public class LengthConversionTest {

    private static final double EPS = 1e-6;

    @Test
    void testConversion_FeetToInches() {
        assertEquals(12.0, Length.convert(1.0, LengthUnit.FEET, LengthUnit.INCHES), EPS);
    }

    @Test
    void testConversion_InchesToFeet() {
        assertEquals(2.0, Length.convert(24.0, LengthUnit.INCHES, LengthUnit.FEET), EPS);
    }

    @Test
    void testConversion_YardsToFeet() {
        assertEquals(9.0, Length.convert(3.0, LengthUnit.YARDS, LengthUnit.FEET), EPS);
    }

    @Test
    void testConversion_CentimetersToInches() {
        assertEquals(1.0, Length.convert(2.54, LengthUnit.CENTIMETERS, LengthUnit.INCHES), 1e-3);
    }

    @Test
    void testConversion_ZeroValue() {
        assertEquals(0.0, Length.convert(0.0, LengthUnit.FEET, LengthUnit.INCHES), EPS);
    }

    @Test
    void testConversion_NegativeValue() {
        assertEquals(-12.0, Length.convert(-1.0, LengthUnit.FEET, LengthUnit.INCHES), EPS);
    }

    @Test
    void testConversion_InvalidUnit() {
        assertThrows(IllegalArgumentException.class, () -> Length.convert(1.0, null, LengthUnit.FEET));
    }

    @Test
    void testConversion_NaN() {
        assertThrows(IllegalArgumentException.class, () -> Length.convert(Double.NaN, LengthUnit.FEET, LengthUnit.INCHES));
    }
}