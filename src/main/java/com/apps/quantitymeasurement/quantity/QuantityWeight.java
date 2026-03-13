package com.apps.quantitymeasurement.quantity;

import java.util.Objects;

import com.apps.quantitymeasurement.units.WeightUnit;

public final class QuantityWeight {

    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value must be finite.");

        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null.");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public WeightUnit getUnit() {
        return unit;
    }

    private double toBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    // ---------------- CONVERSION ----------------

    public QuantityWeight convertTo(WeightUnit targetUnit) {

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null.");

        double base = toBaseUnit();
        double converted = targetUnit.convertFromBaseUnit(base);

        return new QuantityWeight(converted, targetUnit);
    }

    // ---------------- ADDITION ----------------

    public QuantityWeight add(QuantityWeight other) {
        return add(other, this.unit);
    }

    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {

        if (other == null)
            throw new IllegalArgumentException("Operand cannot be null.");

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null.");

        double sumBase = this.toBaseUnit() + other.toBaseUnit();
        double result = targetUnit.convertFromBaseUnit(sumBase);

        return new QuantityWeight(result, targetUnit);
    }

    // ---------------- EQUALITY ----------------

    private static final double EPS = 1e-4;

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        QuantityWeight other = (QuantityWeight) obj;

        double base1 = this.toBaseUnit();
        double base2 = other.toBaseUnit();

        return Math.abs(base1 - base2) <= EPS;
    }

    @Override
    public int hashCode() {
        long normalized = Math.round(toBaseUnit() / EPS);
        return Objects.hash(normalized);
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}