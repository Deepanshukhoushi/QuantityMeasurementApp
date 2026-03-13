package com.apps.quantitymeasurement.entity;

import java.io.Serializable;

public class QuantityMeasurementEntity implements Serializable {

    private String operation;
    private String operand1;
    private String operand2;
    private String result;

    public QuantityMeasurementEntity(
            String operation,
            String operand1,
            String operand2,
            String result) {

        this.operation = operation;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.result = result;
    }

    public String toString() {
        return operand1 + " " + operation + " " + operand2 + " = " + result;
    }
}