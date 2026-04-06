package com.app.quantitymeasurement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * Service responsible for sending security-related emails (e.g. Password Reset).
 */
@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * Sends a password reset link to the user.
     *
     * @param to        the recipient's email address
     * @param resetLink the link containing the JWT token
     */
    @Async
    public void sendPasswordResetEmail(String to, String resetLink) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject("Reset Your Password - Quantity Measurement App");
            helper.setText(buildPasswordResetEmailBody(resetLink), true);

            mailSender.send(message);
            log.info("Password reset email sent successfully to {}", to);
        } catch (Exception e) {
            log.error("Failed to send password reset email to {}: {}", to, e.getMessage());
            // We do not throw exception here to prevent API from crashing 
            // but we log it for troubleshooting.
        }
    }

    private String buildPasswordResetEmailBody(String resetLink) {
        return "<html><body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>"
                + "<div style='max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #eee; border-radius: 10px;'>"
                + "<h2 style='color: #2c3e50; text-align: center;'>Password Reset Request</h2>"
                + "<p>Hello,</p>"
                + "<p>We received a request to reset the password for your account on <strong>Quantity Measurement App</strong>.</p>"
                + "<p>Click the button below to reset your password. This link is valid for <strong>15 minutes</strong>.</p>"
                + "<div style='text-align: center; margin: 30px 0;'>"
                + "<a href='" + resetLink + "' style='background-color: #3498db; color: white; padding: 12px 25px; text-decoration: none; border-radius: 5px; font-weight: bold;'>Reset Password</a>"
                + "</div>"
                + "<p>If you did not request a password reset, please ignore this email or contact support if you have concerns.</p>"
                + "<hr style='border: 0; border-top: 1px solid #eee; margin: 20px 0;'>"
                + "<p style='font-size: 12px; color: #7f8c8d; text-align: center;'>&copy; 2026 Quantity Measurement App. All rights reserved.</p>"
                + "</div>"
                + "</body></html>";
    }
}
