package org.tggc.authenticationservice.sender.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.tggc.authenticationservice.dto.notification.NotificationDto;
import org.tggc.authenticationservice.dto.notification.ParamDto;
import org.tggc.authenticationservice.util.CodeGenerator;
import org.tggc.notificationapi.dto.NotificationType;

import java.util.List;

@Slf4j
@Service
public class ChangePasswordSender extends AbstractSender {

    public ChangePasswordSender(KafkaTemplate<String, Object> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.CHANGE_PASSWORD;
    }

    @Override
    protected NotificationDto.NotificationDtoBuilder getNotificationDtoBuilder() {
        String code = CodeGenerator.generate();
        return NotificationDto.builder()
                .subject("Изменение пароля")
                .text("Ващ код для изменения пароля")
                .params(List.of(new ParamDto("code", code)));
    }
}
