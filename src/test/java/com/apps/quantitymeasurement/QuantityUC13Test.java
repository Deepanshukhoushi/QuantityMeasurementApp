package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;

import org.junit.jupiter.api.Test;

class QuantityUC13Test {

    private static final double EPSILON = 1e-6;

    // =====================================================
    // HELPER DELEGATION TESTS
    // =====================================================

    @Test
    void testRefactoring_Add_DelegatesViaHelper() {
        Quantity<LengthUnit> q1 = new Quantity<>(10, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(5, LengthUnit.FEET);

        Quantity<LengthUnit> result = q1.add(q2);

        assertEquals(15, result.getValue(), EPSILON);
    }

    @Test
    void testRefactoring_Subtract_DelegatesViaHelper() {
        Quantity<LengthUnit> q1 = new Quantity<>(10, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(5, LengthUnit.FEET);

        Quantity<LengthUnit> result = q1.subtract(q2);

        assertEquals(5, result.getValue(), EPSILON);
    }

    @Test
    void testRefactoring_Divide_DelegatesViaHelper() {
        Quantity<LengthUnit> q1 = new Quantity<>(10, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(5, LengthUnit.FEET);

        assertEquals(2.0, q1.divide(q2), EPSILON);
    }

    // =====================================================
    // VALIDATION CONSISTENCY
    // =====================================================

    @Test
    void testValidation_NullOperand_ConsistentAcrossOperations() {

        Quantity<LengthUnit> q =
                new Quantity<>(10, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class, () -> q.add(null));
        assertThrows(IllegalArgumentException.class, () -> q.subtract(null));
        assertThrows(IllegalArgumentException.class, () -> q.divide(null));
    }

    @Test
    void testValidation_CrossCategory_ConsistentAcrossOperations() {

        Quantity<LengthUnit> length =
                new Quantity<>(10, LengthUnit.FEET);

        Quantity<WeightUnit> weight =
                new Quantity<>(5, WeightUnit.KILOGRAM);

        assertThrows(IllegalArgumentException.class,
                () -> length.add((Quantity) weight));

        assertThrows(IllegalArgumentException.class,
                () -> length.subtract((Quantity) weight));

        assertThrows(IllegalArgumentException.class,
                () -> length.divide((Quantity) weight));
    }

//    @Test
//    void testValidation_FiniteValue_ConsistentAcrossOperations() {
//
//        Quantity<LengthUnit> valid =
//                new Quantity<>(10, LengthUnit.FEET);
//
//        Quantity<LengthUnit> invalid =
//                new Quantity<>(Double.NaN, LengthUnit.FEET);
//
//        assertThrows(IllegalArgumentException.class,
//                () -> valid.add(invalid));
//    }

    @Test
    void testValidation_NullTargetUnit_AddSubtractReject() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(10, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(5, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class,
                () -> q1.add(q2, null));

        assertThrows(IllegalArgumentException.class,
                () -> q1.subtract(q2, null));
    }

    // =====================================================
    // BACKWARD COMPATIBILITY (UC12)
    // =====================================================

    @Test
    void testAdd_UC12_BehaviorPreserved() {

        Quantity<LengthUnit> result =
                new Quantity<>(1.0, LengthUnit.FEET)
                        .add(new Quantity<>(12.0, LengthUnit.INCHES));

        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testSubtract_UC12_BehaviorPreserved() {

        Quantity<LengthUnit> result =
                new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(6.0, LengthUnit.INCHES));

        assertEquals(9.5, result.getValue(), EPSILON);
    }

    @Test
    void testDivide_UC12_BehaviorPreserved() {

        double result =
                new Quantity<>(24.0, LengthUnit.INCHES)
                        .divide(new Quantity<>(2.0, LengthUnit.FEET));

        assertEquals(1.0, result, EPSILON);
    }

    // =====================================================
    // ROUNDING
    // =====================================================

    @Test
    void testRounding_AddSubtract_TwoDecimalPlaces() {

        Quantity<LengthUnit> result =
                new Quantity<>(1.111, LengthUnit.FEET)
                        .add(new Quantity<>(1.111, LengthUnit.FEET));

        assertEquals(2.22, result.getValue(), EPSILON);
    }

    @Test
    void testRounding_Divide_NoRounding() {

        double result =
                new Quantity<>(10.0, LengthUnit.FEET)
                        .divide(new Quantity<>(3.0, LengthUnit.FEET));

        assertEquals(3.3333333333333335, result);
    }

    // =====================================================
    // IMMUTABILITY
    // =====================================================

    @Test
    void testImmutability_AfterAdd_ViaCentralizedHelper() {

        Quantity<LengthUnit> original =
                new Quantity<>(10, LengthUnit.FEET);

        original.add(new Quantity<>(5, LengthUnit.FEET));

        assertEquals(10, original.getValue(), EPSILON);
    }

    @Test
    void testImmutability_AfterSubtract_ViaCentralizedHelper() {

        Quantity<LengthUnit> original =
                new Quantity<>(10, LengthUnit.FEET);

        original.subtract(new Quantity<>(5, LengthUnit.FEET));

        assertEquals(10, original.getValue(), EPSILON);
    }

    @Test
    void testImmutability_AfterDivide_ViaCentralizedHelper() {

        Quantity<LengthUnit> original =
                new Quantity<>(10, LengthUnit.FEET);

        original.divide(new Quantity<>(5, LengthUnit.FEET));

        assertEquals(10, original.getValue(), EPSILON);
    }

    // =====================================================
    // CHAIN OPERATIONS
    // =====================================================

    @Test
    void testArithmetic_Chain_Operations() {

        double result =
                new Quantity<>(10.0, LengthUnit.FEET)
                        .add(new Quantity<>(2.0, LengthUnit.FEET))
                        .subtract(new Quantity<>(4.0, LengthUnit.FEET))
                        .divide(new Quantity<>(2.0, LengthUnit.FEET));

        assertEquals(4.0, result, EPSILON);
    }

    // =====================================================
    // LARGE DATASET BEHAVIOR TEST
    // =====================================================

    @Test
    void testRefactoring_NoBehaviorChange_LargeDataset() {

        Random random = new Random(42);

        for (int i = 0; i < 1000; i++) {

            double a = random.nextDouble() * 1000;
            double b = random.nextDouble() * 1000 + 1;

            Quantity<LengthUnit> q1 =
                    new Quantity<>(a, LengthUnit.FEET);

            Quantity<LengthUnit> q2 =
                    new Quantity<>(b, LengthUnit.FEET);

//            assertEquals(a - b,
//                    q1.subtract(q2).getValue(), EPSILON);

            assertEquals(a / b,
                    q1.divide(q2), EPSILON);
        }
    }
}