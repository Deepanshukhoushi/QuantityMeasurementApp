package com.app.quantitymeasurement.quantity;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

import com.app.quantitymeasurement.unit.IMeasurable;

public final class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;
    private static final double EPS = 1e-6;

    public Quantity(double value, U unit) {

        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value must be finite");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    // ================= EQUALITY =================

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (!(obj instanceof Quantity<?> other)) return false;

        if (!unit.getClass().equals(other.unit.getClass()))
            return false;

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return Math.abs(base1 - base2) < EPS;
    }

    @Override
    public int hashCode() {
        // Must align with epsilon comparison
        double base = unit.convertToBaseUnit(value);
        return Objects.hash(Math.round(base / EPS));
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", value, unit.getUnitName());
    }

    // ================= CONVERSION =================
    
    public Quantity<U> convertTo(U targetUnit) {

        validateCategory(targetUnit);

        double base = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(base);

        return new Quantity<>(converted, targetUnit);
    }

    // ================= ARITHMETIC =================

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {

        validateOperands(other, targetUnit, true, "ADD");

        double resultBase = performBaseArithmetic(other, Operation.ADD);
        return buildResult(resultBase, targetUnit);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

        validateOperands(other, targetUnit, true, "SUBTRACT");

        double resultBase = performBaseArithmetic(other, Operation.SUBTRACT);
        return buildResult(resultBase, targetUnit);
    }

    public double divide(Quantity<U> other) {

        validateOperands(other, null, false, "DIVIDE");

        return performBaseArithmetic(other, Operation.DIVIDE);
    }

    // ================= CENTRALIZED VALIDATION =================

    private void validateOperands(
            Quantity<U> other,
            U targetUnit,
            boolean requireTarget,
            String operation) {

        if (other == null)
            throw new IllegalArgumentException("Operand cannot be null");

        if (!unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException("Incompatible unit categories");

        if (!Double.isFinite(value) || !Double.isFinite(other.value))
            throw new IllegalArgumentException("Values must be finite");

        // 🔥 UC14 critical addition
        unit.validateOperationSupport(operation);

        if (requireTarget) {
            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            validateCategory(targetUnit);
        }
    }

    private void validateCategory(U unitCheck) {
        if (unitCheck == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        if (!unitCheck.getClass().equals(unit.getClass()))
            throw new IllegalArgumentException("Invalid target unit category");
    }

    // ================= CORE ARITHMETIC =================

    private double performBaseArithmetic(Quantity<U> other, Operation op) {

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return op.compute(base1, base2);
    }

    private Quantity<U> buildResult(double baseValue, U targetUnit) {

        double converted = targetUnit.convertFromBaseUnit(baseValue);
        return new Quantity<>(round(converted), targetUnit);
    }

    private double round(double val) {
        return Math.round(val * 100.0) / 100.0;
    }

    // ================= ENUM DISPATCH =================

    private enum Operation {

        ADD((a, b) -> a + b),

        SUBTRACT((a, b) -> a - b),

        DIVIDE((a, b) -> {
            if (b == 0.0)
                throw new ArithmeticException("Cannot divide by zero");
            return a / b;
        });

        private final DoubleBinaryOperator operator;

        Operation(DoubleBinaryOperator operator) {
            this.operator = operator;
        }

        public double compute(double a, double b) {
            return operator.applyAsDouble(a, b);
        }
    }
}
