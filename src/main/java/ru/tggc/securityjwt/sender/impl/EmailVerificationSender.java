package ru.tggc.securityjwt.sender.impl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.tggc.securityjwt.dto.notification.NotificationDto;
import ru.tggc.securityjwt.dto.notification.NotificationType;
import ru.tggc.securityjwt.dto.notification.ParamDto;
import ru.tggc.securityjwt.util.CodeGenerator;

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
