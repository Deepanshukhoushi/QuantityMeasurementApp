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
        return unit.convertToBaseUnit(value);
    }

    // =====================================================
    // Conversion (UC5)
    // =====================================================

    public QuantityLength convertTo(LengthUnit targetUnit) {

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }

        double baseValue = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(baseValue);

        return new QuantityLength(converted, targetUnit);
    }

    // =====================================================
    // Addition (UC6 + UC7)
    // =====================================================

    public QuantityLength add(QuantityLength other) {
        return add(other, this.unit);
    }

    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {

        if (other == null) {
            throw new IllegalArgumentException("Second operand cannot be null.");
        }

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }

        double sumBase =
                this.unit.convertToBaseUnit(this.value) +
                other.unit.convertToBaseUnit(other.value);

        double result =
                targetUnit.convertFromBaseUnit(sumBase);

        return new QuantityLength(result, targetUnit);
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