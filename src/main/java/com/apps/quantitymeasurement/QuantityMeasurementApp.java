package com.apps.quantitymeasurement;

import java.util.Scanner;

public class QuantityMeasurementApp {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter first value(Yards): ");
        double value1 = input.nextDouble();

        System.out.println("Enter second value(Feet): ");
        double value2 = input.nextDouble();
        
        QuantityLength q1 =
                new QuantityLength(value1, LengthUnit.YARDS);

        QuantityLength q2 =
                new QuantityLength(value2, LengthUnit.FEET);

        System.out.println("Input: " + q1 + " and " + q2);
        System.out.println("Output: Equal (" + q1.equals(q2) + ")");

        System.out.println();
        
        System.out.println("Enter first value(CM): ");
        double value3 = input.nextDouble();
        System.out.println("Enter first value(INCH): ");
        double value4 = input.nextDouble();
        
        QuantityLength q3 =
                new QuantityLength(value3, LengthUnit.CENTIMETERS);

        QuantityLength q4 =
                new QuantityLength(value4, LengthUnit.INCH);

        System.out.println("Input: " + q3 + " and " + q4);
        System.out.println("Output: Equal (" + q3.equals(q4) + ")");
        input.close();
    }
}