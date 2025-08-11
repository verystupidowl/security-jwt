package org.tggc.authenticationservice.sender.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.tggc.authenticationservice.util.CodeGenerator;
import org.tggc.notificationapi.dto.NotificationRq;
import org.tggc.notificationapi.dto.NotificationType;
import org.tggc.notificationapi.dto.ParamDto;

import java.util.List;

@Slf4j
@Service
public class ChangePasswordConfirmationSender extends AbstractSender {

    public ChangePasswordConfirmationSender(KafkaTemplate<String, Object> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.CHANGE_PASSWORD_CONFIRMATION;
    }

    @Override
    protected NotificationRq.NotificationRqBuilder getNotificationDtoBuilder() {
        String code = CodeGenerator.generate();
        return NotificationRq.builder()
                .subject("Изменение пароля")
                .text("Ващ код для изменения пароля")
                .params(List.of(new ParamDto("code", code)));
    }
}
