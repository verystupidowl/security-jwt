package org.tggc.authenticationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tggc.authenticationservice.dto.request.AuthenticationRq;
import org.tggc.authenticationservice.dto.request.ChangePasswordRq;
import org.tggc.authenticationservice.dto.request.RegisterRq;
import org.tggc.authenticationservice.dto.request.SendCodeRq;
import org.tggc.authenticationservice.dto.request.VerifyRq;
import org.tggc.authenticationservice.dto.response.AuthenticationRs;
import org.tggc.authenticationservice.exception.PasswordsNotMatchException;
import org.tggc.authenticationservice.exception.UserNotFoundException;
import org.tggc.authenticationservice.model.Role;
import org.tggc.authenticationservice.model.User;
import org.tggc.authenticationservice.repository.UserRepository;
import org.tggc.authenticationservice.sender.Sender;
import org.tggc.authenticationservice.sender.SenderFactory;
import org.tggc.authenticationservice.service.AuthenticationService;
import org.tggc.authenticationservice.service.JwtService;
import org.tggc.notificationapi.api.CodeApi;
import org.tggc.notificationapi.dto.NotificationType;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final SenderFactory senderFactory;
    private final CodeApi codeApi;

    @Override
    public AuthenticationRs register(RegisterRq request) {
        if (!request.password().equals(request.passwordConfirmation())) {
            throw new PasswordsNotMatchException("Passwords do not match");
        }
        User user = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .twoFactorEnabled(request.twoFactorEnabled())
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationRs(jwtToken);
    }

    @Override
    public AuthenticationRs authenticate(AuthenticationRq request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException(request.email()));
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationRs(jwtToken);
    }

    @Override
    public void sendCode(SendCodeRq dto) {
        Sender sender = senderFactory.getSender(dto.type());
        sender.send(dto.email(), dto.type());
    }

    @Override
    public Boolean verifyCode(VerifyRq dto) {
        String code = dto.code();
        String email = dto.email();
        String codeFromDb = codeApi.getCode(email, NotificationType.CHANGE_PASSWORD);
        return code.equals(codeFromDb);
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRq dto) {
        if (!dto.password().equals(dto.passwordConfirmation())) {
            throw new PasswordsNotMatchException("Passwords do not match");
        }
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new UserNotFoundException(dto.email()));
        user.setPassword(passwordEncoder.encode(dto.password()));
        codeApi.deleteCode(dto.email(), NotificationType.CHANGE_PASSWORD);
        userRepository.save(user);
    }
}
