package com.apps.quantitymeasurement;

import java.util.Scanner;

public class QuantityMeasurementApp {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter first no.(feet): ");
		Feet first = new Feet(input.nextDouble());
		System.out.println("Enter second no. (feet): ");
		Feet second = new Feet(input.nextDouble());

		boolean result = first.equals(second);

		System.out.println("Input: " + first + " and " + second);
		System.out.println("Output: Equal (" + result + ")");
		input.close();
	}
}