package com.apps.quantitymeasurement.units;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.quantity.Quantity;

public class VolumeQuantityTest {

    private static final double EPS = 1e-4;

    // ---------------- EQUALITY ----------------

    @Test
    void testEquality_LitreToLitre_SameValue() {
        assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE)
           .equals(new Quantity<>(1.0, VolumeUnit.LITRE)));
    }

    @Test
    void testEquality_LitreToLitre_DifferentValue() {
        assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE)
            .equals(new Quantity<>(2.0, VolumeUnit.LITRE)));
    }

    @Test
    void testEquality_LitreToMillilitre() {
        assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE)
           .equals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    void testEquality_MillilitreToLitre() {
        assertTrue(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
           .equals(new Quantity<>(1.0, VolumeUnit.LITRE)));
    }

    @Test
    void testEquality_LitreToGallon() {
        assertTrue(new Quantity<>(3.78541, VolumeUnit.LITRE)
           .equals(new Quantity<>(1.0, VolumeUnit.GALLON)));
    }

    @Test
    void testEquality_GallonToLitre() {
        assertTrue(new Quantity<>(1.0, VolumeUnit.GALLON)
           .equals(new Quantity<>(3.78541, VolumeUnit.LITRE)));
    }

    @Test
    void testEquality_NullComparison() {
        assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE).equals(null));
    }

    @Test
    void testEquality_SameReference() {
        Quantity<VolumeUnit> q = new Quantity<>(1.0, VolumeUnit.LITRE);
        
        assertTrue(q.equals(q));
    }

    // ---------------- CONVERSION ----------------

    @Test
    void testConversion_LitreToMillilitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
                                          .convertTo(VolumeUnit.MILLILITRE);

        assertEquals(1000.0, result.getValue(), EPS);
    }

    @Test
    void testConversion_MillilitreToLitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1000.0, VolumeUnit.MILLILITRE).convertTo(VolumeUnit.LITRE);

        assertEquals(1.0, result.getValue(), EPS);
    }

    @Test
    void testConversion_GallonToLitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.GALLON).convertTo(VolumeUnit.LITRE);

        assertEquals(3.78541, result.getValue(), EPS);
    }

    
    @Test
    void testConversion_LitreToGallon() {
        Quantity<VolumeUnit> result = new Quantity<>(3.78541, VolumeUnit.LITRE).convertTo(VolumeUnit.GALLON);

        assertEquals(1.0, result.getValue(), EPS);
    }

    // ---------------- ADDITION ----------------

    @Test
    void testAddition_SameUnit() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
                                 .add(new Quantity<>(2.0, VolumeUnit.LITRE));

        assertEquals(3.0, result.getValue(), EPS);
    }

    @Test
    void testAddition_CrossUnit_LitrePlusMillilitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
                                 .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));

        assertEquals(2.0, result.getValue(), EPS);
    }

    @Test
    void testAddition_CrossUnit_GallonPlusLitre() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.GALLON)
                                 .add(new Quantity<>(3.78541, VolumeUnit.LITRE));

        assertEquals(2.0, result.getValue(), EPS);
    }

    @Test
    void testAddition_ExplicitTargetUnit() {
        Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
                                 .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.MILLILITRE);

        assertEquals(2000.0, result.getValue(), EPS);
    }

    // ---------------- CATEGORY SAFETY ----------------

    @Test
    void testVolumeVsLength_Incompatible() {
        Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);

        assertFalse(volume.equals(length));
    }

    @Test
    void testVolumeVsWeight_Incompatible() {
        Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertFalse(volume.equals(weight));
    }

    // ---------------- EDGE CASES ----------------

    @Test
    void testZeroValue() {
        assertTrue(new Quantity<>(0.0, VolumeUnit.LITRE)
           .equals(new Quantity<>(0.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    void testNegativeValue() {
        assertTrue(new Quantity<>(-1.0, VolumeUnit.LITRE)
           .equals(new Quantity<>(-1000.0, VolumeUnit.MILLILITRE)));
    }

    @Test
    void testLargeValue() {
        assertTrue(new Quantity<>(1000000.0, VolumeUnit.MILLILITRE)
           .equals(new Quantity<>(1000.0, VolumeUnit.LITRE)));
    }

    // ---------------- HASHCODE CONSISTENCY ----------------

    @Test
    void testHashCodeConsistency() {
        Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> q2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        assertEquals(q1.hashCode(), q2.hashCode());
    }
}