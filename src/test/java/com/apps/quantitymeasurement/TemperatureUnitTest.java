package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureUnitTest {

    private static final double EPSILON = 1e-6;

    // =====================================================
    // EQUALITY TESTS
    // =====================================================

    @Test
    void testTemperatureEquality_CelsiusToCelsius_SameValue() {
        assertTrue(
                new Quantity<>(0.0, TemperatureUnit.CELSIUS)
                        .equals(new Quantity<>(0.0, TemperatureUnit.CELSIUS))
        );
    }

    @Test
    void testTemperatureEquality_FahrenheitToFahrenheit_SameValue() {
        assertTrue(
                new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT)
                        .equals(new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT))
        );
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit_0Equals32() {
        assertTrue(
                new Quantity<>(0.0, TemperatureUnit.CELSIUS)
                        .equals(new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT))
        );
    }

    @Test
    void testTemperatureEquality_100CEquals212F() {
        assertTrue(
                new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                        .equals(new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT))
        );
    }

    @Test
    void testTemperatureEquality_Negative40Equal() {
        assertTrue(
                new Quantity<>(-40.0, TemperatureUnit.CELSIUS)
                        .equals(new Quantity<>(-40.0, TemperatureUnit.FAHRENHEIT))
        );
    }

    @Test
    void testTemperatureEquality_SymmetricProperty() {
        Quantity<TemperatureUnit> a =
                new Quantity<>(0.0, TemperatureUnit.CELSIUS);

        Quantity<TemperatureUnit> b =
                new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);

        assertTrue(a.equals(b) && b.equals(a));
    }

    @Test
    void testTemperatureEquality_ReflexiveProperty() {
        Quantity<TemperatureUnit> a =
                new Quantity<>(50.0, TemperatureUnit.CELSIUS);

        assertTrue(a.equals(a));
    }

    @Test
    void testTemperatureDifferentValuesInequality() {
        assertFalse(
                new Quantity<>(50.0, TemperatureUnit.CELSIUS)
                        .equals(new Quantity<>(100.0, TemperatureUnit.CELSIUS))
        );
    }

    // =====================================================
    // CONVERSION TESTS
    // =====================================================

    @Test
    void testTemperatureConversion_CelsiusToFahrenheit() {
        Quantity<TemperatureUnit> result =
                new Quantity<>(50.0, TemperatureUnit.CELSIUS)
                        .convertTo(TemperatureUnit.FAHRENHEIT);

        assertEquals(122.0, result.getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_FahrenheitToCelsius() {
        Quantity<TemperatureUnit> result =
                new Quantity<>(-4.0, TemperatureUnit.FAHRENHEIT)
                        .convertTo(TemperatureUnit.CELSIUS);

        assertEquals(-20.0, result.getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_RoundTrip() {
        Quantity<TemperatureUnit> original =
                new Quantity<>(37.0, TemperatureUnit.CELSIUS);

        Quantity<TemperatureUnit> converted =
                original.convertTo(TemperatureUnit.FAHRENHEIT)
                        .convertTo(TemperatureUnit.CELSIUS);

        assertEquals(original.getValue(), converted.getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_SameUnit() {
        Quantity<TemperatureUnit> original =
                new Quantity<>(25.0, TemperatureUnit.CELSIUS);

        Quantity<TemperatureUnit> result =
                original.convertTo(TemperatureUnit.CELSIUS);

        assertEquals(25.0, result.getValue(), EPSILON);
    }

    @Test
    void testTemperatureConversion_LargeValues() {
        Quantity<TemperatureUnit> result =
                new Quantity<>(1000.0, TemperatureUnit.CELSIUS)
                        .convertTo(TemperatureUnit.KELVIN);

        assertEquals(1273.15, result.getValue(), EPSILON);
    }

    // =====================================================
    // UNSUPPORTED OPERATIONS
    // =====================================================

    @Test
    void testTemperatureUnsupportedOperation_Add() {
        assertThrows(UnsupportedOperationException.class, () ->
                new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                        .add(new Quantity<>(50.0, TemperatureUnit.CELSIUS))
        );
    }

    @Test
    void testTemperatureUnsupportedOperation_Subtract() {
        assertThrows(UnsupportedOperationException.class, () ->
                new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                        .subtract(new Quantity<>(50.0, TemperatureUnit.CELSIUS))
        );
    }

    @Test
    void testTemperatureUnsupportedOperation_Divide() {
        assertThrows(UnsupportedOperationException.class, () ->
                new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                        .divide(new Quantity<>(50.0, TemperatureUnit.CELSIUS))
        );
    }

    @Test
    void testTemperatureValidateOperationSupport_MethodBehavior() {
        assertThrows(UnsupportedOperationException.class, () ->
                TemperatureUnit.CELSIUS.validateOperationSupport("ADD")
        );
    }

    // =====================================================
    // CROSS CATEGORY SAFETY
    // =====================================================

    @Test
    void testTemperatureVsLengthIncompatibility() {
        Quantity<TemperatureUnit> temp =
                new Quantity<>(100.0, TemperatureUnit.CELSIUS);

        Quantity<LengthUnit> length =
                new Quantity<>(100.0, LengthUnit.FEET);

        assertFalse(temp.equals(length));
    }

    // =====================================================
    // INTERFACE EVOLUTION TESTS
    // =====================================================

    @Test
    void testOperationSupportMethods_TemperatureUnit() {
        assertFalse(TemperatureUnit.CELSIUS.supportsArithmetic());
    }

    @Test
    void testOperationSupportMethods_LengthUnit() {
        assertTrue(LengthUnit.FEET.supportsArithmetic());
    }

    @Test
    void testTemperatureEnumImplementsIMeasurable() {
        assertTrue(TemperatureUnit.CELSIUS instanceof IMeasurable);
    }

    @Test
    void testTemperatureUnit_NameMethod() {
        assertEquals("CELSIUS", TemperatureUnit.CELSIUS.getUnitName());
    }

    @Test
    void testTemperatureUnit_ConversionFactor() {
        assertEquals(1.0,
                TemperatureUnit.CELSIUS.getConversionFactor(),
                EPSILON);
    }

    @Test
    void testTemperatureNullUnitValidation() {
        assertThrows(IllegalArgumentException.class, () ->
                new Quantity<>(100.0, null)
        );
    }

    @Test
    void testTemperatureNullOperandValidation_InComparison() {
        Quantity<TemperatureUnit> temp =
                new Quantity<>(100.0, TemperatureUnit.CELSIUS);

        assertFalse(temp.equals(null));
    }
}