package com.app.quantitymeasurement.unit;

public enum LengthUnit implements IMeasurable {

	/*
	 * FEET(1.0), // base unit = feet INCHES(1.0 / 12.0), YARDS(3.0),
	 * CENTIMETERS(1.0 / 30.48);
	 * 
	 * private final double conversionFactor;
	 * 
	 * LengthUnit(double conversionFactor) { this.conversionFactor =
	 * conversionFactor; }
	 * 
	 * public double getConversionFactor() { return conversionFactor; }
	 * 
	 * // Convert value in this unit to base unit (feet) public double
	 * convertToBaseUnit(double value) { return value * conversionFactor; }
	 * 
	 * // Convert base unit (feet) value to this unit public double
	 * convertFromBaseUnit(double baseValue) { return baseValue / conversionFactor;
	 * }
	 */
	
	FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
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