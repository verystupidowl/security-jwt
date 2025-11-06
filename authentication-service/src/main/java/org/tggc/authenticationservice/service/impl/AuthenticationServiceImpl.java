package org.tggc.authenticationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tggc.authapi.dto.AuthenticationRq;
import org.tggc.authapi.dto.AuthenticationRs;
import org.tggc.authapi.dto.RegisterRq;
import org.tggc.authenticationservice.dto.request.ChangePasswordRq;
import org.tggc.authenticationservice.dto.request.SendCodeRq;
import org.tggc.authenticationservice.exception.UserAlreadyCreatedException;
import org.tggc.authenticationservice.exception.UserNotFoundException;
import org.tggc.authenticationservice.exception.UsernameNotFoundException;
import org.tggc.authenticationservice.mapper.AuthMapper;
import org.tggc.authenticationservice.mapper.UserMapper;
import org.tggc.authenticationservice.repository.UserRepository;
import org.tggc.authenticationservice.sender.Sender;
import org.tggc.authenticationservice.sender.SenderFactory;
import org.tggc.authenticationservice.service.AuthenticationService;
import org.tggc.authenticationservice.service.PasswordService;
import org.tggc.authenticationservice.service.validator.impl.EmailCodeValidator;
import org.tggc.authenticationservice.service.validator.impl.PasswordValidator;
import org.tggc.authenticationservice.service.validator.impl.UserValidator;
import org.tggc.authenticationservice.service.validator.rq.ValidationRq;
import org.tggc.notificationapi.api.CodeApi;
import org.tggc.notificationapi.dto.NotificationType;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final SenderFactory senderFactory;
    private final CodeApi codeApi;
    private final PasswordService passwordService;
    private final PasswordValidator passwordValidator;
    private final EmailCodeValidator emailCodeValidator;
    private final UserValidator userValidator;
    private final AuthMapper authMapper;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public Mono<AuthenticationRs> register(RegisterRq rq) {
        return passwordValidator.validate(new ValidationRq<>(rq.password(), rq.passwordConfirmation()))
                .then(userRepository.findByEmail(rq.email())
                        .flatMap(u -> Mono.error(new UserAlreadyCreatedException(u.getEmail())))
                        .switchIfEmpty(Mono.defer(() -> emailCodeValidator.validate(new ValidationRq<>(
                                rq.email(),
                                rq.verificationCode()
                        ))))
                        .then(userRepository.save(userMapper.toEntity(rq, passwordService)))
                )
                .map(authMapper::toDto);
    }

    @Override
    @Transactional
    public Mono<AuthenticationRs> authenticate(AuthenticationRq rq) {
        return userRepository.findByEmail(rq.email())
                .flatMap(user -> userValidator.validate(new ValidationRq<>(user, rq.password())))
                .switchIfEmpty(Mono.error(new UsernameNotFoundException(rq.email())))
                .map(authMapper::toDto);
    }

    @Override
    public Mono<Void> sendCode(SendCodeRq dto) {
        Sender sender = senderFactory.getSender(dto.type());
        return sender.send(dto.email(), dto.type());
    }

    @Override
    @Transactional
    public Mono<Void> changePassword(ChangePasswordRq dto) {
        return passwordValidator.validate(new ValidationRq<>(dto.password(), dto.passwordConfirmation()))
                .then(userRepository.findByEmail(dto.email())
                        .switchIfEmpty(Mono.error(new UserNotFoundException(dto.email())))
                        .flatMap(user -> {
                            user.setPassword(passwordService.hash(dto.password()));
                            user.setUpdatedAt(LocalDateTime.now());
                            return userRepository.save(user);
                        })
                        .then(Mono.fromRunnable(() -> codeApi.deleteCode(
                                dto.email(),
                                NotificationType.CHANGE_PASSWORD_CONFIRMATION
                        )).subscribeOn(Schedulers.boundedElastic()))
                        .then(sendChangedPasswordNotification(dto.email()))
                );
    }

    private Mono<Void> sendChangedPasswordNotification(String email) {
        return senderFactory.getSender(NotificationType.CHANGED_PASSWORD)
                .send(email, NotificationType.CHANGED_PASSWORD);
    }
}
