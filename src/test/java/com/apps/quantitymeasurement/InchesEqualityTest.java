package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class InchesTest {

    @Test
    void testEquality_SameValue() {
        Inches i1 = new Inches(10.0);
        Inches i2 = new Inches(10.0);

        assertTrue(i1.equals(i2));
    }

    @Test
    void testEquality_DifferentValue() {
        Inches i1 = new Inches(10.0);
        Inches i2 = new Inches(12.0);

        assertFalse(i1.equals(i2));
    }

    @Test
    void testEquality_SameReference() {
        Inches i1 = new Inches(5.0);

        assertTrue(i1.equals(i1));
    }

    @Test
    void testEquality_NullComparison() {
        Inches i1 = new Inches(8.0);

        assertFalse(i1.equals(null));
    }

    @SuppressWarnings("unlikely-arg-type")
	@Test
    void testEquality_DifferentType() {
        Inches i1 = new Inches(5.0);
        Feet feet = new Feet(5.0);

        assertFalse(i1.equals(feet));
    }

    @Test
    void testEquality_ZeroValue() {
        Inches i1 = new Inches(0.0);
        Inches i2 = new Inches(0.0);

        assertTrue(i1.equals(i2));
    }

    @Test
    void testEquality_NegativeValue() {
        Inches i1 = new Inches(-5.0);
        Inches i2 = new Inches(-5.0);

        assertTrue(i1.equals(i2));
    }
}