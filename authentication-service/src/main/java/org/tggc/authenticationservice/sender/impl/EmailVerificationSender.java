package org.tggc.authenticationservice.sender.impl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.tggc.authenticationservice.util.CodeGenerator;
import org.tggc.notificationapi.dto.NotificationRq;
import org.tggc.notificationapi.dto.NotificationType;
import org.tggc.notificationapi.dto.ParamDto;

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
    protected NotificationRq.NotificationRqBuilder getNotificationDtoBuilder() {
        String code = CodeGenerator.generate();
        return NotificationRq.builder()
                .text("Ваш код проверки для регистрации: " + code)
                .params(List.of(new ParamDto("code", code)))
                .subject("Подтверждение почты");
    }
}
