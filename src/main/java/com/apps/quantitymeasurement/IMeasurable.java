package com.apps.quantitymeasurement;

@FunctionalInterface
interface SupportsArithmetic {
    boolean isSupported();
}

public interface IMeasurable {

    double getConversionFactor();

    double convertToBaseUnit(double value);

    double convertFromBaseUnit(double baseValue);

    String getUnitName();

    SupportsArithmetic ARITHMETIC_SUPPORT = () -> true;

    default boolean supportsArithmetic() {
        return ARITHMETIC_SUPPORT.isSupported();
    }

    default void validateOperationSupport(String operation) {
    }
}