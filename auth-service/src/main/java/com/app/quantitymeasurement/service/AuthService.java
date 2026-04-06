package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.ApiResponse;
import com.app.quantitymeasurement.dto.LoginRequest;
import com.app.quantitymeasurement.dto.SignUpRequest;

/**
 * Service interface for authentication operations.
 */
public interface AuthService {

    /** Authenticate a user and return a JWT. */
    String authenticateUser(LoginRequest loginRequest);

    /** Register a new user and sync with user-service. */
    ApiResponse registerUser(SignUpRequest signUpRequest);

    /** Initiate password reset flow by email. */
    void forgotPassword(String email);

    /** Reset password using a valid token. */
    void resetPassword(String token, String newPassword);
}
