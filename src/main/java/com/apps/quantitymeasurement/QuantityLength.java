package com.apps.quantitymeasurement;


import java.util.Objects;

public class QuantityLength {

    private static final double EPSILON = 0.0001;

    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {

        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }

        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    private double convertToFeet() {
        return unit.toFeet(value);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true; 
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false; 
        }

        QuantityLength other = (QuantityLength) obj;

        double thisInFeet = this.convertToFeet();
        double otherInFeet = other.convertToFeet();

        return Math.abs(thisInFeet - otherInFeet) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(convertToFeet());
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}