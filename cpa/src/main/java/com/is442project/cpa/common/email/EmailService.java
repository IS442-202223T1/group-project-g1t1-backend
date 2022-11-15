package com.is442project.cpa.common.email;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class EmailService {
    
    private final JavaMailSender emailSender;

    @Value("${spring.mail.sender}")
    private String EMAIL_SENDER;

    @Value("${web.server.url}")
    private String WEB_SERVER_URL;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendHtmlMessage(String to, String subject, String body) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setFrom(EMAIL_SENDER);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body, true);

            emailSender.send(mimeMessage);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    public boolean sendHtmlTemplate(String to, String subject, String templatePath) {
        String message;
        try {
            message = Files.asCharSource(new File(templatePath), Charsets.UTF_8).read();
            message = message.replace("{{webServer}}", WEB_SERVER_URL);
            message = message.replace("{{email}}", to);
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setFrom(EMAIL_SENDER);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message, true);

            emailSender.send(mimeMessage);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } 
        catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void sendHtmlMessageWithAttachments(String to, String subject, String body, List<Attachment> attachments){
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            mimeMessageHelper.setFrom(EMAIL_SENDER);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body, true);

            attachments.forEach(attachment -> {
                try {
                    mimeMessageHelper.addAttachment(attachment.fileName(),attachment.file());
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            });

            emailSender.send(mimeMessage);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

}
