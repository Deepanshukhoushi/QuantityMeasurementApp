package com.apps.quantitymeasurement;

import java.util.Scanner;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("Enter first value: ");
        double value1 = input.nextDouble();

        System.out.println("Enter first unit (FEET/INCH): ");
        LengthUnit unit1 = LengthUnit.valueOf(input.next().toUpperCase());

        System.out.println("Enter second value: ");
        double value2 = input.nextDouble();

        System.out.println("Enter second unit (FEET/INCH): ");
        LengthUnit unit2 = LengthUnit.valueOf(input.next().toUpperCase());

        QuantityLength q1 = new QuantityLength(value1, unit1);
        QuantityLength q2 = new QuantityLength(value2, unit2);

        boolean result = q1.equals(q2);

        System.out.println("Input: " + q1 + " and " + q2);
        System.out.println("Output: Equal (" + result + ")");

        input.close();
    }
}