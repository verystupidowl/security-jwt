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
import org.tggc.authenticationservice.dto.request.VerifyRq;
import org.tggc.authenticationservice.exception.IncorrectCodeException;
import org.tggc.authenticationservice.exception.IncorrectPasswordException;
import org.tggc.authenticationservice.exception.PasswordsNotMatchException;
import org.tggc.authenticationservice.exception.UserAlreadyCreatedException;
import org.tggc.authenticationservice.exception.UserBlockedException;
import org.tggc.authenticationservice.exception.UserNotFoundException;
import org.tggc.authenticationservice.exception.UsernameNotFoundException;
import org.tggc.authenticationservice.model.Role;
import org.tggc.authenticationservice.model.User;
import org.tggc.authenticationservice.repository.UserRepository;
import org.tggc.authenticationservice.sender.Sender;
import org.tggc.authenticationservice.sender.SenderFactory;
import org.tggc.authenticationservice.service.AuthenticationService;
import org.tggc.authenticationservice.service.PasswordService;
import org.tggc.notificationapi.api.CodeApi;
import org.tggc.notificationapi.dto.NotificationType;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final SenderFactory senderFactory;
    private final CodeApi codeApi;
    private final PasswordService passwordService;

    @Override
    public Mono<AuthenticationRs> register(RegisterRq rq) {
        if (!rq.password().equals(rq.passwordConfirmation())) {
            return Mono.error(new PasswordsNotMatchException());
        }
        return userRepository.findByEmail(rq.email())
                .flatMap(existingUser -> {
                    log.info("user with email {} already exists", existingUser.getEmail());
                    return Mono.error(new UserAlreadyCreatedException(existingUser.getEmail()));
                })
                .switchIfEmpty(Mono.defer(() -> {
                    VerifyRq verifyRq = new VerifyRq(
                            rq.verificationCode(),
                            rq.email(),
                            NotificationType.EMAIL_CONFIRMATION
                    );

                    if (!verifyCode(verifyRq)) {
                        return Mono.error(new IncorrectCodeException());
                    }
                    User user = User.builder()
                            .firstname(rq.firstname())
                            .lastname(rq.lastname())
                            .email(rq.email())
                            .password(passwordService.hash(rq.password()))
                            .role(Role.USER.name())
                            .twoFactorEnabled(rq.twoFactorEnabled())
                            .blocked(false)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build();
                    return userRepository.save(user);
                }))
                .cast(User.class)
                .map(user -> new AuthenticationRs(
                        user.getEmail(),
                        List.of(user.getRole()),
                        user.getId())
                );
    }

    @Override
    public Mono<AuthenticationRs> authenticate(AuthenticationRq request) {
        return userRepository.findByEmail(request.email())
                .flatMap(user -> checkUser(user, request)
                        .then(Mono.just(new AuthenticationRs(
                                user.getEmail(),
                                List.of(user.getRole()),
                                user.getId())
                        )))
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("email {}", request.email());
                    return Mono.error(new UsernameNotFoundException(request.email()));
                }));
    }

    @Override
    public Mono<Void> sendCode(SendCodeRq dto) {
        Sender sender = senderFactory.getSender(dto.type());
        return sender.send(dto.email(), dto.type());
    }

    @Override
    @Transactional
    public Mono<Void> changePassword(ChangePasswordRq dto) {
        if (!dto.password().equals(dto.passwordConfirmation())) {
            return Mono.error(new PasswordsNotMatchException());
        }
        return userRepository.findByEmail(dto.email())
                .switchIfEmpty(Mono.error(new UserNotFoundException(dto.email())))
                .flatMap(user -> {
                    user.setPassword(passwordService.hash(dto.password()));
                    user.setUpdatedAt(LocalDateTime.now());
                    return userRepository.save(user)
                            .then(Mono.fromRunnable(() -> codeApi.deleteCode(dto.email(), NotificationType.CHANGE_PASSWORD_CONFIRMATION))
                                    .subscribeOn(Schedulers.boundedElastic()))
                            .then(Mono.defer(() -> {
                                Sender sender = senderFactory.getSender(NotificationType.CHANGED_PASSWORD);
                                return sender.send(dto.email(), NotificationType.CHANGED_PASSWORD);
                            }));
                });
    }

    private boolean verifyCode(VerifyRq dto) {
        String code = codeApi.getCode(dto.email(), dto.notificationType());
        return dto.code().equals(code);
    }

    private Mono<Void> checkUser(User user, AuthenticationRq rq) {
        return checkPassword(rq.password(), user.getPassword())
                .then(checkBlocked(user.getBlocked()));
    }

    private Mono<Void> checkPassword(String password, String passwordFromDb) {
        if (!passwordService.checkPassword(password, passwordFromDb)) {
            return Mono.error(new IncorrectPasswordException());
        }
        return Mono.empty();
    }

    private Mono<Void> checkBlocked(Boolean blocked) {
        if (Boolean.TRUE.equals(blocked)) {
            return Mono.error(new UserBlockedException());
        }
        return Mono.empty();
    }
}
