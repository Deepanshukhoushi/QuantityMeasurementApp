package com.app.quantitymeasurement.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class QuantityDTOTest {

    @Test
    void testQuantityDTOGetters() {
        QuantityDTO dto = new QuantityDTO(12.0, "FEET", "LENGTH");
        
        assertEquals(12.0, dto.getValue());
        assertEquals("FEET", dto.getUnit());
        assertEquals("LENGTH", dto.getCategory());
    }
}
