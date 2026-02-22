package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static com.apps.quantitymeasurement.LengthUnit.*;

class QuantityLengthUC7Test {

    private static final double EPSILON = 1e-6;

    // ---------------------------------------------------
    // Explicit Target Unit Tests
    // ---------------------------------------------------

    @Test
    void testAddition_ExplicitTargetUnit_Feet() {

        QuantityLength result =
                new QuantityLength(1.0, FEET)
                        .add(new QuantityLength(12.0, INCHES), FEET);

        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(FEET, result.getUnit());
    }

    @Test
    void testAddition_ExplicitTargetUnit_Inches() {

        QuantityLength result =
                new QuantityLength(1.0, FEET)
                        .add(new QuantityLength(12.0, INCHES), INCHES);

        assertEquals(24.0, result.getValue(), EPSILON);
        assertEquals(INCHES, result.getUnit());
    }

    @Test
    void testAddition_ExplicitTargetUnit_Yards() {

        QuantityLength result =
                new QuantityLength(1.0, FEET)
                        .add(new QuantityLength(12.0, INCHES), YARDS);

        assertEquals(0.6666667, result.getValue(), 1e-6);
        assertEquals(YARDS, result.getUnit());
    }

    @Test
    void testAddition_ExplicitTargetUnit_Centimeters() {

        QuantityLength result =
                new QuantityLength(1.0, INCHES)
                        .add(new QuantityLength(1.0, INCHES), CENTIMETERS);

        assertEquals(5.08, result.getValue(), 1e-4);
        assertEquals(CENTIMETERS, result.getUnit());
    }

    // ---------------------------------------------------
    // Target Same As Operands
    // ---------------------------------------------------

    @Test
    void testAddition_TargetSameAsFirstOperand() {

        QuantityLength result =
                new QuantityLength(2.0, YARDS)
                        .add(new QuantityLength(3.0, FEET), YARDS);

        assertEquals(3.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_TargetSameAsSecondOperand() {

        QuantityLength result =
                new QuantityLength(2.0, YARDS)
                        .add(new QuantityLength(3.0, FEET), FEET);

        assertEquals(9.0, result.getValue(), EPSILON);
    }

    // ---------------------------------------------------
    // Commutativity
    // ---------------------------------------------------

    @Test
    void testAddition_Commutativity() {

        QuantityLength a = new QuantityLength(1.0, FEET);
        QuantityLength b = new QuantityLength(12.0, INCHES);

        QuantityLength result1 = a.add(b, YARDS);
        QuantityLength result2 = b.add(a, YARDS);

        assertTrue(result1.equals(result2));
    }

    // ---------------------------------------------------
    // Edge Cases
    // ---------------------------------------------------

    @Test
    void testAddition_WithZero() {

        QuantityLength result =
                new QuantityLength(5.0, FEET)
                        .add(new QuantityLength(0.0, INCHES), YARDS);

        assertEquals(1.6666667, result.getValue(), 1e-6);
    }

    @Test
    void testAddition_NegativeValues() {

        QuantityLength result =
                new QuantityLength(5.0, FEET)
                        .add(new QuantityLength(-2.0, FEET), INCHES);

        assertEquals(36.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_LargeToSmallScale() {

        QuantityLength result =
                new QuantityLength(1000.0, FEET)
                        .add(new QuantityLength(500.0, FEET), INCHES);

        assertEquals(18000.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_SmallToLargeScale() {

        QuantityLength result =
                new QuantityLength(12.0, INCHES)
                        .add(new QuantityLength(12.0, INCHES), YARDS);

        assertEquals(0.6666667, result.getValue(), 1e-6);
    }

    // ---------------------------------------------------
    // Validation Tests
    // ---------------------------------------------------

    @Test
    void testAddition_NullTargetUnit() {

        QuantityLength first = new QuantityLength(1.0, FEET);
        QuantityLength second = new QuantityLength(12.0, INCHES);

        assertThrows(IllegalArgumentException.class,
                () -> first.add(second, null));
    }

    @Test
    void testAddition_NullSecondOperand() {

        QuantityLength first = new QuantityLength(1.0, FEET);

        assertThrows(IllegalArgumentException.class,
                () -> first.add(null, FEET));
    }
}