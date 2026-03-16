package com.app.quantitymeasurement.units;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.quantity.Quantity;
import com.app.quantitymeasurement.unit.LengthUnit;
import com.app.quantitymeasurement.unit.TemperatureUnit;
import com.app.quantitymeasurement.unit.VolumeUnit;
import com.app.quantitymeasurement.unit.WeightUnit;

public class TemperatureUnitTest {

    // ===== EQUALITY =====

    @Test
    void testCelsiusToCelsius() {
        assertEquals(new Quantity<>(0.0, TemperatureUnit.CELSIUS),
                     new Quantity<>(0.0, TemperatureUnit.CELSIUS));
    }

    @Test
    void testCelsiusToFahrenheit_0_Equals_32() {
        assertEquals(new Quantity<>(0.0, TemperatureUnit.CELSIUS),
                     new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT));
    }

    @Test
    void testCelsiusToFahrenheit_100_Equals_212() {
        assertEquals(new Quantity<>(100.0, TemperatureUnit.CELSIUS),
                     new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT));
    }

    @Test
    void testNegative40Equal() {
        assertEquals(new Quantity<>(-40.0, TemperatureUnit.CELSIUS),
                     new Quantity<>(-40.0, TemperatureUnit.FAHRENHEIT));
    }

    @Test
    void testCelsiusToKelvin() {
        assertEquals(new Quantity<>(0.0, TemperatureUnit.CELSIUS),
                     new Quantity<>(273.15, TemperatureUnit.KELVIN));
    }

    @Test
    void testAbsoluteZero() {
        assertEquals(new Quantity<>(-273.15, TemperatureUnit.CELSIUS),
                     new Quantity<>(0.0, TemperatureUnit.KELVIN));
    }

    @Test
    void testSymmetricProperty() {
        Quantity<TemperatureUnit> a = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> b = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);

        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
    }

    @Test
    void testReflexiveProperty() {
        Quantity<TemperatureUnit> a = new Quantity<>(25.0, TemperatureUnit.CELSIUS);

        assertTrue(a.equals(a));
    }

    // ===== CONVERSION =====

    @Test
    void testConvertCelsiusToFahrenheit() {
        Quantity<TemperatureUnit> result = new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                                                   .convertTo(TemperatureUnit.FAHRENHEIT);

        assertEquals(new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT), result);
    }

    @Test
    void testRoundTripConversion() {
        Quantity<TemperatureUnit> original = new Quantity<>(50.0, TemperatureUnit.CELSIUS);

        Quantity<TemperatureUnit> converted = original.convertTo(TemperatureUnit.FAHRENHEIT)
                                                         .convertTo(TemperatureUnit.CELSIUS);

        assertEquals(original, converted);
    }

    @Test
    void testZeroValueConversion() {
        Quantity<TemperatureUnit> result = new Quantity<>(0.0, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.FAHRENHEIT);

        assertEquals(new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT), result);
    }

    @Test
    void testNegativeConversion() {
        Quantity<TemperatureUnit> result = new Quantity<>(-20.0, TemperatureUnit.CELSIUS).convertTo(TemperatureUnit.FAHRENHEIT);

        assertEquals(new Quantity<>(-4.0, TemperatureUnit.FAHRENHEIT), result);
    }

    // ===== UNSUPPORTED OPERATIONS =====

    @Test
    void testAddUnsupported() {
        assertThrows(UnsupportedOperationException.class,
                () -> new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                        .add(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
    }

    @Test
    void testSubtractUnsupported() {
        assertThrows(UnsupportedOperationException.class, () -> new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                                                       .subtract(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
    }

    @Test
    void testDivideUnsupported() {
        assertThrows(UnsupportedOperationException.class, () -> new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                                                         .divide(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
    }

    // ===== CROSS CATEGORY =====

    @Test
    void testTemperatureVsLength() {
        assertNotEquals(new Quantity<>(100.0, TemperatureUnit.CELSIUS),
                        new Quantity<>(100.0, LengthUnit.FEET));
    }

    @Test
    void testTemperatureVsWeight() {
        assertNotEquals(new Quantity<>(50.0, TemperatureUnit.CELSIUS),
                        new Quantity<>(50.0, WeightUnit.KILOGRAM));
    }

    @Test
    void testTemperatureVsVolume() {
        assertNotEquals(new Quantity<>(25.0, TemperatureUnit.CELSIUS),
                        new Quantity<>(25.0, VolumeUnit.LITRE));
    }

    // ===== OPERATION SUPPORT =====

    @Test
    void testSupportsArithmeticFalse() {
        assertFalse(TemperatureUnit.CELSIUS.supportsArithmetic());
    }

    @Test
    void testValidateOperationThrows() {
        assertThrows(UnsupportedOperationException.class, () -> TemperatureUnit.CELSIUS.validateOperationSupport("ADD"));
    }

    @Test
    void testNullUnitValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Quantity<>(100.0, null));
    }

    @Test
    void testNullComparison() {
        Quantity<TemperatureUnit> q = new Quantity<>(100.0, TemperatureUnit.CELSIUS);

        assertNotEquals(null, q);
    }
}