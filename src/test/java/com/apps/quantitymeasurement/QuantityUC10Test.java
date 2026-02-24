package com.apps.quantitymeasurement;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityUC10Test {

    private static final double EPSILON = 1e-6;

    // =====================================================
    // IMeasurable Interface Tests
    // =====================================================

    @Test
    void testIMeasurableInterface_LengthUnitImplementation() {
        assertTrue(LengthUnit.FEET instanceof IMeasurable);
        assertEquals(1.0, LengthUnit.FEET.getConversionFactor(), EPSILON);
    }

    @Test
    void testIMeasurableInterface_WeightUnitImplementation() {
        assertTrue(WeightUnit.KILOGRAM instanceof IMeasurable);
        assertEquals(1.0, WeightUnit.KILOGRAM.getConversionFactor(), EPSILON);
    }

    @Test
    void testIMeasurableInterface_ConsistentBehavior() {
        double feetToBase = LengthUnit.INCHES.convertToBaseUnit(12);
        assertEquals(1.0, feetToBase, EPSILON);

        double kgToBase = WeightUnit.GRAM.convertToBaseUnit(1000);
        assertEquals(1.0, kgToBase, EPSILON);
    }

    // =====================================================
    // Generic Equality Tests
    // =====================================================

    @Test
    void testGenericQuantity_LengthOperations_Equality() {
        Quantity<LengthUnit> a =
                new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> b =
                new Quantity<>(12.0, LengthUnit.INCHES);

        assertTrue(a.equals(b));
    }

    @Test
    void testGenericQuantity_WeightOperations_Equality() {
        Quantity<WeightUnit> a =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> b =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        assertTrue(a.equals(b));
    }

    // =====================================================
    // Conversion Tests
    // =====================================================

    @Test
    void testGenericQuantity_LengthOperations_Conversion() {
        Quantity<LengthUnit> q =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> result =
                q.convertTo(LengthUnit.INCHES);

        assertEquals(12.0, result.getValue(), EPSILON);
    }

    @Test
    void testGenericQuantity_WeightOperations_Conversion() {
        Quantity<WeightUnit> q =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> result =
                q.convertTo(WeightUnit.GRAM);

        assertEquals(1000.0, result.getValue(), EPSILON);
    }

    // =====================================================
    // Addition Tests
    // =====================================================

    @Test
    void testGenericQuantity_LengthOperations_Addition() {
        Quantity<LengthUnit> a =
                new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> b =
                new Quantity<>(12.0, LengthUnit.INCHES);

        Quantity<LengthUnit> result =
                a.add(b, LengthUnit.FEET);

        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testGenericQuantity_WeightOperations_Addition() {
        Quantity<WeightUnit> a =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> b =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        Quantity<WeightUnit> result =
                a.add(b, WeightUnit.KILOGRAM);

        assertEquals(2.0, result.getValue(), EPSILON);
    }

    // =====================================================
    // Cross Category Prevention
    // =====================================================

    @Test
    void testCrossCategoryPrevention_LengthVsWeight() {
        Quantity<LengthUnit> length =
                new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<WeightUnit> weight =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertFalse(length.equals(weight));
    }

    // =====================================================
    // Constructor Validation
    // =====================================================

    @Test
    void testGenericQuantity_ConstructorValidation_NullUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(1.0, null));
    }

    @Test
    void testGenericQuantity_ConstructorValidation_InvalidValue() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
    }

    // =====================================================
    // HashCode & Equals Contract
    // =====================================================

    @Test
    void testHashCode_GenericQuantity_Consistency() {
        Quantity<LengthUnit> a =
                new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> b =
                new Quantity<>(12.0, LengthUnit.INCHES);

        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testEquals_GenericQuantity_ContractPreservation() {

        Quantity<LengthUnit> a =
                new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> b =
                new Quantity<>(12.0, LengthUnit.INCHES);
        Quantity<LengthUnit> c =
                new Quantity<>(0.333333333, LengthUnit.YARDS);

        // Reflexive
        assertTrue(a.equals(a));

        // Symmetric
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));

        // Transitive
        assertTrue(a.equals(b));
        assertTrue(b.equals(c));
        assertTrue(a.equals(c));
    }

    // =====================================================
    // Scalability Test (New Category Example)
    // =====================================================

    enum VolumeUnit implements IMeasurable {
        LITER(1.0),
        MILLILITER(0.001);

        private final double factor;

        VolumeUnit(double factor) {
            this.factor = factor;
        }

        public double getConversionFactor() { return factor; }

        public double convertToBaseUnit(double value) {
            return value * factor;
        }

        public double convertFromBaseUnit(double baseValue) {
            return baseValue / factor;
        }

        public String getUnitName() {
            return name();
        }
    }

    @Test
    void testScalability_NewUnitEnumIntegration() {

        Quantity<VolumeUnit> a =
                new Quantity<>(1.0, VolumeUnit.LITER);
        Quantity<VolumeUnit> b =
                new Quantity<>(1000.0, VolumeUnit.MILLILITER);

        assertTrue(a.equals(b));
    }
}