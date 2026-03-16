package com.app.quantitymeasurement.quantity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.quantity.Quantity;
import com.app.quantitymeasurement.unit.LengthUnit;
import com.app.quantitymeasurement.unit.VolumeUnit;
import com.app.quantitymeasurement.unit.WeightUnit;

public class CentralizedArithmeticLogicTest {

    @Test
    void testEquality_SameUnit() {
        assertEquals(new Quantity<>(1.0, LengthUnit.FEET),
                     new Quantity<>(1.0, LengthUnit.FEET));
    }

    @Test
    void testEquality_CrossUnitEquivalent() {
        assertEquals(new Quantity<>(1.0, LengthUnit.FEET),
                     new Quantity<>(12.0, LengthUnit.INCHES));
    }

    @Test
    void testEquality_CrossCategoryFalse() {
        assertNotEquals(new Quantity<>(1.0, LengthUnit.FEET),
                        new Quantity<>(1.0, WeightUnit.KILOGRAM));
    }

    @Test
    void testEquality_Null() {
        Quantity<LengthUnit> q = new Quantity<>(1.0, LengthUnit.FEET);

        assertNotEquals(null, q);
    }

    @Test
    void testHashCodeConsistency() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCHES);

        assertEquals(q1.hashCode(), q2.hashCode());
    }

    // ================= CONVERSION =================

    @Test
    void testConversion_Length() {
        Quantity<LengthUnit> q = new Quantity<>(1.0, LengthUnit.FEET);

        assertEquals(new Quantity<>(12.0, LengthUnit.INCHES), q.convertTo(LengthUnit.INCHES));
    }

    @Test
    void testConversion_Weight() {
        Quantity<WeightUnit> q = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertEquals(new Quantity<>(1000.0, WeightUnit.GRAM), q.convertTo(WeightUnit.GRAM));
    }

    @Test
    void testConversion_Volume() {
        Quantity<VolumeUnit> q = new Quantity<>(1.0, VolumeUnit.LITRE);

        assertEquals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), q.convertTo(VolumeUnit.MILLILITRE));
    }

    @Test
    void testConversion_InvalidCategory() {
        Quantity<LengthUnit> q = new Quantity<>(1.0, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class, () -> q.convertTo((LengthUnit) null));
    }

    // ================= ADD =================

    @Test
    void testAdd_SameUnit() {
        Quantity<LengthUnit> q1 = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(3.0, LengthUnit.FEET);

        assertEquals(new Quantity<>(5.0, LengthUnit.FEET), q1.add(q2));
    }

    @Test
    void testAdd_CrossUnit() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCHES);

        assertEquals(new Quantity<>(2.0, LengthUnit.FEET), q1.add(q2));
    }

    @Test
    void testAdd_ExplicitTarget() {
        Quantity<WeightUnit> q1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> q2 = new Quantity<>(500.0, WeightUnit.GRAM);

        assertEquals(new Quantity<>(1500.0, WeightUnit.GRAM), q1.add(q2, WeightUnit.GRAM));
    }

    @Test
    void testAdd_NegativeValues() {
        Quantity<LengthUnit> q1 = new Quantity<>(5.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(-2.0, LengthUnit.FEET);

        assertEquals( new Quantity<>(3.0, LengthUnit.FEET), q1.add(q2));
    }

    @Test
    void testAdd_NullOperand() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class, () -> q1.add(null));
    }

    // ================= SUBTRACT =================

    @Test
    void testSubtract_SameUnit() {
        Quantity<LengthUnit> q1 = new Quantity<>(5.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(2.0, LengthUnit.FEET);

        assertEquals( new Quantity<>(3.0, LengthUnit.FEET), q1.subtract(q2));
    }

    @Test
    void testSubtract_CrossUnit() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(6.0, LengthUnit.INCHES);

        assertEquals(new Quantity<>(0.5, LengthUnit.FEET), q1.subtract(q2));
    }

    @Test
    void testSubtract_ResultNegative() {
        Quantity<LengthUnit> q1 = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(5.0, LengthUnit.FEET);

        assertEquals(new Quantity<>(-3.0, LengthUnit.FEET), q1.subtract(q2));
    }

    @Test
    void testSubtract_ExplicitTarget() {
        Quantity<VolumeUnit> q1 = new Quantity<>(5.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> q2 = new Quantity<>(2000.0, VolumeUnit.MILLILITRE);

        assertEquals(new Quantity<>(3000.0, VolumeUnit.MILLILITRE),
                            q1.subtract(q2, VolumeUnit.MILLILITRE));
    }

    @Test
    void testSubtract_NullOperand() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class, () -> q1.subtract(null));
    }

    // ================= DIVIDE =================

    @Test
    void testDivide_SameUnit() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(2.0, LengthUnit.FEET);

        assertEquals(5.0, q1.divide(q2));
    }

    @Test
    void testDivide_CrossUnit() {
        Quantity<LengthUnit> q1 = new Quantity<>(24.0, LengthUnit.INCHES);
        Quantity<LengthUnit> q2 = new Quantity<>(2.0, LengthUnit.FEET);

        assertEquals(1.0, q1.divide(q2));
    }

    @Test
    void testDivide_ResultLessThanOne() {
        Quantity<LengthUnit> q1 = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(4.0, LengthUnit.FEET);

        assertEquals(0.5, q1.divide(q2));
    }

    @Test
    void testDivide_ByZero() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(0.0, LengthUnit.FEET);

        assertThrows(ArithmeticException.class, () -> q1.divide(q2));
    }

    @Test
    void testDivide_NullOperand() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class, () -> q1.divide(null));
    }

    // ================= IMMUTABILITY =================

    @Test
    void testImmutability_Add() {
        Quantity<LengthUnit> q1 = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(3.0, LengthUnit.FEET);

        q1.add(q2);

        assertEquals(new Quantity<>(2.0, LengthUnit.FEET), q1);
    }

    @Test
    void testImmutability_Subtract() {
        Quantity<LengthUnit> q1 = new Quantity<>(5.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(2.0, LengthUnit.FEET);

        q1.subtract(q2);

        assertEquals(new Quantity<>(5.0, LengthUnit.FEET), q1);
    }

    @Test
    void testImmutability_Divide() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(2.0, LengthUnit.FEET);

        q1.divide(q2);

        assertEquals(new Quantity<>(10.0, LengthUnit.FEET), q1);
    }
}