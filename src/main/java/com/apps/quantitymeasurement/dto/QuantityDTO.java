package com.apps.quantitymeasurement.dto;

public class QuantityDTO {

    private double value;
    private String unit;
    private String category;

    public QuantityDTO(double value, String unit, String category) {
        this.value = value;
        this.unit = unit;
        this.category = category;
    }

    public double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public String getCategory() {
        return category;
    }
}