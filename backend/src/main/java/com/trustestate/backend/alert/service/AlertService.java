package com.trustestate.backend.alert.service;

import com.trustestate.backend.alert.models.Alert;
import com.trustestate.backend.alert.repository.AlertRepository;
import com.trustestate.backend.common.AlertPriority;
import com.trustestate.backend.exception.UserException;
import com.trustestate.backend.profile.models.Tenant;
import com.trustestate.backend.user_management.models.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;
    private final JavaMailSender mailSender;
    public void createAlert(User recipient, String title, String message, AlertPriority priority) {
        Alert alert = new Alert();
        alert.setRecipient(recipient);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPriority(priority);
        alert.setRead(false);
        alert.setCreatedAt(LocalDateTime.now());

        alertRepository.save(alert);

        if (priority == AlertPriority.HIGH) {
            sendEmail(recipient.getEmail(), title, message);
        }
    }

    @Async
    protected void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("TrustEstate | " + subject);

            // You can use HTML tags here!
            String htmlContent = String.format(
                    "<html><body style='font-family: Arial, sans-serif;'>" +
                            "<h2 style='color: #2e6c80;'>TrustEstate Update</h2>" +
                            "<p>%s</p>" +
                            "<hr/>" +
                            "<footer style='font-size: 0.8em;'>This is an automated security alert from TrustEstate.</footer>" +
                            "</body></html>", body);

            helper.setText(htmlContent, true); // 'true' means it's HTML
            helper.setFrom("alerts@trustestate.co.ke");

            mailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("Email failed: " + e.getMessage());
        }
    }


    @Transactional
    public void markAlertAsRead(Long alertId) {
        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new UserException("Alert not found"));

        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!alert.getRecipient().getEmail().equals(currentUser)) {
            throw new UserException("Unauthorized to modify this alert");
        }

        alert.setRead(true);
        alertRepository.save(alert);
    }
}
