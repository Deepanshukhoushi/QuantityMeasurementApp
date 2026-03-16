/**
 * QuantityMeasurementController.java
 *
 * This class serves as the command-line interface controller for the application.
 * It is responsible for presenting the user with operational choices (such as compare,
 * convert, add, subtract, and divide) across various measurement categories like
 * Length, Weight, Volume, and Temperature. The controller captures user input via
 * the standard input scanner, validates the selected options, instantiates the required
 * Quantity objects, and delegates the core business logic processing to the
 * IQuantityMeasurementService layer. It handles the input/output boundaries of the application.
 *
 * @author Developer
 * @version 16.0
 * @since 16.0
 */
package com.app.quantitymeasurement.controller;

import java.util.Scanner;
import java.util.logging.Logger;

import com.app.quantitymeasurement.quantity.Quantity;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.app.quantitymeasurement.unit.IMeasurable;
import com.app.quantitymeasurement.unit.LengthUnit;
import com.app.quantitymeasurement.unit.TemperatureUnit;
import com.app.quantitymeasurement.unit.VolumeUnit;
import com.app.quantitymeasurement.unit.WeightUnit;

public class QuantityMeasurementController {

    private static final Logger logger =
            Logger.getLogger(QuantityMeasurementController.class.getName());

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    public void startApplication() {

        Scanner sc = new Scanner(System.in);

        logger.info("Welcome to Quantity Measurement");

        printCategory();

        System.out.print("Enter option: ");
        int category = Integer.parseInt(sc.nextLine());

        printOperation();

        System.out.print("Enter option: ");
        int operation = Integer.parseInt(sc.nextLine());

        switch (category) {

            case 1 -> handleCategory(sc, operation, LengthUnit.values());
            case 2 -> handleCategory(sc, operation, WeightUnit.values());
            case 3 -> handleCategory(sc, operation, VolumeUnit.values());
            case 4 -> handleCategory(sc, operation, TemperatureUnit.values());

            default -> logger.warning("Invalid category");
        }

        logger.info("Thank you for using the application");

        sc.close();
    }

    private void printCategory() {

        logger.info("\nChoose Category:");
        logger.info("1 Length");
        logger.info("2 Weight");
        logger.info("3 Volume");
        logger.info("4 Temperature");
    }

    private void printOperation() {

        logger.info("\nChoose Operation:");
        logger.info("1 Compare");
        logger.info("2 Convert");
        logger.info("3 Add");
        logger.info("4 Subtract");
        logger.info("5 Divide");
    }

    private <U extends IMeasurable> void handleCategory(
            Scanner sc,
            int operation,
            U[] units) {

        printUnits(units);

        Quantity<U> q1 = createQuantity(sc, units);

        service.performOperation(sc, operation, q1, units);
    }

    private <U extends IMeasurable> void printUnits(U[] units) {

        System.out.println("\nChoose unit:");

        for (int i = 0; i < units.length; i++) {
            System.out.println((i + 1) + ". " + units[i].getUnitName());
        }
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
}