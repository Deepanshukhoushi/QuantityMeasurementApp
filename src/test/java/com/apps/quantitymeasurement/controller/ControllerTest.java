package com.apps.quantitymeasurement.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;

class ControllerTest {

    @Test
    void shouldCreateControllerInstance() {

        QuantityMeasurementServiceImpl service =
                new QuantityMeasurementServiceImpl();

        QuantityMeasurementController controller =
                new QuantityMeasurementController(service);

        assertNotNull(controller);
    }
}