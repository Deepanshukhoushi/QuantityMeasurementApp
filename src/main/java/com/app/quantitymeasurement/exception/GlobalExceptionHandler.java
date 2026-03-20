package com.app.quantitymeasurement.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
/**
 * Global Exception Handler for the Quantity Measurement Application.
 * This class intercepts various exceptions thrown by the controllers and
 * returns structured error responses with appropriate HTTP status codes.
 */
public class GlobalExceptionHandler {

    private static final Logger logger =
            Logger.getLogger(GlobalExceptionHandler.class.getName());

    // Error Response Class
    static class ErrorResponse {
        public LocalDateTime timestamp;
        public int status;
        public String error;
        public String message;
        public String path;
    }

    // 1. Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            WebRequest request) {

        List<String> errMsg = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());

        ErrorResponse error = new ErrorResponse();
        error.timestamp = LocalDateTime.now();
        error.status = HttpStatus.BAD_REQUEST.value();
        error.error = "Validation Error";
        error.message = String.join(", ", errMsg);
        error.path = request.getDescription(false).replace("uri=", "");

        logger.warning("Validation Error: " + error.message);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 2. Custom Exception
    @ExceptionHandler(QuantityMeasurementException.class)
    public ResponseEntity<ErrorResponse> handleQuantityException(
            QuantityMeasurementException ex,
            WebRequest request) {

        ErrorResponse error = new ErrorResponse();
        error.timestamp = LocalDateTime.now();
        error.status = HttpStatus.BAD_REQUEST.value();
        error.error = "Quantity Measurement Error";
        error.message = ex.getMessage();
        error.path = request.getDescription(false).replace("uri=", "");

        logger.warning("Quantity Error: " + ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 3. Arithmetic Exception → 500 Internal Server Error
    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<ErrorResponse> handleArithmeticException(
            ArithmeticException ex,
            WebRequest request) {

        ErrorResponse error = new ErrorResponse();
        error.timestamp = LocalDateTime.now();
        error.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        error.error = "Internal Server Error";
        error.message = ex.getMessage();
        error.path = request.getDescription(false).replace("uri=", "");

        logger.severe("Arithmetic Error: " + ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 4. Runtime Exception → 400 Bad Request
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(
            RuntimeException ex,
            WebRequest request) {

        ErrorResponse error = new ErrorResponse();
        error.timestamp = LocalDateTime.now();
        error.status = HttpStatus.BAD_REQUEST.value();
        error.error = "Bad Request";
        error.message = ex.getMessage();
        error.path = request.getDescription(false).replace("uri=", "");

        logger.warning("Runtime Error: " + ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 4. Global Exception → 500 Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex,
            WebRequest request) {

        ErrorResponse error = new ErrorResponse();
        error.timestamp = LocalDateTime.now();
        error.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        error.error = "Internal Server Error";
        error.message = ex.getMessage();
        error.path = request.getDescription(false).replace("uri=", "");

        logger.severe("Global Error: " + ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}