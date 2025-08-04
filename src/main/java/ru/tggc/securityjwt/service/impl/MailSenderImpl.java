package ru.tggc.securityjwt.service.impl;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.tggc.securityjwt.exception.SendMailException;
import ru.tggc.securityjwt.service.MailSender;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailSenderImpl implements MailSender {
    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String text) {
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, "UTF-8");
        String subject = "Смена пароля";

        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, false);
            helper.setFrom("amaklov2002@gmail.com");
            log.info("Отправляем сообщение {} {}", to, text);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Ошибка при отправке письма {} {}", to, e.getMessage(), e);
            throw new SendMailException("Ошибка отправки письма ", e);
        }
    }
}
