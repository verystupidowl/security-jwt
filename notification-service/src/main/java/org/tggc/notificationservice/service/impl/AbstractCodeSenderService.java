package org.tggc.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.tggc.notificationservice.dto.NotificationRq;
import org.tggc.notificationservice.exception.MailDuplicateException;
import org.tggc.notificationservice.service.MailSender;
import org.tggc.notificationservice.service.SenderService;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractCodeSenderService implements SenderService {
    private final RedisTemplate<String, String> redisTemplate;
    private final MailSender mailSender;

    @Override
    public void sendNotification(NotificationRq notificationRq) {
        String key = getNotificationType().getKey(notificationRq.to());
        notificationRq.params().stream()
                .filter(params -> params.name().equals("code"))
                .findFirst()
                .ifPresent(param -> {
                    String code = param.value();
                    Boolean success = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
                        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
                        byte[] valueBytes = code.getBytes(StandardCharsets.UTF_8);
                        Expiration ttl = Expiration.seconds(300);
                        return connection.stringCommands().set(
                                keyBytes,
                                valueBytes,
                                ttl,
                                RedisStringCommands.SetOption.ifAbsent()
                        );
                    });
                    if (Boolean.FALSE.equals(success)) {
                        log.error("Error while sending change password code");
                        throw new MailDuplicateException("Код уже был отправлен на этот email");
                    }
                    mailSender.send(notificationRq);
                });

    }
}
