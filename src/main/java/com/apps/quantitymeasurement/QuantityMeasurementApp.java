package com.apps.quantitymeasurement;

import java.util.Scanner;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {

            System.out.println("=== LENGTH CONVERSION ===");

            System.out.print("Enter value: ");
            double value = scanner.nextDouble();

            System.out.print("Enter source unit (FEET, INCHES, YARDS, CENTIMETERS): ");
            LengthUnit source = LengthUnit.valueOf(scanner.next().toUpperCase());

            System.out.print("Enter target unit (FEET, INCHES, YARDS, CENTIMETERS): ");
            LengthUnit target = LengthUnit.valueOf(scanner.next().toUpperCase());

            double result = QuantityLength.convert(value, source, target);

            System.out.println("Result: " + result + " " + target);

            System.out.println("\n=== LENGTH EQUALITY CHECK ===");

            System.out.print("Enter first value: ");
            double v1 = scanner.nextDouble();

            System.out.print("Enter first unit: ");
            LengthUnit u1 = LengthUnit.valueOf(scanner.next().toUpperCase());

            System.out.print("Enter second value: ");
            double v2 = scanner.nextDouble();

            System.out.print("Enter second unit: ");
            LengthUnit u2 = LengthUnit.valueOf(scanner.next().toUpperCase());

            QuantityLength q1 = new QuantityLength(v1, u1);
            QuantityLength q2 = new QuantityLength(v2, u2);

            System.out.println("Are they equal? " + q1.equals(q2));

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error occurred.");
        } finally {
            scanner.close();
        }
    }
}