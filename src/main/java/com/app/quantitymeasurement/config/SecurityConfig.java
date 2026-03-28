package com.app.quantitymeasurement.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.app.quantitymeasurement.security.CustomOAuth2UserService;
import com.app.quantitymeasurement.security.JwtAuthenticationFilter;
import com.app.quantitymeasurement.security.OAuth2AuthenticationSuccessHandler;

/**
 * Spring Security configuration for the Quantity Measurement Application.
 *
 * <p>Configures:
 * <ul>
 *   <li>Stateless JWT-based API security</li>
 *   <li>Google OAuth2 login flow with a custom user service and success handler</li>
 *   <li>Endpoint authorization rules (public vs. protected)</li>
 *   <li>CORS policy for frontend origins</li>
 * </ul>
 * </p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    /**
     * Registers the JWT Authentication Filter as a Spring Bean.
     * Defined here (not as @Component) to avoid double-registration by Spring Boot.
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    /**
     * Configures the main Security Filter Chain.
     *
     * <p>Public endpoints (Swagger, OAuth2 redirects, auth APIs) are allowed without
     * a token. All other endpoints require a valid JWT.</p>
     *
     * @param http the HttpSecurity builder
     * @return the built SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // Enable CORS with the configured source
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // Disable CSRF (stateless REST API — no cookies)
            .csrf(csrf -> csrf.disable())

            // Stateless session — JWTs are self-contained, no server-side session needed
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Allow H2 console iframes
            .headers(headers ->
                headers.frameOptions(frame -> frame.disable())
            )

            // Authorization rules — no RBAC, all authenticated users have the same access
            .authorizeHttpRequests(auth -> auth
                // Public endpoints — no token required
                .requestMatchers(
                    "/h2-console/**",
                    "/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/actuator/**",
                    "/oauth2/**",
                    "/login/**",
                    "/api/auth/**"
                ).permitAll()
                // All other requests need a valid JWT
                .anyRequest().authenticated()
            )

            // Google OAuth2 Login configuration
            .oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(endpoint ->
                    endpoint.baseUri("/oauth2/authorize")
                )
                .redirectionEndpoint(endpoint ->
                    endpoint.baseUri("/login/oauth2/code/*")
                )
                .userInfoEndpoint(userInfo ->
                    userInfo.userService(customOAuth2UserService)
                )
                .successHandler(oAuth2AuthenticationSuccessHandler)
            );

        // Insert JWT filter before the standard username/password filter
        http.addFilterBefore(jwtAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Exposes the AuthenticationManager bean for use in any local authentication logic.
     *
     * @param authenticationConfiguration Spring's auto-configured auth configuration
     * @return the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }

    /**
     * CORS configuration allowing the frontend origin and common HTTP methods.
     *
     * @return the CORS configuration source
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow frontend origins
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "http://localhost:4200",
                "http://localhost:8080"
        ));

        // Allow HTTP methods
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
        ));

        // Allow specific headers
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "Origin", "X-Requested-With"));

        // Allow credentials (auth headers, cookies)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}