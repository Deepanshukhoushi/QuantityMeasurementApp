/**
 * QuantityMeasurementServiceImpl.java
 *
 * This class implements the IQuantityMeasurementService interface and contains the core
 * business logic for the application. It receives validated operational requests from the
 * controller (like compare or convert), gathers necessary secondary inputs from the user,
 * and performs mathematical conversions or operations across different unit types. Once
 * the operation is successfully executed or an error is encountered, this service
 * constructs a QuantityMeasurementEntity and delegates to the IQuantityMeasurementRepository
 * implementation for data persistence. It acts as the bridge coordinating domain calculations
 * and data storage.
 *
 * @author Developer
 * @version 16.0
 * @since 16.0
 */
package com.app.quantitymeasurement.service;

import java.util.Scanner;
import java.util.logging.Logger;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.quantity.Quantity;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.unit.IMeasurable;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private static final Logger logger =
            Logger.getLogger(QuantityMeasurementServiceImpl.class.getName());

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
        logger.info("QuantityMeasurementService initialized");
    }

    @Override
    public <U extends IMeasurable> void performOperation(
            Scanner sc,
            int operation,
            Quantity<U> q1,
            U[] units) {

        logger.info("Performing operation: " + operation);

        switch (operation) {

            case 1 -> compare(sc, q1, units);
            case 2 -> convert(sc, q1, units);
            case 3 -> add(sc, q1, units);
            case 4 -> subtract(sc, q1, units);
            case 5 -> divide(sc, q1, units);

            default -> logger.warning("Invalid operation selected");
        }
    }

    private <U extends IMeasurable> void compare(
            Scanner sc,
            Quantity<U> q1,
            U[] units) {

        Quantity<U> q2 = createQuantity(sc, units);

        boolean result = q1.equals(q2);

        logger.info("Comparison result: " + result);

        saveEntity(q1, q2, "COMPARE", String.valueOf(result), null, null);
    }

    private <U extends IMeasurable> void convert(
            Scanner sc,
            Quantity<U> q1,
            U[] units) {

        System.out.print("Convert to unit: ");
        int index = Integer.parseInt(sc.nextLine()) - 1;

        Quantity<U> result = q1.convertTo(units[index]);

        logger.info("Converted Result: " + result);

        saveEntity(q1, null, "CONVERT", result.toString(), result, null);
    }

    private <U extends IMeasurable> void add(
            Scanner sc,
            Quantity<U> q1,
            U[] units) {

        Quantity<U> q2 = createQuantity(sc, units);

        Quantity<U> result = q1.add(q2);

        logger.info("Addition Result: " + result);

        saveEntity(q1, q2, "ADD", result.toString(), result, null);
    }

    private <U extends IMeasurable> void subtract(
            Scanner sc,
            Quantity<U> q1,
            U[] units) {

        Quantity<U> q2 = createQuantity(sc, units);

        Quantity<U> result = q1.subtract(q2);

        logger.info("Subtraction Result: " + result);

        saveEntity(q1, q2, "SUBTRACT", result.toString(), result, null);
    }

    private <U extends IMeasurable> void divide(
            Scanner sc,
            Quantity<U> q1,
            U[] units) {

        Quantity<U> q2 = createQuantity(sc, units);

        double result = q1.divide(q2);

        logger.info("Division Result: " + result);

        saveEntity(q1, q2, "DIVIDE", String.valueOf(result), null, result);
    }

    private <U extends IMeasurable> Quantity<U> createQuantity(
            Scanner sc,
            U[] units) {

        System.out.print("Choose unit: ");
        int index = Integer.parseInt(sc.nextLine()) - 1;

        System.out.print("Enter value: ");
        double value = Double.parseDouble(sc.nextLine());

        return new Quantity<>(value, units[index]);
    }

    private <U extends IMeasurable> void saveEntity(
            Quantity<U> q1,
            Quantity<U> q2,
            String operation,
            String resultString,
            Quantity<U> resultQuantity,
            Double resultNumeric) {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setThisValue(q1.getValue());
        entity.setThisUnit(q1.getUnit().getUnitName());
        entity.setThisMeasurementType(getMeasurementType(q1.getUnit()));

        if (q2 != null) {
            entity.setThatValue(q2.getValue());
            entity.setThatUnit(q2.getUnit().getUnitName());
            entity.setThatMeasurementType(getMeasurementType(q2.getUnit()));
        }

        entity.setOperation(operation);
        entity.setResultString(resultString);

        // Populate structured result fields
        if (resultQuantity != null) {
            entity.setResultValue(resultQuantity.getValue());
            entity.setResultUnit(resultQuantity.getUnit().getUnitName());
            entity.setResultMeasurementType(getMeasurementType(resultQuantity.getUnit()));
        } else if (resultNumeric != null) {
            entity.setResultValue(resultNumeric);
            // resultUnit and resultMeasurementType remain null for dimensionless DIVIDE results
        }

        entity.setError(false);

        repository.save(entity);
    }

    /**
     * Derives the measurement category name from the unit enum class.
     * e.g. LengthUnit -> "LENGTH", WeightUnit -> "WEIGHT"
     */
    private <U extends IMeasurable> String getMeasurementType(U unit) {
        String className = unit.getClass().getSimpleName();
        return className.toUpperCase().replace("UNIT", "").trim();
    }
}