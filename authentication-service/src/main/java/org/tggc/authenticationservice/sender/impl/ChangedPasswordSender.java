package org.tggc.authenticationservice.sender.impl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.tggc.notificationapi.dto.NotificationRq;
import org.tggc.notificationapi.dto.NotificationType;

@Service
public class ChangedPasswordSender extends AbstractSender {


    public ChangedPasswordSender(KafkaTemplate<String, Object> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    protected NotificationRq.NotificationRqBuilder getNotificationDtoBuilder() {
        return NotificationRq.builder()
                .text("Вы сменили пароль, если Вы этого не делали, обратитесь в поддержку")
                .subject("Смена пароля");
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.CHANGED_PASSWORD;
    }
}
