package com.app.quantitymeasurement.security;

import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.app.quantitymeasurement.exception.BadRequestException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handler for successful OAuth2 login. Generates a JWT and redirects back to the frontend.
 */
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Value("${app.oauth2.redirectUri}")
    private String authorizedRedirectUri;

    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String targetUrl = authorizedRedirectUri;

        // Fail-fast logic for production
        if (targetUrl == null || targetUrl.isEmpty() || targetUrl.contains("localhost")) {
            if (!activeProfile.contains("dev")) {
                throw new BadRequestException("Potential security risk: OAuth2 Redirect URI is misconfigured for " + activeProfile + " profile. Localhost or empty URI is not allowed.");
            }
        }

        if(!isAuthorizedRedirectUri(targetUrl)) {
            throw new BadRequestException("Unauthorized Redirect URI");
        }

        String token = tokenProvider.generateToken(authentication);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        if (uri == null || uri.isEmpty()) return false;
        
        try {
            URI clientRedirectUri = URI.create(uri);
            URI authorizedUri = URI.create(authorizedRedirectUri);

            return authorizedUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                    && authorizedUri.getPort() == clientRedirectUri.getPort();
        } catch (Exception e) {
            return false;
        }
    }
}
