package com.apps.quantitymeasurement.model;

import java.io.Serializable;

public class QuantityMeasurementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Object operand1;
    private Object operand2;
    private String operationType;
    private Object result;
    private String errorMessage;

    public QuantityMeasurementEntity(Object operand1,
                                     Object operand2,
                                     String operationType,
                                     Object result) {

        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operationType = operationType;
        this.result = result;
    }

    public QuantityMeasurementEntity(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getOperand1() {
        return operand1;
    }

    public Object getOperand2() {
        return operand2;
    }

    public String getOperationType() {
        return operationType;
    }

    public Object getResult() {
        return result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean hasError() {
        return errorMessage != null;
    }
}