package com.quesra.quesra.service;

import com.quesra.quesra.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;

@Service
public class EmailSenderService {
    private static final String USER = "user";
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    @Value("${spring.mail.username}") private String sender;

    public EmailSenderService(JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmail(User user, String to, String subject) throws MessagingException{
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        context.setVariable(USER,user);
        String html = templateEngine.process("emailCreation", context);
        helper.setTo(to);
        helper.setText(html, true);
        helper.setSubject(subject);
        helper.setFrom(sender);
        emailSender.send(message);
    }
}
