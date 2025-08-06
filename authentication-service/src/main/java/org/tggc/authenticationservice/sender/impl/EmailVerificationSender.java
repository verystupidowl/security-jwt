package org.tggc.authenticationservice.sender.impl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.tggc.authenticationservice.dto.notification.NotificationDto;
import org.tggc.authenticationservice.dto.notification.ParamDto;
import org.tggc.authenticationservice.util.CodeGenerator;
import org.tggc.notificationapi.dto.NotificationType;

import java.util.List;

@Service
public class EmailVerificationSender extends AbstractSender {

    public EmailVerificationSender(KafkaTemplate<String, Object> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.EMAIL_CONFIRMATION;
    }

    @Override
    protected NotificationDto.NotificationDtoBuilder getNotificationDtoBuilder() {
        String code = CodeGenerator.generate();
        return NotificationDto.builder()
                .text(STR."Ваш код проверки для регистрации: \{code}")
                .params(List.of(new ParamDto("code", code)))
                .subject("Подтверждение почты");
    }
}
