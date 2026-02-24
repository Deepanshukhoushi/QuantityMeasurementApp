package com.apps.quantitymeasurement;

import java.util.Objects;

public class Quantity<U extends IMeasurable> {

    private static final double EPSILON = 1e-6;

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {

        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    // =====================================================
    // CONVERSION
    // =====================================================

    public Quantity<U> convertTo(U targetUnit) {

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double baseValue = unit.convertToBaseUnit(value);
        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

        return new Quantity<>(convertedValue, targetUnit);
    }

    // =====================================================
    // ADDITION
    // =====================================================

    public Quantity<U> add(Quantity<U> other) {

        if (other == null) {
            throw new IllegalArgumentException("Other quantity cannot be null");
        }

        double totalBase = this.toBaseUnit() + other.toBaseUnit();
        double result = this.unit.convertFromBaseUnit(totalBase);

        return new Quantity<>(result, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {

        if (other == null) {
            throw new IllegalArgumentException("Other quantity cannot be null");
        }

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double totalBase = this.toBaseUnit() + other.toBaseUnit();
        double result = targetUnit.convertFromBaseUnit(totalBase);

        return new Quantity<>(result, targetUnit);
    }

    // =====================================================
    // EQUALITY
    // =====================================================

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (!(obj instanceof Quantity<?> other)) return false;

        // 🚨 Cross-category prevention
        if (!this.unit.getClass().equals(other.unit.getClass())) {
            return false;
        }

        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
    }

    private double toBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    // =====================================================
    // HASHCODE (Must match equals)
    // =====================================================

    @Override
    public int hashCode() {
        long rounded = Math.round(toBaseUnit() / EPSILON);
        return Objects.hash(rounded, unit.getClass());
    }

    // =====================================================
    // TOSTRING
    // =====================================================

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }
}