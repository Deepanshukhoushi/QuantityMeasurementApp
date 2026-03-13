package com.apps.quantitymeasurement.units;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.quantity.Length;
import com.apps.quantitymeasurement.quantity.QuantityWeight;

public class WeightEquality {

	private static final double EPS = 1e-4;

	// ---------- SAME UNIT EQUALITY ----------

	@Test
	void testEquality_KgToKg() {
		assertTrue(new QuantityWeight(1.0, WeightUnit.KILOGRAM).equals(new QuantityWeight(1.0, WeightUnit.KILOGRAM)));
	}

	@Test
	void testEquality_GramToGram() {
		assertTrue(new QuantityWeight(100.0, WeightUnit.GRAM).equals(new QuantityWeight(100.0, WeightUnit.GRAM)));
	}

	@Test
	void testEquality_PoundToPound() {
		assertTrue(new QuantityWeight(2.0, WeightUnit.POUND).equals(new QuantityWeight(2.0, WeightUnit.POUND)));
	}

	// ---------- CROSS UNIT EQUALITY ----------

	@Test
	void testEquality_KgToGram() {
		assertTrue(new QuantityWeight(1.0, WeightUnit.KILOGRAM).equals(new QuantityWeight(1000.0, WeightUnit.GRAM)));
	}

	@Test
	void testEquality_KgToPound() {
		assertFalse(new QuantityWeight(1.0, WeightUnit.KILOGRAM).equals(new QuantityWeight(2.20462262, WeightUnit.POUND)));
	}

	@Test
	void testEquality_GramToPound() {
		assertTrue(new QuantityWeight(453.592, WeightUnit.GRAM).equals(new QuantityWeight(1.0, WeightUnit.POUND)));
	}

	// ---------- CONVERSION ----------

	@Test
	void testConversion_KgToGram() {
		QuantityWeight result = new QuantityWeight(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);

		assertEquals(1000.0, result.getValue(), EPS);
	}

	@Test
	void testConversion_PoundToKg() {
		QuantityWeight result = new QuantityWeight(2.20462, WeightUnit.POUND).convertTo(WeightUnit.KILOGRAM);

		assertEquals(1.0, result.getValue(), 1e-3);
	}

	// ---------- ADDITION ----------

	@Test
	void testAddition_SameUnit() {
		QuantityWeight result = new QuantityWeight(1.0, WeightUnit.KILOGRAM)
				.add(new QuantityWeight(2.0, WeightUnit.KILOGRAM));

		assertEquals(3.0, result.getValue(), EPS);
	}

	@Test
	void testAddition_CrossUnit() {
		QuantityWeight result = new QuantityWeight(1.0, WeightUnit.KILOGRAM)
				.add(new QuantityWeight(1000.0, WeightUnit.GRAM));

		assertEquals(2.0, result.getValue(), EPS);
	}

	@Test
	void testAddition_WithTargetUnit() {
		QuantityWeight result = new QuantityWeight(1.0, WeightUnit.KILOGRAM)
				.add(new QuantityWeight(1000.0, WeightUnit.GRAM), WeightUnit.GRAM);

		assertEquals(2000.0, result.getValue(), EPS);
	}

	// ---------- ZERO / NEGATIVE ----------

	@Test
	void testZeroEquality() {
		assertTrue(new QuantityWeight(0.0, WeightUnit.KILOGRAM).equals(new QuantityWeight(0.0, WeightUnit.GRAM)));
	}

	@Test
	void testNegativeValue() {
		assertTrue(new QuantityWeight(-1.0, WeightUnit.KILOGRAM).equals(new QuantityWeight(-1000.0, WeightUnit.GRAM)));
	}

	// ---------- CATEGORY SAFETY ----------

	@Test
	void testWeightVsLength_NotEqual() {
		QuantityWeight weight = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
		Length length = new Length(1.0, LengthUnit.FEET);

		assertFalse(weight.equals(length));
	}

	// ---------- NULL & REFLEXIVE ----------

	@Test
	void testNullComparison() {
		assertFalse(new QuantityWeight(1.0, WeightUnit.KILOGRAM).equals(null));
	}

	@Test
	void testSameReference() {
		QuantityWeight w = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
		assertTrue(w.equals(w));
	}
}