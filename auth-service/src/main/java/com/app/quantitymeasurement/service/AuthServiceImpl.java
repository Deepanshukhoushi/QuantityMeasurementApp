package com.app.quantitymeasurement.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.quantitymeasurement.client.UserServiceClient;
import com.app.quantitymeasurement.dto.ApiResponse;
import com.app.quantitymeasurement.dto.LoginRequest;
import com.app.quantitymeasurement.dto.SignUpRequest;
import com.app.quantitymeasurement.entity.User;
import com.app.quantitymeasurement.enums.AuthProvider;
import com.app.quantitymeasurement.exception.BadRequestException;
import com.app.quantitymeasurement.exception.ResourceNotFoundException;
import com.app.quantitymeasurement.repository.UserRepository;
import com.app.quantitymeasurement.security.JwtTokenProvider;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of AuthService.
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private EmailService emailService;

    @Value("${app.auth.reset-password-url:http://localhost:4200/reset-password}")
    private String resetPasswordUrl;

    @Override
    public String authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.generateToken(authentication);
    }

    @Override
    @Transactional
    public ApiResponse registerUser(SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        // 1. Create and save user identity locally (auth-service DB)
        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setProvider(AuthProvider.local);
        user.setEmailVerified(false);

        User result = userRepository.save(user);
        log.info("Identity created in auth-service for: {}", result.getEmail());

        // 2. Sync with user-service (create profile)
        try {
            userServiceClient.createProfile(signUpRequest);
            log.info("Profile synchronized with user-service for: {}", signUpRequest.getEmail());
        } catch (Exception e) {
            log.error("Failed to sync profile with user-service: {}", e.getMessage());
            // In a production system, you might want to use a transactional outbox 
            // or retry logic here. For now, we log and continue.
        }

        return new ApiResponse(true, "User registered successfully");
    }

    @Override
    @Transactional
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        // Generate short-lived JWT token (15 minutes)
        String token = tokenProvider.generatePasswordResetToken(email);
        
        // Store token in database for validation and to prevent reuse
        user.setResetPasswordToken(token);
        user.setResetPasswordTokenExpiry(LocalDateTime.now().plusMinutes(15));
        userRepository.save(user);
        
        String resetLink = resetPasswordUrl + "?token=" + token;

        // Send email asynchronously
        emailService.sendPasswordResetEmail(email, resetLink);
        log.info("Password reset token generated and email sent for: {}", email);
    }

    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {
        // 1. Find user by token stored in database
        User user = userRepository.findByResetPasswordToken(token)
                .orElseThrow(() -> new BadRequestException("Invalid password reset token."));

        // 2. Check if token has expired
        if (user.getResetPasswordTokenExpiry() != null && 
            LocalDateTime.now().isAfter(user.getResetPasswordTokenExpiry())) {
            throw new BadRequestException("Password reset token has expired.");
        }

        // 3. Validate JWT token signature
        if (!tokenProvider.validateToken(token)) {
            throw new BadRequestException("Invalid or expired password reset token.");
        }

        // 4. Verify token belongs to this user (email match)
        String tokenEmail = tokenProvider.getEmailFromToken(token);
        if (!user.getEmail().equals(tokenEmail)) {
            throw new BadRequestException("Invalid password reset token.");
        }

        // 5. Update password and reset date
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setLastPasswordResetDate(LocalDateTime.now());
        
        // 6. Clear the reset token to prevent reuse
        user.setResetPasswordToken(null);
        user.setResetPasswordTokenExpiry(null);
        
        userRepository.save(user);

        log.info("Password successfully reset for user: {}", user.getEmail());
    }
}
