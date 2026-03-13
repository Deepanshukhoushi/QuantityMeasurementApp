package com.apps.quantitymeasurement.units;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.quantity.Length;

public class LengthUnitEquality {

    private static final double EPSILON = 1e-6;

    // ---------- ENUM EXISTENCE ----------

    @Test
    void testEnum_FeetExists() {
        assertNotNull(LengthUnit.FEET);
    }

    @Test
    void testEnum_InchesExists() {
        assertNotNull(LengthUnit.INCHES);
    }

    @Test
    void testEnum_YardsExists() {
        assertNotNull(LengthUnit.YARDS);
    }

    @Test
    void testEnum_CentimetersExists() {
        assertNotNull(LengthUnit.CENTIMETERS);
    }

    // ---------- convertToBaseUnit ----------

    @Test
    void testConvertToBaseUnit_Feet() {
        assertEquals(1.0, LengthUnit.FEET.convertToBaseUnit(1.0),EPSILON);
    }

    @Test
    void testConvertToBaseUnit_Inches() {
        assertEquals(1.0, LengthUnit.INCHES.convertToBaseUnit(12.0), EPSILON);
    }

    @Test
    void testConvertToBaseUnit_Yards() {
        assertEquals(3.0, LengthUnit.YARDS.convertToBaseUnit(1.0), EPSILON);
    }

    @Test
    void testConvertToBaseUnit_Centimeters() {
        assertEquals(1.0, LengthUnit.CENTIMETERS.convertToBaseUnit(30.48), 1e-3);
    }

    // ---------- convertFromBaseUnit ----------

    @Test
    void testConvertFromBaseUnit_Feet() {
        assertEquals(1.0,LengthUnit.FEET.convertFromBaseUnit(1.0),EPSILON);
    }

    @Test
    void testConvertFromBaseUnit_Inches() {
        assertEquals(12.0, LengthUnit.INCHES.convertFromBaseUnit(1.0),EPSILON);
    }

    @Test
    void testConvertFromBaseUnit_Yards() {
        assertEquals(1.0, LengthUnit.YARDS.convertFromBaseUnit(3.0),EPSILON);
    }

    @Test
    void testConvertFromBaseUnit_Centimeters() {
        assertEquals(30.48, LengthUnit.CENTIMETERS.convertFromBaseUnit(1.0), 1e-2);
    }

    // ---------- Delegation Through Length ----------

    @Test
    void testLengthConvertTo_Delegation() {
        Length length = new Length(1.0, LengthUnit.FEET);
        Length converted = length.convertTo(LengthUnit.INCHES);

        assertEquals(12.0, converted.getValue(),EPSILON);
        assertEquals(LengthUnit.INCHES, converted.getUnit());
    }

    // ---------- Static Convert Delegation ----------

    @Test
    void testStaticConvert_Delegation() {
        double result = Length.convert(1.0, LengthUnit.FEET, LengthUnit.INCHES);

        assertEquals(12.0, result,EPSILON);
    }

    // ---------- Equality Backward Compatibility ----------

    @Test
    void testEquality_BackwardCompatibility() {
        assertTrue(new Length(1.0, LengthUnit.FEET).equals(new Length(12.0, LengthUnit.INCHES)));
    }

    // ---------- Addition Backward Compatibility ----------

    @Test
    void testAddition_BackwardCompatibility() {
        Length result = new Length(1.0, LengthUnit.FEET).add(new Length(12.0, LengthUnit.INCHES));

        assertEquals(2.0, result.getValue(),EPSILON);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    // ---------- Addition With Target Unit ----------

    @Test
    void testAdditionWithTargetUnit_BackwardCompatibility() {
        Length result = new Length(1.0, LengthUnit.FEET).add(new Length(12.0, LengthUnit.INCHES), LengthUnit.YARDS);

        assertEquals(0.667, result.getValue(), 1e-3);
        assertEquals(LengthUnit.YARDS, result.getUnit());
    }
}
