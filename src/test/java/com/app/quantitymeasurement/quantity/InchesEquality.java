package com.app.quantitymeasurement.quantity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.quantity.Inches;

public class InchesEquality {

    @Test
    void testEquality_SameValue() {
        assertTrue(new Inches(5.0).equals(new Inches(5.0)));
    }

    @Test
    void testEquality_DifferentValue() {
        assertFalse(new Inches(5.0).equals(new Inches(10.0)));
    }

    @Test
    void testEquality_NullComparison() {
        assertFalse(new Inches(5.0).equals(null));
    }

    @Test
    void testEquality_DifferentClass() {
        assertFalse(new Inches(5.0).equals("5.0"));
    }

    @Test
    void testEquality_SameReference() {
        Inches i = new Inches(5.0);
        assertTrue(i.equals(i));
    }

    @Test
    void testEquality_NonNumericInput() {
        assertThrows(NumberFormatException.class, () -> {
            Double.parseDouble("abc");
        });
    }
}