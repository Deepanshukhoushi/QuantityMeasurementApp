package com.apps.quantitymeasurement;

import java.util.Scanner;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {

            System.out.println("=== UC10 Generic Quantity Measurement App ===");
            System.out.println("1. Length Operations");
            System.out.println("2. Weight Operations");
            System.out.print("Choose category (1-2): ");

            int category = scanner.nextInt();

            switch (category) {
                case 1 -> handleLength(scanner);
                case 2 -> handleWeight(scanner);
                default -> System.out.println("Invalid category.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static <U extends Enum<U> & IMeasurable>
    void performConversion(Scanner scanner, Class<U> unitType) {

        Quantity<U> quantity = readQuantity(scanner, "source", unitType);

        System.out.print("Enter target unit: ");
        U target = Enum.valueOf(unitType,
                scanner.next().toUpperCase());

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
        U target = Enum.valueOf(unitType,
                scanner.next().toUpperCase());

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

        System.out.print("Enter target unit for final result: ");
        U target = Enum.valueOf(unitType,
                scanner.next().toUpperCase());

        result = result.convertTo(target);

        System.out.println("Final Output: " + result);
    }

    private static <U extends Enum<U> & IMeasurable>
    Quantity<U> readQuantity(Scanner scanner,
                             String label,
                             Class<U> unitType) {

        System.out.print("Enter " + label + " value: ");
        double value = scanner.nextDouble();

        System.out.print("Enter " + label + " unit: ");
        U unit = Enum.valueOf(unitType,
                scanner.next().toUpperCase());

        return new Quantity<>(value, unit);
    }


    private static void handleLength(Scanner scanner) {

        System.out.println("\n--- Length Operations ---");
        System.out.println("1. Convert");
        System.out.println("2. Equality");
        System.out.println("3. Add (First Unit)");
        System.out.println("4. Add (Target Unit)");
        System.out.println("5. Add Multiple Quantities");
        System.out.print("Choose option (1-5): ");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> performConversion(scanner, LengthUnit.class);
            case 2 -> performEquality(scanner, LengthUnit.class);
            case 3 -> performAdditionImplicit(scanner, LengthUnit.class);
            case 4 -> performAdditionExplicit(scanner, LengthUnit.class);
            case 5 -> performMultipleAddition(scanner, LengthUnit.class);
            default -> System.out.println("Invalid choice.");
        }
    }


    private static void handleWeight(Scanner scanner) {

        System.out.println("\n--- Weight Operations ---");
        System.out.println("1. Convert");
        System.out.println("2. Equality");
        System.out.println("3. Add (First Unit)");
        System.out.println("4. Add (Target Unit)");
        System.out.println("5. Add Multiple Quantities");
        System.out.print("Choose option (1-5): ");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> performConversion(scanner, WeightUnit.class);
            case 2 -> performEquality(scanner, WeightUnit.class);
            case 3 -> performAdditionImplicit(scanner, WeightUnit.class);
            case 4 -> performAdditionExplicit(scanner, WeightUnit.class);
            case 5 -> performMultipleAddition(scanner, WeightUnit.class);
            default -> System.out.println("Invalid choice.");
        }
    }
}