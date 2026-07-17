package org.tggc.authenticationservice.service.impl;

import lombok.NonNull;
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
import org.tggc.authenticationservice.exception.UserForbiddenException;
import org.tggc.authenticationservice.exception.UserNotFoundException;
import org.tggc.authenticationservice.mapper.AuthMapper;
import org.tggc.authenticationservice.model.Role;
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
import org.tggc.userapi.api.AuthenticationApi;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.EnumSet;

import static org.tggc.notificationapi.dto.NotificationType.CHANGED_PASSWORD;
import static org.tggc.notificationapi.dto.NotificationType.CHANGE_PASSWORD_CONFIRMATION;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationApi authenticationApi;
    private final UserRepository userRepository;
    private final SenderFactory senderFactory;
    private final CodeApi codeApi;
    private final PasswordService passwordService;
    private final PasswordValidator passwordValidator;
    private final EmailCodeValidator emailCodeValidator;
    private final UserValidator userValidator;
    private final AuthMapper authMapper;

    @Override
    @Transactional
    public Mono<@NonNull AuthenticationRs> register(RegisterRq rq) {
        return passwordValidator.validate(new ValidationRq<>(rq.password(), rq.passwordConfirmation()))
                .then(authenticationApi.getUserByEmail(rq.email())
                        .flatMap(u -> Mono.error(new UserAlreadyCreatedException(u.email())))
                        .switchIfEmpty(emailCodeValidator.validate(new ValidationRq<>(
                                rq.email(),
                                rq.verificationCode()
                        )))
                )
                .then(authenticationApi.saveUser(rq));
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<@NonNull AuthenticationRs> authenticate(AuthenticationRq rq) {
        return authenticationApi.getUserByEmail(rq.email())
                .flatMap(u -> userRepository.findByEmail(u.email()))
                .flatMap(u -> userValidator.validate(new ValidationRq<>(u, rq.password())))
                .map(authMapper::toDto);
    }

    @Override
    public Mono<@NonNull Void> sendCode(SendCodeRq dto) {
        Sender sender = senderFactory.getSender(dto.type());
        return sender.send(dto.email(), dto.type());
    }

    @Override
    @Transactional
    public Mono<@NonNull Void> changePassword(ChangePasswordRq dto) {
        return passwordValidator.validate(new ValidationRq<>(dto.password(), dto.passwordConfirmation()))
                .then(userRepository.findByEmail(dto.email())
                        .switchIfEmpty(Mono.error(new UserNotFoundException(dto.email())))
                        .flatMap(user -> {
                            user.setPassword(passwordService.hash(dto.password()));
                            user.setUpdatedAt(LocalDateTime.now());
                            return userRepository.save(user);
                        })
                        .then(codeApi.deleteCode(dto.email(), CHANGE_PASSWORD_CONFIRMATION))
                        .then(sendChangedPasswordNotification(dto.email())));
    }

    @Transactional
    @Override
    public Mono<@NonNull Void> blockUser(Long userId, Boolean block, Long blockerId) {
        return userRepository.findById(blockerId)
                .flatMap(u -> {
                    EnumSet<Role> requiredRoles = EnumSet.of(Role.ADMIN);
                    if (!requiredRoles.contains(u.getRole())) {
                        return Mono.error(new UserForbiddenException(u.getEmail()));
                    }
                    return userRepository.findById(userId)
                            .switchIfEmpty(Mono.error(new UserNotFoundException("User not found")))
                            .doOnNext(uc -> uc.setBlocked(block));
                })
                .then();
    }

    private Mono<@NonNull Void> sendChangedPasswordNotification(String email) {
        return senderFactory.getSender(CHANGED_PASSWORD)
                .send(email, CHANGED_PASSWORD);
    }
}
