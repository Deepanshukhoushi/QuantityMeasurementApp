package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QuantityVolumeTest {

    private static final double EPSILON = 1e-6;

    // =====================================================
    // EQUALITY TESTS
    // =====================================================

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
    void testEquality_LitreToMillilitre_EquivalentValue() {
        assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE)
                .equals(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)));
    }
    
    @Test 
    void testEquality_MillilitreToLitre_EquivalentValue() {
    	assertTrue(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
    			.equals(new Quantity<>(1.0, VolumeUnit.LITRE)));
    }
    
    @Test
    void testEquality_LitreToGallon_EquivalentValue() {
        assertTrue(new Quantity<>(1.0, VolumeUnit.LITRE)
                .equals(new Quantity<>(0.264172, VolumeUnit.GALLON)));
    }
    
    @Test 
    void testEquality_GallonToLitre_EquivalentValue(){
    	assertTrue(new Quantity<>(1.0, VolumeUnit.GALLON)
    			.equals(new Quantity<>(3.78541, VolumeUnit.LITRE)));
    }
    
    @Test
    void testEquality_VolumeVsLength_Incompatible() {
        Quantity<VolumeUnit> volume =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<LengthUnit> length =
                new Quantity<>(1.0, LengthUnit.FEET);

        assertFalse(volume.equals(length));
    }

    @Test
    void testEquality_NullComparison() {
        assertFalse(new Quantity<>(1.0, VolumeUnit.LITRE)
                .equals(null));
    }

    @Test
    void testEquality_SameReference() {
        Quantity<VolumeUnit> volume =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        assertTrue(volume.equals(volume));
    }

    // =====================================================
    // CONVERSION TESTS
    // =====================================================

    @Test
    void testConversion_LitreToMillilitre() {
        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .convertTo(VolumeUnit.MILLILITRE);

        assertEquals(1000.0, result.getValue(), EPSILON);
    }

    @Test
    void testConversion_GallonToLitre() {
        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.GALLON)
                        .convertTo(VolumeUnit.LITRE);

        assertEquals(3.78541, result.getValue(), EPSILON);
    }

    @Test
    void testConversion_RoundTrip() {
        Quantity<VolumeUnit> result =
                new Quantity<>(1.5, VolumeUnit.LITRE)
                        .convertTo(VolumeUnit.MILLILITRE)
                        .convertTo(VolumeUnit.LITRE);

        assertEquals(1.5, result.getValue(), EPSILON);
    }

    // =====================================================
    // ADDITION TESTS
    // =====================================================

    @Test
    void testAddition_SameUnit_LitrePlusLitre() {
        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .add(new Quantity<>(2.0, VolumeUnit.LITRE));

        assertEquals(3.0, result.getValue(), EPSILON);
    }
    
    @Test 
    void testAddition_SameUnit_MillilitrePlusMillilitre() {
    	Quantity<VolumeUnit> result = 
    			new Quantity<>(500.0, VolumeUnit.MILLILITRE)
    			.add(new Quantity<>(500.0, VolumeUnit.MILLILITRE));
    	
    	assertEquals(1000.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_CrossUnit_LitrePlusMillilitre() {
        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));

        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test 
    void testAddition_CrossUnit_MillilitrePlusLitre() {
    	Quantity<VolumeUnit> result = 
    			new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
    			.add(new Quantity<>(1.0, VolumeUnit.LITRE));
    	assertEquals(2000.0, result.getValue(), EPSILON);
    }
    
    @Test
    void testAddition_ExplicitTargetUnit() {
        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE),
                                VolumeUnit.MILLILITRE);

        assertEquals(2000.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_GallonPlusLitre() {
        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.GALLON)
                        .add(new Quantity<>(3.78541, VolumeUnit.LITRE));

        assertEquals(2.0, result.getValue(), EPSILON);
    }

    // =====================================================
    // ENUM TESTS
    // =====================================================

    @Test
    void testVolumeUnitEnum_LitreConstant() {
        assertEquals(1.0,
                VolumeUnit.LITRE.getConversionFactor(),
                EPSILON);
    }

    @Test
    void testConvertToBaseUnit_GallonToLitre() {
        assertEquals(3.78541,
                VolumeUnit.GALLON.convertToBaseUnit(1.0),
                EPSILON);
    }

    @Test
    void testConvertFromBaseUnit_LitreToMillilitre() {
        assertEquals(1000.0,
                VolumeUnit.MILLILITRE.convertFromBaseUnit(1.0),
                EPSILON);
    }
}