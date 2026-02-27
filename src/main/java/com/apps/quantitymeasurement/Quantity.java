package com.apps.quantitymeasurement;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

public class Quantity<U extends IMeasurable> {

    private static final double EPSILON = 1e-6;

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {

        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid value");

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

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        if (!this.unit.getClass().equals(targetUnit.getClass()))
            throw new IllegalArgumentException("Different measurement categories");

        double base = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(base);

        return new Quantity<>(round(converted), targetUnit);
    }

    // =====================================================
    // CENTRALIZED ARITHMETIC ENUM (UC13)
    // =====================================================

    private enum ArithmeticOperation {

        ADD((a, b) -> a + b),

        SUBTRACT((a, b) -> a - b),

        DIVIDE((a, b) -> {
            if (Math.abs(b) < EPSILON)
                throw new ArithmeticException("Division by zero quantity");
            return a / b;
        });

        private final DoubleBinaryOperator operator;

        ArithmeticOperation(DoubleBinaryOperator operator) {
            this.operator = operator;
        }

        public double compute(double a, double b) {
            return operator.applyAsDouble(a, b);
        }
    }

    // =====================================================
    // ADDITION
    // =====================================================

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {

        validateArithmeticOperands(other, targetUnit, true);

        double baseResult =
                performBaseArithmetic(other, ArithmeticOperation.ADD);

        double converted =
                targetUnit.convertFromBaseUnit(baseResult);

        return new Quantity<>(round(converted), targetUnit);
    }

    // =====================================================
    // SUBTRACTION
    // =====================================================

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

        validateArithmeticOperands(other, targetUnit, true);

        double baseResult =
                performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);

        double converted =
                targetUnit.convertFromBaseUnit(baseResult);

        return new Quantity<>(round(converted), targetUnit);
    }

    // =====================================================
    // DIVISION (Dimensionless)
    // =====================================================

    public double divide(Quantity<U> other) {

        validateArithmeticOperands(other, null, false);

        return performBaseArithmetic(other,
                ArithmeticOperation.DIVIDE);
    }

    // =====================================================
    // CENTRALIZED VALIDATION (UPDATED FOR UC14)
    // =====================================================

    private void validateArithmeticOperands(
            Quantity<U> other,
            U targetUnit,
            boolean targetUnitRequired) {

        if (other == null)
            throw new IllegalArgumentException("Operand cannot be null");

        if (!this.unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException("Different measurement categories");

        if (!Double.isFinite(this.value) ||
            !Double.isFinite(other.value))
            throw new IllegalArgumentException("Values must be finite");

        if (targetUnitRequired && targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        // 🔥 UC14 ADDITION
        // Validate arithmetic capability BEFORE operation
        this.unit.validateOperationSupport("ARITHMETIC");
    }

    // =====================================================
    // CORE ARITHMETIC ENGINE
    // =====================================================

    private double performBaseArithmetic(
            Quantity<U> other,
            ArithmeticOperation operation) {

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return operation.compute(base1, base2);
    }

    // =====================================================
    // ROUNDING
    // =====================================================

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    // =====================================================
    // EQUALITY
    // =====================================================

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (!(obj instanceof Quantity<?> other)) return false;

        if (!this.unit.getClass().equals(other.unit.getClass()))
            return false;

        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
    }

    private double toBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    // =====================================================
    // HASHCODE
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