package com.app.quantitymeasurement.unit;

@FunctionalInterface
interface SupportsArithmetic {
    boolean isSupported();
}

public interface IMeasurable {

    // Conversion methods 
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);
    double getConversionFactor();
    String getUnitName();

    // Default arithmetic capability 
    SupportsArithmetic supportsArithmetic = () -> true;

    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    // Default validation 
    default void validateOperationSupport(String operation) {
    }
}