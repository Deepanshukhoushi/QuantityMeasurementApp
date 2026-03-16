package com.app.quantitymeasurement.exception;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class DatabaseExceptionTest {

    @Test
    void testConnectionFailedException() {
        Throwable cause = new RuntimeException("DB down");
        DatabaseException exception = DatabaseException.connectionFailed("localhost:1521", cause);
        
        assertEquals("Database connection failed: localhost:1521", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testQueryFailedException() {
        Throwable cause = new RuntimeException("Syntax error in SQL");
        DatabaseException exception = DatabaseException.queryFailed("SELECT * FROM x", cause);
        
        assertEquals("Query execution failed: SELECT * FROM x", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testCustomMessageAndCauseConstructor() {
        Throwable cause = new RuntimeException("Root cause");
        DatabaseException exception = new DatabaseException("Custom Message", cause);
        
        assertEquals("Custom Message", exception.getMessage());
        assertNotNull(exception.getCause());
        assertEquals("Root cause", exception.getCause().getMessage());
    }

    @Test
    void testStringMessageException() {
        DatabaseException exception = new DatabaseException("Only Message Passed");
        assertEquals("Only Message Passed", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testTransactionFailedException() {
        Throwable cause = new RuntimeException("Rollback error");
        DatabaseException exception = DatabaseException.transactionFailed("SAVE_ENTITY", cause);
        
        assertEquals("Transaction failed during SAVE_ENTITY", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
