package org.tggc.authenticationservice.sender.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tggc.authenticationservice.util.CodeGenerator;
import org.tggc.notificationapi.dto.NotificationRq;
import org.tggc.notificationapi.dto.NotificationType;
import org.tggc.notificationapi.dto.ParamDto;
import reactor.kafka.sender.KafkaSender;

import java.util.List;

@Slf4j
@Service
public class ChangePasswordConfirmationSender extends AbstractSender {

    public ChangePasswordConfirmationSender(KafkaSender<String, Object> kafkaSender) {
        super(kafkaSender);
    }

    @Override
    protected NotificationRq.NotificationRqBuilder getNotificationDtoBuilder() {
        String code = CodeGenerator.generate();
        return NotificationRq.builder()
                .subject("Изменение пароля")
                .text("Ваш код для изменения пароля")
                .params(List.of(new ParamDto("code", code)));
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.CHANGE_PASSWORD_CONFIRMATION;
    }
}
