package com.apps.quantitymeasurement.service;

import java.util.Scanner;

import com.apps.quantitymeasurement.quantity.Quantity;
import com.apps.quantitymeasurement.units.IMeasurable;

public class QuantityMeasurementServiceImpl {

    public <U extends IMeasurable> void performOperation(
            Scanner sc,
            int operation,
            Quantity<U> q1,
            U[] units) {

        switch (operation) {

            case 1 -> compare(sc, q1, units);

            case 2 -> convert(sc, q1, units);

            case 3 -> add(sc, q1, units);

            case 4 -> subtract(sc, q1, units);

            case 5 -> divide(sc, q1, units);

            default -> System.out.println("Invalid operation");
        }
    }

    private <U extends IMeasurable> void compare(
            Scanner sc,
            Quantity<U> q1,
            U[] units) {

        Quantity<U> q2 = createQuantity(sc, units);

        System.out.println("Equal: " + q1.equals(q2));
    }

    private <U extends IMeasurable> void convert(
            Scanner sc,
            Quantity<U> q1,
            U[] units) {

        System.out.print("Convert to unit: ");
        int index = Integer.parseInt(sc.nextLine()) - 1;

        Quantity<U> result = q1.convertTo(units[index]);

        System.out.println("Converted Result: " + result);
    }

    private <U extends IMeasurable> void add(
            Scanner sc,
            Quantity<U> q1,
            U[] units) {

        Quantity<U> q2 = createQuantity(sc, units);

        Quantity<U> result = q1.add(q2);

        System.out.println("Addition Result: " + result);
    }

    private <U extends IMeasurable> void subtract(
            Scanner sc,
            Quantity<U> q1,
            U[] units) {

        Quantity<U> q2 = createQuantity(sc, units);

        Quantity<U> result = q1.subtract(q2);

        System.out.println("Subtraction Result: " + result);
    }

    private <U extends IMeasurable> void divide(
            Scanner sc,
            Quantity<U> q1,
            U[] units) {

        Quantity<U> q2 = createQuantity(sc, units);

        double result = q1.divide(q2);

        System.out.println("Division Result: " + result);
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