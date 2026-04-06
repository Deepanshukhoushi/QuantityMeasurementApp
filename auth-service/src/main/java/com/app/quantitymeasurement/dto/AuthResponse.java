package com.app.quantitymeasurement.dto;

import lombok.Data;

/**
 * DTO for authentication response.
 */
@Data
public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
