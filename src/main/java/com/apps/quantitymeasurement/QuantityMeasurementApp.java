package com.apps.quantitymeasurement;

import java.util.Scanner;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // -------- Feet Equality --------
        System.out.println("Enter first no. (feet): ");
        Feet firstFeet = new Feet(input.nextDouble());

        System.out.println("Enter second no. (feet): ");
        Feet secondFeet = new Feet(input.nextDouble());

        boolean feetResult = firstFeet.equals(secondFeet);

        System.out.println("Input: " + firstFeet + " and " + secondFeet);
        System.out.println("Output: Equal (" + feetResult + ")");

        System.out.println("----------------------------------");

        // -------- Inches Equality --------
        System.out.println("Enter first no. (inch): ");
        Inches firstInch = new Inches(input.nextDouble());

        System.out.println("Enter second no. (inch): ");
        Inches secondInch = new Inches(input.nextDouble());

        boolean inchResult = firstInch.equals(secondInch);

        System.out.println("Input: " + firstInch + " and " + secondInch);
        System.out.println("Output: Equal (" + inchResult + ")");

        input.close();
    }
}