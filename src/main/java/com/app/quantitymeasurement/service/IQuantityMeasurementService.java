/**
 * IQuantityMeasurementService.java
 *
 * This interface defines the contract for the service layer in the Quantity Measurement
 * application. It provides the core business logic boundary for handling measurement operations
 * such as conversion, comparison, addition, subtraction, and division between different
 * physical quantities (e.g., Length, Weight, Volume, Temperature). Implementations of
 * this interface are responsible for validating the operations, calculating results,
 * and interacting with the repository layer to persist these measurement transactions.
 *
 * @author Developer
 * @version 16.0
 * @since 16.0
 */
package com.app.quantitymeasurement.service;

import java.util.Scanner;

import com.app.quantitymeasurement.quantity.Quantity;
import com.app.quantitymeasurement.unit.IMeasurable;

public interface IQuantityMeasurementService {

    <U extends IMeasurable> void performOperation(
            Scanner sc,
            int operation,
            Quantity<U> q1,
            U[] units);
}