package org.tggc.notificationservice.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.tggc.notificationapi.dto.NotificationType;
import org.tggc.notificationservice.service.CodeService;
import org.tggc.notificationservice.service.MailSender;

@Service
public class TwoFactorService extends AbstractCodeSenderService implements CodeService {
    private final RedisTemplate<String, String> redisTemplate;

    public TwoFactorService(RedisTemplate<String, String> redisTemplate, MailSender mailSender) {
        super(redisTemplate, mailSender);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getCode(String email) {
        return redisTemplate.opsForValue().get(getNotificationType().getKey(email));
    }

    @Override
    public void deleteCode(String email) {
        redisTemplate.delete(getNotificationType().getKey(email));
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.TWO_FACTOR;
    }
}
