package com.apps.quantitymeasurement.quantity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class FeetEquality {

    @Test
    void testEquality_SameValue() {
        assertTrue(new Feet(1.0).equals(new Feet(1.0)));
    }

    @Test
    void testEquality_DifferentValue() {
        assertFalse(new Feet(1.0).equals(new Feet(2.0)));
    }

    @Test
    void testEquality_NullComparison() {
        assertFalse(new Feet(1.0).equals(null));
    }

    @Test
    void testEquality_DifferentClass() {
        assertFalse(new Feet(1.0).equals("1.0"));
    }

    @Test
    void testEquality_SameReference() {
        Feet f = new Feet(1.0);
        assertTrue(f.equals(f));
    }
    
    @Test
    void testEquality_NonNumericInput() {
        assertThrows(NumberFormatException.class, () -> {
            Double.parseDouble("abc");
        });
    }
}