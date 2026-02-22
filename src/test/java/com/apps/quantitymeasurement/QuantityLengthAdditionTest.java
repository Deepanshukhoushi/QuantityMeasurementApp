package com.apps.quantitymeasurement;

import static com.apps.quantitymeasurement.LengthUnit.FEET;
import static com.apps.quantitymeasurement.LengthUnit.INCHES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class QuantityLengthAdditionTest {

    private static final double EPSILON = 1e-6;

    @Test
    void testAddition_SameUnit_FeetPlusFeet() {
        QuantityLength result =
                new QuantityLength(1.0, FEET)
                        .add(new QuantityLength(2.0, FEET));

        assertEquals(3.0, result.convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).convertTo(FEET).getValue(), EPSILON);
    }

    @Test
    void testAddition_CrossUnit_FeetPlusInches() {
        QuantityLength result =
                new QuantityLength(1.0, FEET)
                        .add(new QuantityLength(12.0, INCHES));

        assertEquals(2.0, result.convertTo(FEET).getValue(), EPSILON);
    }

    @Test
    void testAddition_Commutativity() {

        QuantityLength a =
                new QuantityLength(1.0, FEET);

        QuantityLength b =
                new QuantityLength(12.0, INCHES);

        QuantityLength result1 = a.add(b);
        QuantityLength result2 = b.add(a);

        assertTrue(result1.equals(result2));
    }

    @Test
    void testAddition_WithZero() {
        QuantityLength result =
                new QuantityLength(5.0, FEET)
                        .add(new QuantityLength(0.0, INCHES));

        assertEquals(5.0, result.convertTo(FEET).getValue(), EPSILON);
    }

    @Test
    void testAddition_NullSecondOperand() {
        QuantityLength first =
                new QuantityLength(1.0, FEET);

        assertThrows(IllegalArgumentException.class,
                () -> first.add(null));
    }
}