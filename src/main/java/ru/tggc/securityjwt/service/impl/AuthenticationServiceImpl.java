package ru.tggc.securityjwt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tggc.securityjwt.api.CodeApi;
import ru.tggc.securityjwt.dto.AuthenticationRq;
import ru.tggc.securityjwt.dto.AuthenticationRs;
import ru.tggc.securityjwt.dto.ChangePasswordDto;
import ru.tggc.securityjwt.dto.RegisterRq;
import ru.tggc.securityjwt.dto.SendCodeDto;
import ru.tggc.securityjwt.dto.VerifyDto;
import ru.tggc.securityjwt.dto.notification.NotificationType;
import ru.tggc.securityjwt.exception.PasswordsNotMatchException;
import ru.tggc.securityjwt.exception.UserNotFoundException;
import ru.tggc.securityjwt.model.Role;
import ru.tggc.securityjwt.model.User;
import ru.tggc.securityjwt.repository.UserRepository;
import ru.tggc.securityjwt.sender.Sender;
import ru.tggc.securityjwt.sender.SenderFactory;
import ru.tggc.securityjwt.service.AuthenticationService;
import ru.tggc.securityjwt.service.JwtService;

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
    public void sendCode(SendCodeDto dto) {
        Sender sender = senderFactory.getSender(dto.type());
        sender.send(dto.email(), dto.type());
    }

    @Override
    public Boolean verifyCode(VerifyDto dto) {
        String code = dto.code();
        String email = dto.email();
        String codeFromDb = codeApi.getCode(email, NotificationType.CHANGE_PASSWORD);
        return code.equals(codeFromDb);
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordDto dto) {
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
