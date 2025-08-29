package org.tggc.authenticationservice.sender.impl;

import org.springframework.stereotype.Service;
import org.tggc.authenticationservice.util.CodeGenerator;
import org.tggc.notificationapi.dto.NotificationRq;
import org.tggc.notificationapi.dto.NotificationType;
import org.tggc.notificationapi.dto.ParamDto;
import reactor.kafka.sender.KafkaSender;

import java.util.List;

@Service
public class TwoFactorSender extends AbstractSender {

    public TwoFactorSender(KafkaSender<String, Object> kafkaSender) {
        super(kafkaSender);
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.TWO_FACTOR;
    }

    @Override
    protected NotificationRq.NotificationRqBuilder getNotificationDtoBuilder() {
        String code = CodeGenerator.generate();
        String text = "Ваш код для 2-х факторной аутентификации: " + code;
        return NotificationRq.builder()
                .params(List.of(new ParamDto("code", code)))
                .subject("2-factor authentication")
                .text(text);
    }
}
