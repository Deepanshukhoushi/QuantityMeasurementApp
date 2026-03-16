package com.app.quantitymeasurement.unit;

public enum WeightUnit implements IMeasurable {

	/* base KiloGram
	 * KILOGRAM(1.0), GRAM(0.001), POUND(0.453592);
	 */
	
	KILOGRAM(1000.0),
    GRAM(1.0),           // base Gram
    POUND(453.592);
	
    private final double conversionFactor;

    WeightUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    @Override
    public double getConversionFactor() {
        return conversionFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return value * conversionFactor;
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / conversionFactor;
    }
    
    @Override
    public String getUnitName() {
        return name();
    }
}