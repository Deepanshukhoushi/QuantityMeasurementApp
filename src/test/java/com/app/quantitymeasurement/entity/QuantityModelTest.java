package com.app.quantitymeasurement.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.app.quantitymeasurement.unit.TemperatureUnit;

public class QuantityModelTest {

    @Test
    void testQuantityModelGetters() {
        QuantityModel<TemperatureUnit> model = new QuantityModel<>(100.0, TemperatureUnit.CELSIUS);
        
        assertEquals(100.0, model.getValue());
        assertEquals(TemperatureUnit.CELSIUS, model.getUnit());
    }
}
