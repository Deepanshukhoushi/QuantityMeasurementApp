package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static com.apps.quantitymeasurement.WeightUnit.*;

class QuantityWeightTest {

    private static final double EPSILON = 1e-6;


    @Test
    void testEquality_KgToGram() {
        QuantityWeight a = new QuantityWeight(1.0, KILOGRAM);
        QuantityWeight b = new QuantityWeight(1000.0, GRAM);
        assertTrue(a.equals(b));
    }

    @Test
    void testEquality_KgToPound() {
        QuantityWeight a = new QuantityWeight(1.0, KILOGRAM);
        QuantityWeight b = new QuantityWeight(2.20462, POUND);
        assertTrue(a.equals(b));
    }

    @Test
    void testEquality_Different() {
        assertFalse(
            new QuantityWeight(1.0, KILOGRAM)
                .equals(new QuantityWeight(2.0, KILOGRAM))
        );
    }

    @Test
    void testEquality_Null() {
        assertFalse(new QuantityWeight(1.0, KILOGRAM).equals(null));
    }


    @Test
    void testConversion_KgToGram() {
        QuantityWeight result =
                new QuantityWeight(1.0, KILOGRAM)
                        .convertTo(GRAM);
        assertEquals(1000.0, result.getValue(), EPSILON);
    }

    @Test
    void testConversion_PoundToKg() {
        QuantityWeight result =
                new QuantityWeight(2.20462, POUND)
                        .convertTo(KILOGRAM);
        assertEquals(1.0, result.getValue(), 1e-4);
    }

    @Test
    void testRoundTripConversion() {
        QuantityWeight original =
                new QuantityWeight(1.5, KILOGRAM);

        QuantityWeight back =
                original.convertTo(GRAM)
                        .convertTo(KILOGRAM);

        assertEquals(1.5, back.getValue(), EPSILON);
    }


    @Test
    void testAddition_SameUnit() {
        QuantityWeight result =
                new QuantityWeight(1.0, KILOGRAM)
                        .add(new QuantityWeight(2.0, KILOGRAM));
        assertEquals(3.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_CrossUnit() {
        QuantityWeight result =
                new QuantityWeight(1.0, KILOGRAM)
                        .add(new QuantityWeight(1000.0, GRAM));
        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_ExplicitTarget() {
        QuantityWeight result =
                new QuantityWeight(1.0, KILOGRAM)
                        .add(new QuantityWeight(1000.0, GRAM), GRAM);
        assertEquals(2000.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_Commutativity() {
        QuantityWeight a =
                new QuantityWeight(1.0, KILOGRAM);
        QuantityWeight b =
                new QuantityWeight(1000.0, GRAM);

        assertTrue(
                a.add(b, KILOGRAM)
                 .equals(b.add(a, KILOGRAM))
        );
    }

    @Test
    void testNullUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuantityWeight(1.0, null));
    }

    @Test
    void testInvalidValue() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuantityWeight(Double.NaN, KILOGRAM));
    }
}