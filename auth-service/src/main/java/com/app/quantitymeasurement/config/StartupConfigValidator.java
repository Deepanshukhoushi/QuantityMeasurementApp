package com.app.quantitymeasurement.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * Validates critical configuration at startup to ensure the app is production-ready.
 */
@Configuration
public class StartupConfigValidator {

    private static final Logger logger = LoggerFactory.getLogger(StartupConfigValidator.class);

    @Value("${app.cors.allowedOrigins:}")
    private String allowedOrigins;

    @Value("${app.oauth2.redirectUri:}")
    private String redirectUri;

    @Value("${spring.profiles.active:dev}")
    private String activeProfiles;

    private final Environment env;

    public StartupConfigValidator(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void validateConfig() {
        logger.info("================================================================================");
        logger.info("STARTUP VALIDATION: Starting configuration check...");
        logger.info("Active Profiles: {}", activeProfiles);
        logger.info("CORS Allowed Origins: {}", allowedOrigins);
        logger.info("OAuth2 Redirect URI: {}", redirectUri);

        boolean isProd = Arrays.asList(env.getActiveProfiles()).contains("prod");

        if (isProd) {
            validateProdConfig();
        }

        logger.info("STARTUP VALIDATION: Configuration check completed successfully.");
        logger.info("================================================================================");
    }

    private void validateProdConfig() {
        if (redirectUri == null || redirectUri.isEmpty() || redirectUri.contains("localhost")) {
            throw new IllegalStateException("FATAL: OAuth2 redirect URI cannot be empty or localhost in production!");
        }

        if (allowedOrigins == null || allowedOrigins.isEmpty() || allowedOrigins.contains("localhost")) {
            logger.warn("CORS origins are missing or contain localhost in production. This might be insecure.");
        }
    }
}
