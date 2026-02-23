package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static com.apps.quantitymeasurement.LengthUnit.*;

class QuantityLengthTest {

    private static final double EPSILON = 1e-6;

    @Test
    void testEquality_CrossUnit() {

        QuantityLength a =
                new QuantityLength(1.0, FEET);

        QuantityLength b =
                new QuantityLength(12.0, INCHES);

        assertTrue(a.equals(b));
    }

    @Test
    void testConvertTo() {

        QuantityLength q =
                new QuantityLength(1.0, FEET);

        QuantityLength result =
                q.convertTo(INCHES);

        assertEquals(12.0, result.getValue(), EPSILON);
        assertEquals(INCHES, result.getUnit());
    }

    @Test
    void testAdd_ImplicitTarget() {

        QuantityLength result =
                new QuantityLength(1.0, FEET)
                        .add(new QuantityLength(12.0, INCHES));

        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(FEET, result.getUnit());
    }

    @Test
    void testAdd_ExplicitTarget() {

        QuantityLength result =
                new QuantityLength(1.0, FEET)
                        .add(new QuantityLength(12.0, INCHES), YARDS);

        assertEquals(0.6666667, result.getValue(), EPSILON);
        assertEquals(YARDS, result.getUnit());
    }

    @Test
    void testNullUnit_Throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuantityLength(1.0, null));
    }

    @Test
    void testInvalidValue_Throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuantityLength(Double.NaN, FEET));
    }

    @Test
    void testAdd_NullOperand_Throws() {

        QuantityLength first =
                new QuantityLength(1.0, FEET);

        assertThrows(IllegalArgumentException.class,
                () -> first.add(null));
    }

    @Test
    void testRoundTripConversion() {

        QuantityLength original =
                new QuantityLength(5.0, FEET);

        QuantityLength converted =
                original.convertTo(INCHES);

        QuantityLength back =
                converted.convertTo(FEET);

        assertEquals(original.getValue(),
                     back.getValue(),
                     EPSILON);
    }


    @Test
    void testAddition_Commutativity() {

        QuantityLength a =
                new QuantityLength(1.0, FEET);

        QuantityLength b =
                new QuantityLength(12.0, INCHES);

        QuantityLength r1 = a.add(b, INCHES);
        QuantityLength r2 = b.add(a, INCHES);

        assertTrue(r1.equals(r2));
    }
}