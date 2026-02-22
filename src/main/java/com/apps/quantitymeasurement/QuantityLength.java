package com.apps.quantitymeasurement;

import java.util.Objects;

/**
 * Immutable value object representing a length quantity.
 */
public final class QuantityLength {

    private static final double EPSILON = 1e-6;

    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        validate(value, unit);
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    private void validate(double value, LengthUnit unit) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite.");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null.");
        }
    }

    private double toBaseUnit() {
        return unit.toFeet(value);
    }

    // =====================================================
    // UC6 - Addition (Result in first operand's unit)
    // =====================================================

    public QuantityLength add(QuantityLength other) {
        return addInternal(other, this.unit);
    }

    // =====================================================
    // UC7 - Addition with Explicit Target Unit
    // =====================================================

    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
        return addInternal(other, targetUnit);
    }

    // =====================================================
    // Private Utility Method (DRY)
    // =====================================================

    private QuantityLength addInternal(QuantityLength other, LengthUnit targetUnit) {

        if (other == null) {
            throw new IllegalArgumentException("Second operand cannot be null.");
        }

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }

        double sumInFeet = this.toBaseUnit() + other.toBaseUnit();

        double resultValue = targetUnit.fromFeet(sumInFeet);

        return new QuantityLength(resultValue, targetUnit);
    }

    // =====================================================
    // Conversion API (UC5)
    // =====================================================

    public QuantityLength convertTo(LengthUnit targetUnit) {
        double convertedValue = convert(this.value, this.unit, targetUnit);
        return new QuantityLength(convertedValue, targetUnit);
    }

    public static double convert(double value,
                                 LengthUnit source,
                                 LengthUnit target) {

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value.");
        }

        if (source == null || target == null) {
            throw new IllegalArgumentException("Units cannot be null.");
        }

        if (source == target) {
            return value;
        }

        double valueInFeet = source.toFeet(value);
        return target.fromFeet(valueInFeet);
    }

    // =====================================================
    // Equality (UC3)
    // =====================================================

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityLength other = (QuantityLength) obj;

        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(toBaseUnit());
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}