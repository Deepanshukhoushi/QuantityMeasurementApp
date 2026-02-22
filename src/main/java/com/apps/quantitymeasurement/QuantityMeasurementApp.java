package com.apps.quantitymeasurement;

import java.util.Scanner;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {

            System.out.println("=== LENGTH ADDITION WITH TARGET UNIT (UC7) ===");

            // First Length
            System.out.print("Enter first value: ");
            double value1 = scanner.nextDouble();

            System.out.print("Enter first unit (FEET, INCHES, YARDS, CENTIMETERS): ");
            LengthUnit unit1 = LengthUnit.valueOf(scanner.next().toUpperCase());

            // Second Length
            System.out.print("Enter second value: ");
            double value2 = scanner.nextDouble();

            System.out.print("Enter second unit (FEET, INCHES, YARDS, CENTIMETERS): ");
            LengthUnit unit2 = LengthUnit.valueOf(scanner.next().toUpperCase());

            // Target Unit
            System.out.print("Enter target unit for result (FEET, INCHES, YARDS, CENTIMETERS): ");
            LengthUnit targetUnit = LengthUnit.valueOf(scanner.next().toUpperCase());

            // Create objects
            QuantityLength length1 = new QuantityLength(value1, unit1);
            QuantityLength length2 = new QuantityLength(value2, unit2);

            // Perform addition
            QuantityLength result = length1.add(length2, targetUnit);

            System.out.println("\nInput: add(" + length1 + ", " + length2 + ", " + targetUnit + ")");
            System.out.println("Output: " + result);

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error occurred.");
        } finally {
            scanner.close();
        }
    }
}