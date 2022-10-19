package com.is442project.cpa.common.email;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    private final EmailService emailService;

    @Value("${spring.mail.emailChecking}")
    private boolean emailChecking;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/verify")
    public ResponseEntity sendVerificationEmail(@RequestParam("email") String email) {
        String[] toComponents = email.split("@");
        List<String> permittedEmails = Arrays.asList("sportsschool.edu.sg", "nysi.org.sg");

        if (toComponents.length != 2 || (emailChecking && !permittedEmails.contains(toComponents[1]))) {
            return ResponseEntity.badRequest().body("Invalid email");
        }

        String subject = "Complete Your SSS Pass Service Account Registration";
        String templatePath = "src/main/resources/emailTemplates/registration.html";
        boolean isEmailSent = emailService.sendHtmlTemplate(email, subject, templatePath);
        return isEmailSent ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
