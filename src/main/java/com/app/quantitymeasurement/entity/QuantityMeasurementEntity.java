/**
 * QuantityMeasurementEntity.java
 *
 * This class represents the entity model for a quantity measurement transaction.
 * It is used to encapsulate all data related to a specific measurement operation, including
 * the input values, target units, selected operations, and the final computed results or errors.
 * This entity is mapped to the relational database schema, allowing the repository layer
 * to persist the state of each conversion or comparison request for historical tracking.
 *
 * @author Developer
 * @version 16.0
 * @since 16.0
 */
package com.app.quantitymeasurement.entity;

public class QuantityMeasurementEntity {

    private long id;
    private double thisValue;
    private String thisUnit;
    private String thisMeasurementType;

    private double thatValue;
    private String thatUnit;
    private String thatMeasurementType;

    private String operation;

    private double resultValue;
    private String resultUnit;
    private String resultMeasurementType;
    private String resultString;

    private boolean isError;
    private String errorMessage;

    public QuantityMeasurementEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getThisValue() {
        return thisValue;
    }

    public void setThisValue(double thisValue) {
        this.thisValue = thisValue;
    }

    public String getThisUnit() {
        return thisUnit;
    }

    public void setThisUnit(String thisUnit) {
        this.thisUnit = thisUnit;
    }

    public String getThisMeasurementType() {
        return thisMeasurementType;
    }

    public void setThisMeasurementType(String thisMeasurementType) {
        this.thisMeasurementType = thisMeasurementType;
    }

    public double getThatValue() {
        return thatValue;
    }

    public void setThatValue(double thatValue) {
        this.thatValue = thatValue;
    }

    public String getThatUnit() {
        return thatUnit;
    }

    public void setThatUnit(String thatUnit) {
        this.thatUnit = thatUnit;
    }

    public String getThatMeasurementType() {
        return thatMeasurementType;
    }

    public void setThatMeasurementType(String thatMeasurementType) {
        this.thatMeasurementType = thatMeasurementType;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public double getResultValue() {
        return resultValue;
    }

    public void setResultValue(double resultValue) {
        this.resultValue = resultValue;
    }

    public String getResultUnit() {
        return resultUnit;
    }

    public void setResultUnit(String resultUnit) {
        this.resultUnit = resultUnit;
    }

    public String getResultMeasurementType() {
        return resultMeasurementType;
    }

    public void setResultMeasurementType(String resultMeasurementType) {
        this.resultMeasurementType = resultMeasurementType;
    }

    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "QuantityMeasurementEntity{" +
                "id=" + id +
                ", thisValue=" + thisValue +
                ", thisUnit='" + thisUnit + '\'' +
                ", thisMeasurementType='" + thisMeasurementType + '\'' +
                ", thatValue=" + thatValue +
                ", thatUnit='" + thatUnit + '\'' +
                ", thatMeasurementType='" + thatMeasurementType + '\'' +
                ", operation='" + operation + '\'' +
                ", resultValue=" + resultValue +
                ", resultUnit='" + resultUnit + '\'' +
                ", resultMeasurementType='" + resultMeasurementType + '\'' +
                ", resultString='" + resultString + '\'' +
                ", isError=" + isError +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}