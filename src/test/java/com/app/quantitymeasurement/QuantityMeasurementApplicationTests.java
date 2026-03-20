package com.app.quantitymeasurement;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityInputDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;

/**
 * Integration tests for the Quantity Measurement Application.
 * This class performs end-to-end testing of the REST API endpoints using a random port.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QuantityMeasurementApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // ---------------- Helper Methods ----------------

    private String baseUrl() {
        return "http://localhost:" + port + "/api/v1/quantities";
    }

    private QuantityInputDTO input(
            double thisValue, String thisUnit, String thisMeasurementType,
            double thatValue, String thatUnit, String thatMeasurementType
    ) {
        QuantityInputDTO inputDTO = new QuantityInputDTO();
        inputDTO.setThisQuantityDTO(new QuantityDTO(thisValue, thisUnit, thisMeasurementType));
        inputDTO.setThatQuantityDTO(new QuantityDTO(thatValue, thatUnit, thatMeasurementType));
        return inputDTO;
    }

    private QuantityInputDTO inputWithTarget(
            double thisValue, String thisUnit, String thisMeasurementType,
            double thatValue, String thatUnit, String thatMeasurementType,
            double targetValue, String targetUnit, String targetMeasurementType
    ) {
        QuantityInputDTO inputDTO = new QuantityInputDTO();
        inputDTO.setThisQuantityDTO(new QuantityDTO(thisValue, thisUnit, thisMeasurementType));
        inputDTO.setThatQuantityDTO(new QuantityDTO(thatValue, thatUnit, thatMeasurementType));
        inputDTO.setTargetQuantityDTO(new QuantityDTO(targetValue, targetUnit, targetMeasurementType));
        return inputDTO;
    }

    private HttpEntity<QuantityInputDTO> jsonEntity(QuantityInputDTO body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }

    // ---------------- Test Cases ----------------

    @Test
    @Order(1)
    @DisplayName("Application context loads and test server starts")
    void contextLoads() {
        assertThat(restTemplate).isNotNull();
        assertThat(port).isGreaterThan(0);
        System.out.println("✅ Test server on port: " + port);
    }

    @Test
    @Order(2)
    @DisplayName("POST /compare – 1 foot equals 12 inches → true")
    void testCompare_FootEqualsInches() {
        QuantityInputDTO body = input(1.0, "FEET", "LengthUnit", 12.0, "INCHES", "LengthUnit");
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                baseUrl() + "/compare", HttpMethod.POST, jsonEntity(body), QuantityMeasurementDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getResultString()).isEqualTo("true");
    }

    @Test
    @Order(3)
    @DisplayName("POST /compare – 1 foot does NOT equal 1 inch → false")
    void testCompare_FootNotEqualInch() {
        QuantityInputDTO body = input(1.0, "FEET", "LengthUnit", 1.0, "INCHES", "LengthUnit");
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                baseUrl() + "/compare", HttpMethod.POST, jsonEntity(body), QuantityMeasurementDTO.class);
        assertThat(response.getBody().getResultString()).isEqualTo("false");
    }

    @Test
    @Order(4)
    @DisplayName("POST /compare – 1 gallon equals 3.785 litres → true")
    void testCompare_GallonEqualsLitres() {
        QuantityInputDTO body = input(1.0, "GALLON", "VolumeUnit", 3.785, "LITRE", "VolumeUnit");
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                baseUrl() + "/compare", HttpMethod.POST, jsonEntity(body), QuantityMeasurementDTO.class);
        assertThat(response.getBody().getResultString()).isEqualTo("true");
    }

    @Test
    @Order(5)
    @DisplayName("POST /compare – 212 Fahrenheit equals 100 Celsius → true")
    void testCompare_FahrenheitEqualsCelsius() {
        QuantityInputDTO body = input(212.0, "FAHRENHEIT", "TemperatureUnit", 100.0, "CELSIUS", "TemperatureUnit");
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                baseUrl() + "/compare", HttpMethod.POST, jsonEntity(body), QuantityMeasurementDTO.class);
        assertThat(response.getBody().getResultString()).isEqualTo("true");
    }

    @Test
    @Order(6)
    @DisplayName("POST /convert — convert 100 Celsius to Fahrenheit")
    void testConvert_CelsiusToFahrenheit() {
        QuantityInputDTO body = input(100.0, "CELSIUS", "TemperatureUnit", 0.0, "FAHRENHEIT", "TemperatureUnit");
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                baseUrl() + "/convert", HttpMethod.POST, jsonEntity(body), QuantityMeasurementDTO.class);
        assertThat((Double) response.getBody().getResultValue()).isEqualTo(212.0);
    }

    @Test
    @Order(7)
    @DisplayName("POST /add — add 1 gallon and 3.785 litres = 2 gallons")
    void testAdd_GallonAndLitres() {
        QuantityInputDTO body = input(1.0, "GALLON", "VolumeUnit", 3.785, "LITRE", "VolumeUnit");
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                baseUrl() + "/add", HttpMethod.POST, jsonEntity(body), QuantityMeasurementDTO.class);
        assertThat((Double) response.getBody().getResultValue()).isEqualTo(2.0);
    }

    @Test
    @Order(8)
    @DisplayName("POST /add-with-target-unit - 1 foot + 12 inches = 24 inches")
    void testAddWithTargetUnit_FootAndInchesToInches() {
        QuantityInputDTO body = inputWithTarget(1.0, "FEET", "LengthUnit", 12.0, "INCHES", "LengthUnit", 0.0, "INCHES", "LengthUnit");
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                baseUrl() + "/add-with-target-unit", HttpMethod.POST, jsonEntity(body), QuantityMeasurementDTO.class);
        assertThat((Double) response.getBody().getResultValue()).isEqualTo(24.0);
    }

    @Test
    @Order(9)
    @DisplayName("POST /subtract - 2 feet - 12 inches = 1 foot")
    void testSubtract_FeetMinusInches() {
        QuantityInputDTO body = input(2.0, "FEET", "LengthUnit", 12.0, "INCHES", "LengthUnit");
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                baseUrl() + "/subtract", HttpMethod.POST, jsonEntity(body), QuantityMeasurementDTO.class);
        assertThat((Double) response.getBody().getResultValue()).isEqualTo(1.0);
    }

    @Test
    @Order(10)
    @DisplayName("POST /subtract-with-target-unit - 2 feet - 12 inches = 12 inches")
    void testSubtractWithTargetUnit() {
        QuantityInputDTO body = inputWithTarget(2.0, "FEET", "LengthUnit", 12.0, "INCHES", "LengthUnit", 0.0, "INCHES", "LengthUnit");
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                baseUrl() + "/subtract-with-target-unit", HttpMethod.POST, jsonEntity(body), QuantityMeasurementDTO.class);
        assertThat((Double) response.getBody().getResultValue()).isEqualTo(12.0);
    }

    @Test
    @Order(11)
    @DisplayName("POST /divide - 1 yard ÷ 1 foot = 3.0")
    void testDivide_YardByFoot() {
        QuantityInputDTO body = input(1.0, "YARDS", "LengthUnit", 1.0, "FEET", "LengthUnit");
        ResponseEntity<QuantityMeasurementDTO> response = restTemplate.exchange(
                baseUrl() + "/divide", HttpMethod.POST, jsonEntity(body), QuantityMeasurementDTO.class);
        assertThat((Double) response.getBody().getResultValue()).isEqualTo(3.0);
    }

    @Test
    @Order(12)
    @DisplayName("GET /history/operation/CONVERT - returns list of CONVERT operations")
    @SuppressWarnings("unchecked")
    void testGetHistoryByOperation_Convert() {
        ResponseEntity<List> response = restTemplate.getForEntity(
                baseUrl() + "/history/operation/CONVERT", List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    @Order(13)
    @DisplayName("GET /history/type/TemperatureUnit - returns history for TemperatureUnit measurements")
    @SuppressWarnings("unchecked")
    void testGetHistoryByType_Temperature() {
        ResponseEntity<List> response = restTemplate.getForEntity(
                baseUrl() + "/history/type/TemperatureUnit", List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    @Order(14)
    @DisplayName("GET /count/DIVIDE - returns count of DIVIDE operations > 0")
    void testGetOperationCount_Divide() {
        ResponseEntity<Long> response = restTemplate.getForEntity(
                baseUrl() + "/count/DIVIDE", Long.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isGreaterThan(0L);
    }

    @Test
    @Order(15)
    @DisplayName("POST /divide — 1 yard ÷ 0 foot → error, GET /history/errored returns that error")
    @SuppressWarnings("unchecked")
    void testDivide_YardByFeet_Error() {
        QuantityInputDTO body = input(1.0, "YARDS", "LengthUnit", 0.0, "FEET", "LengthUnit");

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl() + "/divide", HttpMethod.POST, jsonEntity(body), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).contains("Divide by zero");

        ResponseEntity<List> errorHistoryResponse = restTemplate.getForEntity(
                baseUrl() + "/history/errored", List.class);

        assertThat(errorHistoryResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(16)
    @DisplayName("POST /compare — validation fails: Unit must be valid for the specified measurement type")
    void testCompare_FootEqualsInches_UnitValidationFails() {
        QuantityInputDTO body = input(1.0, "FOOT", "LengthUnit", 12.0, "INCHES", "LengthUnit");

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl() + "/compare", HttpMethod.POST, jsonEntity(body), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Unit must be valid for the specified measurement type");
    }

    @Test
    @Order(17)
    @DisplayName("POST /compare — validation fails: Measurement type must be one of LengthUnit, VolumeUnit, WeightUnit, TemperatureUnit")
    void testCompare_FootEqualsInches_TypeValidationFails() {
        QuantityInputDTO body = input(1.0, "FEET", "InvalidType", 12.0, "INCHES", "LengthUnit");

        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl() + "/compare", HttpMethod.POST, jsonEntity(body), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Measurement type must be one of");
    }
}
