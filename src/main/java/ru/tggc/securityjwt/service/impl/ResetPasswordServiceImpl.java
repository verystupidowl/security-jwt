package ru.tggc.securityjwt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.tggc.securityjwt.service.MailSender;
import ru.tggc.securityjwt.service.ResetPasswordService;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResetPasswordServiceImpl implements ResetPasswordService {
    private final MailSender mailSender;

    @Override
    @Cacheable(value = "reset-code", key = "#email")
    public String getCode(String email) {
        return null;
    }


    @Override
    @CachePut(value = "reset-code", key = "#email")
    public void sendCode(String email, String text) {
        mailSender.sendEmail(email, text);
    }

    @Override
    @CacheEvict(value = "reset-code", key = "#email")
    public void deleteCode(String email) {
        log.info("cache reset-code for email: {}", email);
    }
}
