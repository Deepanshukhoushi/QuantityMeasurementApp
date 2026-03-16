package com.app.quantitymeasurement.quantity;

import java.util.Objects;

import com.app.quantitymeasurement.unit.LengthUnit;

public final class Length {

    private final double value;
    private final LengthUnit unit;

    private static final double EPS = 1E-6;
    
    public Length(double value, LengthUnit unit) {

    	if (!Double.isFinite(value)) {
    		throw new IllegalArgumentException("Value must be finite");
    	}
    	
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        this.value = value;
        this.unit = unit;
    }

    // Getters
    
    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }
    
    // static conversion
    
    public static double convert(double value, LengthUnit source, LengthUnit target) {
    	if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite.");
        }

        if (source == null || target == null) {
            throw new IllegalArgumentException("Units must not be null.");
        }

        double base = source.convertToBaseUnit(value);
        return target.convertFromBaseUnit(base);
    }
    
    // instance conversion
    
    public Length convertTo(LengthUnit targetUnit) {

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit must not be null.");
        }

        double base = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(base);

        return new Length(converted, targetUnit);
    }
    
    // Private Add Method

    private static Length addInternal(Length l1, Length l2, LengthUnit targetUnit) {

        if (l1 == null || l2 == null) {
            throw new IllegalArgumentException("Operands cannot be null.");
        }

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }

        double base1 = l1.unit.convertToBaseUnit(l1.value);
        double base2 = l2.unit.convertToBaseUnit(l2.value);

        double sumBase = base1 + base2;

        double result = targetUnit.convertFromBaseUnit(sumBase);

        return new Length(result, targetUnit);
    }

    // Addition 

    public Length add(Length other) {
        return addInternal(this, other, this.unit);
    }

    // Addition Target Unit

    public Length add(Length other, LengthUnit targetUnit) {
        return addInternal(this, other, targetUnit);
    }
    
    // Equality
    
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Length other = (Length) obj;

        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return Math.abs(base1 - base2) < EPS;
    }

    @Override
    public int hashCode() {
        double base = unit.convertToBaseUnit(value);
        long normalized = Math.round(base / EPS);
        return Objects.hash(normalized);
    }
    
    @Override
    public String toString() {
    	return String.format("%.2f %", value, unit);
    }
}