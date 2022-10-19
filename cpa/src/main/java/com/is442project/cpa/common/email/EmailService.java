package com.is442project.cpa.common.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailService {
    private final JavaMailSender emailSender;

    @Value("${spring.mail.sender}")
    private String EMAIL_SENDER;

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
