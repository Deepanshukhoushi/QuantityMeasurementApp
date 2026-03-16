package com.app.quantitymeasurement.quantity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.quantity.Length;
import com.app.quantitymeasurement.unit.LengthUnit;

public class LengthEquality {

    @Test
    void testEquality_FeetToFeet_SameValue() {
        assertTrue(new Length(1.0, LengthUnit.FEET).equals(new Length(1.0, LengthUnit.FEET)));
    }

    @Test
    void testEquality_InchToInch_SameValue() {
        assertTrue(new Length(5.0, LengthUnit.INCHES).equals(new Length(5.0, LengthUnit.INCHES)));
    }

    @Test
    void testEquality_FeetToInch_EquivalentValue() {
        assertTrue(new Length(1.0, LengthUnit.FEET).equals(new Length(12.0, LengthUnit.INCHES)));
    }
    
    @Test
    void testEquality_YardToFeet_EquivalentValue() {
        assertTrue(new Length(1.0, LengthUnit.YARDS).equals(new Length(3.0, LengthUnit.FEET)));
    }

    @Test
    void testEquality_YardToInches_EquivalentValue() {
        assertTrue(new Length(1.0, LengthUnit.YARDS).equals(new Length(36.0, LengthUnit.INCHES)));
    }

    @Test
    void testEquality_CentimeterToInches_EquivalentValue() {
        assertTrue(new Length(1.0, LengthUnit.CENTIMETERS).equals(new Length(0.393701, LengthUnit.INCHES)));
    }


    @Test
    void testEquality_CentimeterToFeet_NonEquivalent() {
        assertFalse(new Length(1.0, LengthUnit.CENTIMETERS).equals(new Length(1.0, LengthUnit.FEET)));
    }

    @Test
    void testEquality_TransitiveProperty() {
        Length yard = new Length(1.0, LengthUnit.YARDS);
        Length feet = new Length(3.0, LengthUnit.FEET);
        Length inches = new Length(36.0, LengthUnit.INCHES);

        assertTrue(yard.equals(feet));
        assertTrue(feet.equals(inches));
        assertTrue(yard.equals(inches));
    }

    @Test
    void testEquality_DifferentValue() {
    	assertFalse(new Length(1.0, LengthUnit.FEET).equals(new Length(2.0, LengthUnit.FEET)));
        assertFalse(new Length(1.0, LengthUnit.YARDS).equals(new Length(2.0, LengthUnit.YARDS)));
        assertFalse(new Length(5.0, LengthUnit.INCHES).equals(new Length(6.0, LengthUnit.INCHES)));
        assertFalse(new Length(3.0, LengthUnit.CENTIMETERS).equals(new Length(4.0, LengthUnit.CENTIMETERS)));
    }

    @Test
    void testEquality_NullComparison() {
    	assertFalse(new Length(1.0, LengthUnit.FEET).equals(null));
        assertFalse(new Length(1.0, LengthUnit.YARDS).equals(null));
        assertFalse(new Length(1.0, LengthUnit.INCHES).equals(null));
        assertFalse(new Length(1.0, LengthUnit.CENTIMETERS).equals(null));
    }

    @Test
    void testEquality_SameReference() {
    	Length feet = new Length(1.0, LengthUnit.FEET);
        Length yard = new Length(1.0, LengthUnit.YARDS);
        Length inch = new Length(1.0, LengthUnit.INCHES);
        Length cm = new Length(1.0, LengthUnit.CENTIMETERS);

        assertTrue(feet.equals(feet));
        assertTrue(yard.equals(yard));
        assertTrue(inch.equals(inch));
        assertTrue(cm.equals(cm));
    }
}