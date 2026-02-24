package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class QuantityTest {

    @Test
    void testLengthEquality() {
        Quantity<LengthUnit> a =
                new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> b =
                new Quantity<>(12.0, LengthUnit.INCHES);

        assertTrue(a.equals(b));
    }

    @Test
    void testWeightEquality() {
        Quantity<WeightUnit> a =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> b =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        assertTrue(a.equals(b));
    }

    @Test
    void testLengthConversion() {
        Quantity<LengthUnit> q =
                new Quantity<>(1.0, LengthUnit.FEET);

        assertEquals(12.0,
                q.convertTo(LengthUnit.INCHES));
    }

    @Test
    void testWeightAddition() {
        Quantity<WeightUnit> a =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> b =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        Quantity<WeightUnit> result =
                a.add(b, WeightUnit.KILOGRAM);

        assertEquals(2.0, result.getValue());
    }

    @Test
    void testCrossCategoryPrevention() {
        Quantity<LengthUnit> length =
                new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<WeightUnit> weight =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertFalse(length.equals(weight));
    }
}