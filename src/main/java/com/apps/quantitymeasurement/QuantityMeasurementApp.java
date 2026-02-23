package com.apps.quantitymeasurement;

import java.util.Scanner;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {

            System.out.println("=== Quantity Measurement Application ===");
            System.out.println("1. Length Operations");
            System.out.println("2. Weight Operations");
            System.out.print("Choose category (1-2): ");

            int category = scanner.nextInt();

            switch (category) {

                case 1:
                    handleLength(scanner);
                    break;

                case 2:
                    handleWeight(scanner);
                    break;

                default:
                    System.out.println("Invalid category.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error occurred.");
        }
    }

    // =====================================================
    // LENGTH SECTION (UC1–UC8)
    // =====================================================

    private static void handleLength(Scanner scanner) {

        System.out.println("\n--- Length Operations ---");
        System.out.println("1. Convert Length");
        System.out.println("2. Check Equality");
        System.out.println("3. Add Lengths (Result in First Unit)");
        System.out.println("4. Add Lengths (Explicit Target Unit)");
        System.out.println("5. Enum Base Conversion Demo");
        System.out.print("Choose option (1-5): ");

        int choice = scanner.nextInt();

        switch (choice) {

            case 1:
                performLengthConversion(scanner);
                break;

            case 2:
                performLengthEquality(scanner);
                break;

            case 3:
                performLengthAdditionImplicit(scanner);
                break;

            case 4:
                performLengthAdditionExplicit(scanner);
                break;

            case 5:
                performLengthEnumBaseConversion(scanner);
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void performLengthConversion(Scanner scanner) {

        System.out.print("Enter value: ");
        double value = scanner.nextDouble();

        System.out.print("Enter source unit (FEET, INCHES, YARDS, CENTIMETERS): ");
        LengthUnit source = LengthUnit.valueOf(scanner.next().toUpperCase());

        System.out.print("Enter target unit: ");
        LengthUnit target = LengthUnit.valueOf(scanner.next().toUpperCase());

        QuantityLength quantity = new QuantityLength(value, source);
        System.out.println("Output: " + quantity.convertTo(target));
    }

    private static void performLengthEquality(Scanner scanner) {

        QuantityLength q1 = readLength(scanner, "first");
        QuantityLength q2 = readLength(scanner, "second");

        System.out.println("Output: " + q1.equals(q2));
    }

    private static void performLengthAdditionImplicit(Scanner scanner) {

        QuantityLength q1 = readLength(scanner, "first");
        QuantityLength q2 = readLength(scanner, "second");

        System.out.println("Output: " + q1.add(q2));
    }

    private static void performLengthAdditionExplicit(Scanner scanner) {

        QuantityLength q1 = readLength(scanner, "first");
        QuantityLength q2 = readLength(scanner, "second");

        System.out.print("Enter target unit: ");
        LengthUnit target =
                LengthUnit.valueOf(scanner.next().toUpperCase());

        System.out.println("Output: " + q1.add(q2, target));
    }

    private static void performLengthEnumBaseConversion(Scanner scanner) {

        System.out.print("Enter value: ");
        double value = scanner.nextDouble();

        System.out.print("Enter unit: ");
        LengthUnit unit =
                LengthUnit.valueOf(scanner.next().toUpperCase());

        System.out.println("Converted to base unit (FEET): "
                + unit.convertToBaseUnit(value));
    }

    private static QuantityLength readLength(Scanner scanner,
                                             String label) {

        System.out.print("Enter " + label + " value: ");
        double value = scanner.nextDouble();

        System.out.print("Enter " + label + " unit: ");
        LengthUnit unit =
                LengthUnit.valueOf(scanner.next().toUpperCase());

        return new QuantityLength(value, unit);
    }

    // =====================================================
    // WEIGHT SECTION (UC9)
    // =====================================================

    private static void handleWeight(Scanner scanner) {

        System.out.println("\n--- Weight Operations ---");
        System.out.println("1. Convert Weight");
        System.out.println("2. Check Equality");
        System.out.println("3. Add Weights (Result in First Unit)");
        System.out.println("4. Add Weights (Explicit Target Unit)");
        System.out.print("Choose option (1-4): ");

        int choice = scanner.nextInt();

        switch (choice) {

            case 1:
                performWeightConversion(scanner);
                break;

            case 2:
                performWeightEquality(scanner);
                break;

            case 3:
                performWeightAdditionImplicit(scanner);
                break;

            case 4:
                performWeightAdditionExplicit(scanner);
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void performWeightConversion(Scanner scanner) {

        QuantityWeight weight = readWeight(scanner, "source");

        System.out.print("Enter target unit (KILOGRAM, GRAM, POUND): ");
        WeightUnit target =
                WeightUnit.valueOf(scanner.next().toUpperCase());

        System.out.println("Output: " + weight.convertTo(target));
    }

    private static void performWeightEquality(Scanner scanner) {

        QuantityWeight w1 = readWeight(scanner, "first");
        QuantityWeight w2 = readWeight(scanner, "second");

        System.out.println("Output: " + w1.equals(w2));
    }

    private static void performWeightAdditionImplicit(Scanner scanner) {

        QuantityWeight w1 = readWeight(scanner, "first");
        QuantityWeight w2 = readWeight(scanner, "second");

        System.out.println("Output: " + w1.add(w2));
    }

    private static void performWeightAdditionExplicit(Scanner scanner) {

        QuantityWeight w1 = readWeight(scanner, "first");
        QuantityWeight w2 = readWeight(scanner, "second");

        System.out.print("Enter target unit: ");
        WeightUnit target =
                WeightUnit.valueOf(scanner.next().toUpperCase());

        System.out.println("Output: " + w1.add(w2, target));
    }

    private static QuantityWeight readWeight(Scanner scanner,
                                             String label) {

        System.out.print("Enter " + label + " value: ");
        double value = scanner.nextDouble();

        System.out.print("Enter " + label + " unit (KILOGRAM, GRAM, POUND): ");
        WeightUnit unit =
                WeightUnit.valueOf(scanner.next().toUpperCase());

        return new QuantityWeight(value, unit);
    }
}