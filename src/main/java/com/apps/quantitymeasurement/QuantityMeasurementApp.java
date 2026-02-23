package com.apps.quantitymeasurement;

import java.util.Scanner;

public class QuantityMeasurementApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("=== UC8 Quantity Measurement Application ===");
            System.out.println("1. Convert Length");
            System.out.println("2. Check Equality");
            System.out.println("3. Add Lengths (Result in First Unit)");
            System.out.println("4. Add Lengths (Explicit Target Unit)");
            System.out.println("5. Enum Base Conversion Demo");
            System.out.print("Choose option (1-5): ");

            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    performConversion(scanner);
                    break;

                case 2:
                    performEquality(scanner);
                    break;

                case 3:
                    performAdditionImplicit(scanner);
                    break;

                case 4:
                    performAdditionExplicit(scanner);
                    break;

                case 5:
                    performEnumBaseConversion(scanner);
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error occurred.");
        }
    }

    private static void performConversion(Scanner scanner) {

        System.out.print("Enter value: ");
        double value = scanner.nextDouble();

        System.out.print("Enter source unit (FEET, INCHES, YARDS, CENTIMETERS): ");
        LengthUnit source = LengthUnit.valueOf(scanner.next().toUpperCase());

        System.out.print("Enter target unit: ");
        LengthUnit target = LengthUnit.valueOf(scanner.next().toUpperCase());

        QuantityLength quantity = new QuantityLength(value, source);
        QuantityLength result = quantity.convertTo(target);

        System.out.println("Output: " + result);
    }


    private static void performEquality(Scanner scanner) {

        QuantityLength q1 = readQuantity(scanner, "first");
        QuantityLength q2 = readQuantity(scanner, "second");

        System.out.println("Output: " + q1.equals(q2));
    }


    private static void performAdditionImplicit(Scanner scanner) {

        QuantityLength q1 = readQuantity(scanner, "first");
        QuantityLength q2 = readQuantity(scanner, "second");

        QuantityLength result = q1.add(q2);

        System.out.println("Output: " + result);
    }


    private static void performAdditionExplicit(Scanner scanner) {

        QuantityLength q1 = readQuantity(scanner, "first");
        QuantityLength q2 = readQuantity(scanner, "second");

        System.out.print("Enter target unit: ");
        LengthUnit target =
                LengthUnit.valueOf(scanner.next().toUpperCase());

        QuantityLength result = q1.add(q2, target);

        System.out.println("Output: " + result);
    }

    // -----------------------------------------------------
    // Standalone Enum Conversion Demo (UC8)
    // -----------------------------------------------------

    private static void performEnumBaseConversion(Scanner scanner) {

        System.out.print("Enter value: ");
        double value = scanner.nextDouble();

        System.out.print("Enter unit: ");
        LengthUnit unit =
                LengthUnit.valueOf(scanner.next().toUpperCase());

        double baseValue = unit.convertToBaseUnit(value);

        System.out.println("Converted to base unit (FEET): " + baseValue);
    }

    private static QuantityLength readQuantity(Scanner scanner,
                                               String label) {

        System.out.print("Enter " + label + " value: ");
        double value = scanner.nextDouble();

        System.out.print("Enter " + label + " unit: ");
        LengthUnit unit =
                LengthUnit.valueOf(scanner.next().toUpperCase());

        return new QuantityLength(value, unit);
    }
}