package com.apps.quantitymeasurement.controller;

import java.util.Scanner;

import com.apps.quantitymeasurement.quantity.Quantity;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.apps.quantitymeasurement.units.IMeasurable;
import com.apps.quantitymeasurement.units.LengthUnit;
import com.apps.quantitymeasurement.units.TemperatureUnit;
import com.apps.quantitymeasurement.units.VolumeUnit;
import com.apps.quantitymeasurement.units.WeightUnit;

public class QuantityMeasurementController {

	private final QuantityMeasurementServiceImpl service;

	public QuantityMeasurementController(QuantityMeasurementServiceImpl service) {
		this.service = service;
	}

	public void startApplication() {

		Scanner sc = new Scanner(System.in);

		System.out.println("Welcome to Quantity Measurement");

		printCategory();
		System.out.print("\nEnter option: ");
		int category = Integer.parseInt(sc.nextLine());

		printOperation();
		System.out.print("\nEnter option: ");
		int operation = Integer.parseInt(sc.nextLine());

		switch (category) {

		case 1 -> handleCategory(sc, operation, LengthUnit.values());
		case 2 -> handleCategory(sc, operation, WeightUnit.values());
		case 3 -> handleCategory(sc, operation, VolumeUnit.values());
		case 4 -> handleCategory(sc, operation, TemperatureUnit.values());

		default -> System.out.println("\nInvalid category");
		}

		System.out.println("\nThankyou for using");
		sc.close();
	}

	private void printCategory() {

		System.out.println("\nChoose Category:");
		System.out.println("1 Length");
		System.out.println("2 Weight");
		System.out.println("3 Volume");
		System.out.println("4 Temperature");
	}

	private void printOperation() {

		System.out.println("\nChoose Operation:");
		System.out.println("1 Compare");
		System.out.println("2 Convert");
		System.out.println("3 Add");
		System.out.println("4 Subtract");
		System.out.println("5 Divide");
	}

	private <U extends IMeasurable> void handleCategory(Scanner sc, int operation, U[] units) {

		printUnits(units);

		Quantity<U> q1 = createQuantity(sc, units);

		service.performOperation(sc, operation, q1, units);
	}

	private <U extends IMeasurable> void printUnits(U[] units) {

		for (int i = 0; i < units.length; i++) {
			System.out.println((i + 1) + ". " + units[i].getUnitName());
		}
	}

	private <U extends IMeasurable> Quantity<U> createQuantity(Scanner sc, U[] units) {

		System.out.print("Choose unit: ");
		int index = Integer.parseInt(sc.nextLine()) - 1;

		System.out.print("Enter value: ");
		double value = Double.parseDouble(sc.nextLine());

		return new Quantity<>(value, units[index]);
	}
}