package com.is442project.cpa.common.email;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity sendVerificationEmail(@RequestBody String to) {
        String[] toComponents = to.split("@");
        List<String> permittedEmails = Arrays.asList("sportsschool.edu.sg", "nysi.org.sg");

        if (emailChecking && (toComponents.length < 2 || !permittedEmails.contains(toComponents[1]))) {
            return ResponseEntity.badRequest().body("Invalid email");
        }

        String subject = "Complete Your SSS Pass Service Account Registration";
        String templatePath = "src/main/resources/emailTemplates/registration.html";
        emailService.sendHtmlTemplate(to, subject, templatePath);
        return ResponseEntity.ok().build();
    }
}
