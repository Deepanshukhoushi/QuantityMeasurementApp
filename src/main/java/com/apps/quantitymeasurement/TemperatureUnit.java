package com.apps.quantitymeasurement;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS(
            c -> c,                    
            c -> c                  
    ),

    FAHRENHEIT(
            f -> (f - 32) * 5 / 9,      
            c -> (c * 9 / 5) + 32      
    ),

    KELVIN(
            k -> k - 273.15,          
            c -> c + 273.15            
    );



    private final Function<Double, Double> toBaseConverter;
    private final Function<Double, Double> fromBaseConverter;

    private static final SupportsArithmetic ARITHMETIC_SUPPORT = () -> false;



    TemperatureUnit(Function<Double, Double> toBaseConverter,
                    Function<Double, Double> fromBaseConverter) {
        this.toBaseConverter = toBaseConverter;
        this.fromBaseConverter = fromBaseConverter;
    }

    @Override
    public double getConversionFactor() {
        return 1.0; 
    }

    @Override
    public double convertToBaseUnit(double value) {
        return toBaseConverter.apply(value);
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        return fromBaseConverter.apply(baseValue);
    }

    @Override
    public String getUnitName() {
        return this.name();
    }

    @Override
    public boolean supportsArithmetic() {
        return ARITHMETIC_SUPPORT.isSupported();
    }

    @Override
    public void validateOperationSupport(String operation) {
        throw new UnsupportedOperationException(
                "Operation '" + operation +
                "' is not supported for absolute temperature values. " +
                "Temperature supports only equality comparison and conversion."
        );
    }
}