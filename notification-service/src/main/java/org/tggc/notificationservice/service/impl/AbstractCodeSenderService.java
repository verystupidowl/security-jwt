package org.tggc.notificationservice.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.tggc.notificationservice.dto.NotificationRq;
import org.tggc.notificationservice.service.MailSender;
import org.tggc.notificationservice.service.SenderService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractCodeSenderService implements SenderService {
    private final ReactiveRedisTemplate<@NonNull String, @NonNull String> redisTemplate;
    private final MailSender mailSender;

    @Override
    public Mono<@NonNull Void> sendNotification(NotificationRq notificationRq) {
        String key = getNotificationType().getKey(notificationRq.to());
        return Mono.just(notificationRq.params())
                .flatMapMany(Flux::fromIterable)
                .filter(param -> param.name().equals("code"))
                .next()
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Code parameter not found")))
                .flatMap(param -> {
                    String code = param.value();

                    return redisTemplate.opsForValue()
                            .setIfAbsent(key, code, Duration.ofMinutes(3))
                            .flatMap(success -> {
                                if (!success) {
                                    return Mono.error(new RuntimeException("Код уже был отправлен на этот email"));
                                }

                                return mailSender.send(notificationRq);
                            });
                });
    }
}
