package org.tggc.authenticationservice.sender.impl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.tggc.authenticationservice.dto.notification.NotificationDto;
import org.tggc.authenticationservice.dto.notification.ParamDto;
import org.tggc.authenticationservice.util.CodeGenerator;
import org.tggc.notificationapi.dto.NotificationType;

import java.util.List;

@Service
public class TwoFactorSender extends AbstractSender {

    public TwoFactorSender(KafkaTemplate<String, Object> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    protected NotificationDto.NotificationDtoBuilder getNotificationDtoBuilder() {
        String code = CodeGenerator.generate();
        String text = STR."Ваш код для 2-х факторной аутентификации: \{code}";
        return NotificationDto.builder()
                .params(List.of(new ParamDto("code", code)))
                .subject("2-factor authentication")
                .text(text);
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.TWO_FACTOR;
    }
}
