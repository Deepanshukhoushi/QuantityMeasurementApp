package com.app.quantitymeasurement.quantity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.quantity.Length;
import com.app.quantitymeasurement.unit.LengthUnit;

public class LengthAdditionEquality {

    private static final double EPS = 1e-6;

    @Test
    void testAddition_SameUnit_FeetPlusFeet() {
        Length result = new Length(1.0, LengthUnit.FEET).add(new Length(2.0, LengthUnit.FEET));

        assertEquals(3.0, result.getValue(), EPS);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testAddition_SameUnit_InchPlusInch() {
        Length result = new Length(6.0, LengthUnit.INCHES).add(new Length(6.0, LengthUnit.INCHES));

        assertEquals(12.0, result.getValue(), EPS);
        assertEquals(LengthUnit.INCHES, result.getUnit());
    }

    @Test
    void testAddition_CrossUnit_FeetPlusInches() {
        Length result = new Length(1.0, LengthUnit.FEET).add(new Length(12.0, LengthUnit.INCHES));

        assertEquals(2.0, result.getValue(), EPS);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testAddition_CrossUnit_InchesPlusFeet() {
        Length result = new Length(12.0, LengthUnit.INCHES).add(new Length(1.0, LengthUnit.FEET));

        assertEquals(24.0, result.getValue(), EPS);
        assertEquals(LengthUnit.INCHES, result.getUnit());
    }

    @Test
    void testAddition_YardPlusFeet() {
        Length result = new Length(1.0, LengthUnit.YARDS).add(new Length(3.0, LengthUnit.FEET));

        assertEquals(2.0, result.getValue(), EPS);
        assertEquals(LengthUnit.YARDS, result.getUnit());
    }

    @Test
    void testAddition_CentimeterPlusInch() {
        Length result = new Length(2.54, LengthUnit.CENTIMETERS).add(new Length(1.0, LengthUnit.INCHES));

        assertEquals(5.08, result.getValue(), 1e-2);
        assertEquals(LengthUnit.CENTIMETERS, result.getUnit());
    }

    @Test
    void testAddition_Commutativity() {
        Length a = new Length(1.0, LengthUnit.FEET);
        Length b = new Length(12.0, LengthUnit.INCHES);

        Length result1 = a.add(b);
        Length result2 = b.add(a);

        assertTrue(result1.equals(result2));
    }

    @Test
    void testAddition_WithZero() {
        Length result = new Length(5.0, LengthUnit.FEET).add(new Length(0.0, LengthUnit.INCHES));

        assertEquals(5.0, result.getValue(), EPS);
    }

    @Test
    void testAddition_NegativeValues() {
        Length result = new Length(5.0, LengthUnit.FEET).add(new Length(-2.0, LengthUnit.FEET));

        assertEquals(3.0, result.getValue(), EPS);
    }

    @Test
    void testAddition_NullSecondOperand() {
        assertThrows(IllegalArgumentException.class, () -> new Length(1.0, LengthUnit.FEET).add(null));
    }

    @Test
    void testAddition_LargeValues() {
        Length result = new Length(1e6, LengthUnit.FEET).add(new Length(1e6, LengthUnit.FEET));

        assertEquals(2e6, result.getValue(), EPS);
    }

    @Test
    void testAddition_SmallValues() {
        Length result = new Length(0.001, LengthUnit.FEET).add(new Length(0.002, LengthUnit.FEET));

        assertEquals(0.003, result.getValue(), EPS);
    }
}