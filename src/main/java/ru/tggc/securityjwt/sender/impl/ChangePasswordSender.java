package ru.tggc.securityjwt.sender.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.tggc.securityjwt.dto.notification.NotificationDto;
import ru.tggc.securityjwt.dto.notification.NotificationType;
import ru.tggc.securityjwt.dto.notification.ParamDto;
import ru.tggc.securityjwt.util.CodeGenerator;

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
