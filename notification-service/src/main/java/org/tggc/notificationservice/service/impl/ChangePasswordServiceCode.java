package org.tggc.notificationservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.tggc.notificationapi.dto.NotificationType;
import org.tggc.notificationservice.service.CodeService;
import org.tggc.notificationservice.service.MailSender;

@Slf4j
@Service
public class ChangePasswordServiceCode extends AbstractCodeSenderService implements CodeService {
    private final RedisTemplate<String, String> redisTemplate;

    public ChangePasswordServiceCode(RedisTemplate<String, String> redisTemplate, MailSender mailSender) {
        super(redisTemplate, mailSender);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getCode(String email) {
        return redisTemplate.opsForValue().get(getNotificationType().getKey(email));
    }


    @Override
    public void deleteCode(String email) {
        log.info("cache reset-code for email: {}", email);
        redisTemplate.delete(getNotificationType().getKey(email));
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.CHANGE_PASSWORD;
    }
}
