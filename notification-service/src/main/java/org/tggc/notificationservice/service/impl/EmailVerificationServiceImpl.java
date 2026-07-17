package org.tggc.notificationservice.service.impl;

import lombok.NonNull;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import org.tggc.notificationapi.dto.NotificationType;
import org.tggc.notificationservice.service.CodeService;
import org.tggc.notificationservice.service.MailSender;
import reactor.core.publisher.Mono;

import static org.tggc.notificationapi.dto.NotificationType.EMAIL_CONFIRMATION;

@Service
public class EmailVerificationServiceImpl extends AbstractCodeSenderService implements CodeService {
    private final ReactiveRedisTemplate<@NonNull String, @NonNull String> redisTemplate;

    public EmailVerificationServiceImpl(ReactiveRedisTemplate<@NonNull String, @NonNull String> redisTemplate,
                                        MailSender mailSender) {
        super(redisTemplate, mailSender);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Mono<@NonNull String> getCode(String email) {
        return redisTemplate.opsForValue().get(getNotificationType().getKey(email));
    }

    @Override
    public Mono<@NonNull Void> deleteCode(String email) {
        return redisTemplate.delete(getNotificationType().getKey(email)).then();
    }

    @Override
    public NotificationType getNotificationType() {
        return EMAIL_CONFIRMATION;
    }
}
