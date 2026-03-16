package com.app.quantitymeasurement.quantity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.quantity.Length;
import com.app.quantitymeasurement.unit.LengthUnit;

public class LengthAdditionTargetUnitEquality {

    private static final double EPS = 1e-3;

    @Test
    void testAddition_ExplicitTargetUnit_Feet() {
        Length result = new Length(1.0, LengthUnit.FEET).add(new Length(12.0, LengthUnit.INCHES), LengthUnit.FEET);

        assertEquals(2.0, result.getValue(), EPS);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testAddition_ExplicitTargetUnit_Inches() {
        Length result = new Length(1.0, LengthUnit.FEET).add(new Length(12.0, LengthUnit.INCHES), LengthUnit.INCHES);

        assertEquals(24.0, result.getValue(), EPS);
    }

    @Test
    void testAddition_ExplicitTargetUnit_Yards() {
        Length result = new Length(1.0, LengthUnit.FEET).add(new Length(12.0, LengthUnit.INCHES), LengthUnit.YARDS);

        assertEquals(0.667, result.getValue(), EPS);
    }

    @Test
    void testAddition_Commutativity_WithTargetUnit() {
        Length a = new Length(1.0, LengthUnit.FEET);
        Length b = new Length(12.0, LengthUnit.INCHES);

        Length result1 = a.add(b, LengthUnit.YARDS);
        Length result2 = b.add(a, LengthUnit.YARDS);

        assertTrue(result1.equals(result2));
    }

    @Test
    void testAddition_NullTargetUnit() {
        assertThrows(IllegalArgumentException.class, () -> new Length(1.0, LengthUnit.FEET).add(new Length(12.0, LengthUnit.INCHES), null));
    }
}