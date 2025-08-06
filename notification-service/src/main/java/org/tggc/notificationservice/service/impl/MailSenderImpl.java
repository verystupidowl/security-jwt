package org.tggc.notificationservice.service.impl;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.tggc.notificationservice.dto.NotificationRq;
import org.tggc.notificationservice.exception.SendMailException;
import org.tggc.notificationservice.service.MailSender;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender {
    private final JavaMailSender mailSender;

    @Override
    public void send(NotificationRq notificationRq) {
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, "UTF-8");
        String to = notificationRq.to();
        String subject = notificationRq.subject();
        String text = notificationRq.text();

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
