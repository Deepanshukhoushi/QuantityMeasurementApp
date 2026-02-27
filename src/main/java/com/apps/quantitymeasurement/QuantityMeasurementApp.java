package com.apps.quantitymeasurement;

import java.util.Scanner;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {

            System.out.println("=== UC14 Generic Quantity Measurement App ===");
            System.out.println("1. Length Operations (FEET, INCHES, YARDS, CENTIMETERS)");
            System.out.println("2. Weight Operations (KILOGRAM, GRAM, POUND)");
            System.out.println("3. Volume Operations (LITRE, MILLILITRE, GALLON)");
            System.out.println("4. Temperature Operations (CELSIUS, FAHRENHEIT, KELVIN)");
            System.out.print("Choose category (1-4): ");

            int category = scanner.nextInt();

            switch (category) {
                case 1 -> handleCategory(scanner, LengthUnit.class);
                case 2 -> handleCategory(scanner, WeightUnit.class);
                case 3 -> handleCategory(scanner, VolumeUnit.class);
                case 4 -> handleCategory(scanner, TemperatureUnit.class);
                default -> System.out.println("Invalid category.");
            }

        } catch (UnsupportedOperationException e) {
            System.out.println("Unsupported Operation: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // =====================================================
    // GENERIC CATEGORY HANDLER
    // =====================================================

    private static <U extends Enum<U> & IMeasurable>
    void handleCategory(Scanner scanner, Class<U> unitType) {

        System.out.println("\n--- Operations ---");
        System.out.println("1. Convert");
        System.out.println("2. Equality");
        System.out.println("3. Add (First Unit)");
        System.out.println("4. Add (Target Unit)");
        System.out.println("5. Add Multiple Quantities");
        System.out.println("6. Subtract (First Unit)");
        System.out.println("7. Subtract (Target Unit)");
        System.out.println("8. Divide (Ratio)");
        System.out.print("Choose option (1-8): ");

        int choice = scanner.nextInt();

        try {
            switch (choice) {
                case 1 -> performConversion(scanner, unitType);
                case 2 -> performEquality(scanner, unitType);
                case 3 -> performAdditionImplicit(scanner, unitType);
                case 4 -> performAdditionExplicit(scanner, unitType);
                case 5 -> performMultipleAddition(scanner, unitType);
                case 6 -> performSubtractionImplicit(scanner, unitType);
                case 7 -> performSubtractionExplicit(scanner, unitType);
                case 8 -> performDivision(scanner, unitType);
                default -> System.out.println("Invalid choice.");
            }
        } catch (UnsupportedOperationException e) {
            System.out.println("Unsupported Operation: " + e.getMessage());
        }
    }

    // =====================================================
    // OPERATIONS
    // =====================================================

    private static <U extends Enum<U> & IMeasurable>
    void performConversion(Scanner scanner, Class<U> unitType) {

        printAvailableUnits(unitType);

        System.out.print("Enter value: ");
        double value = scanner.nextDouble();

        System.out.print("Convert FROM: ");
        U source = Enum.valueOf(unitType, scanner.next().toUpperCase());

        System.out.print("Convert TO: ");
        U target = Enum.valueOf(unitType, scanner.next().toUpperCase());

        Quantity<U> quantity = new Quantity<>(value, source);

        System.out.println("Output: " + quantity.convertTo(target));
    }

    private static <U extends Enum<U> & IMeasurable>
    void performEquality(Scanner scanner, Class<U> unitType) {

        Quantity<U> q1 = readQuantity(scanner, "first", unitType);
        Quantity<U> q2 = readQuantity(scanner, "second", unitType);

        System.out.println("Output: " + q1.equals(q2));
    }

    private static <U extends Enum<U> & IMeasurable>
    void performAdditionImplicit(Scanner scanner, Class<U> unitType) {

        Quantity<U> q1 = readQuantity(scanner, "first", unitType);
        Quantity<U> q2 = readQuantity(scanner, "second", unitType);

        System.out.println("Output: " + q1.add(q2));
    }

    private static <U extends Enum<U> & IMeasurable>
    void performAdditionExplicit(Scanner scanner, Class<U> unitType) {

        Quantity<U> q1 = readQuantity(scanner, "first", unitType);
        Quantity<U> q2 = readQuantity(scanner, "second", unitType);

        System.out.print("Enter target unit: ");
        U target = Enum.valueOf(unitType, scanner.next().toUpperCase());

        System.out.println("Output: " + q1.add(q2, target));
    }

    private static <U extends Enum<U> & IMeasurable>
    void performMultipleAddition(Scanner scanner, Class<U> unitType) {

        System.out.print("How many quantities to add? ");
        int count = scanner.nextInt();

        if (count < 2) {
            System.out.println("Need at least 2 quantities.");
            return;
        }

        Quantity<U> result = null;

        for (int i = 1; i <= count; i++) {

            Quantity<U> quantity =
                    readQuantity(scanner, "quantity " + i, unitType);

            if (result == null) {
                result = quantity;
            } else {
                result = result.add(quantity);
            }
        }

        System.out.print("Enter target unit: ");
        U target = Enum.valueOf(unitType, scanner.next().toUpperCase());

        System.out.println("Final Output: " + result.convertTo(target));
    }

    private static <U extends Enum<U> & IMeasurable>
    void performSubtractionImplicit(Scanner scanner, Class<U> unitType) {

        Quantity<U> q1 = readQuantity(scanner, "first", unitType);
        Quantity<U> q2 = readQuantity(scanner, "second", unitType);

        System.out.println("Output: " + q1.subtract(q2));
    }

    private static <U extends Enum<U> & IMeasurable>
    void performSubtractionExplicit(Scanner scanner, Class<U> unitType) {

        Quantity<U> q1 = readQuantity(scanner, "first", unitType);
        Quantity<U> q2 = readQuantity(scanner, "second", unitType);

        System.out.print("Enter target unit: ");
        U target = Enum.valueOf(unitType, scanner.next().toUpperCase());

        System.out.println("Output: " + q1.subtract(q2, target));
    }

    private static <U extends Enum<U> & IMeasurable>
    void performDivision(Scanner scanner, Class<U> unitType) {

        Quantity<U> q1 = readQuantity(scanner, "first", unitType);
        Quantity<U> q2 = readQuantity(scanner, "second", unitType);

        System.out.println("Output (Ratio): " + q1.divide(q2));
    }

    // =====================================================

    private static <U extends Enum<U>>
    void printAvailableUnits(Class<U> unitType) {

        System.out.println("\nAvailable Units:");

        for (U unit : unitType.getEnumConstants()) {
            System.out.println("- " + unit.name());
        }

        System.out.println();
    }

    private static <U extends Enum<U> & IMeasurable>
    Quantity<U> readQuantity(Scanner scanner,
                             String label,
                             Class<U> unitType) {

        System.out.print("Enter " + label + " value: ");
        double value = scanner.nextDouble();

        System.out.print("Enter " + label + " unit: ");
        U unit = Enum.valueOf(unitType, scanner.next().toUpperCase());

        return new Quantity<>(value, unit);
    }
}