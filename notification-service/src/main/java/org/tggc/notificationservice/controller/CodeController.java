package org.tggc.notificationservice.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tggc.notificationapi.api.CodeApi;
import org.tggc.notificationapi.dto.NotificationType;
import org.tggc.notificationservice.service.CodeService;
import org.tggc.notificationservice.service.factory.CodeServiceFactory;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/code")
public class CodeController implements CodeApi {
    private final CodeServiceFactory factory;

    @Override
    public Mono<@NonNull String> getCode(@RequestParam("email") String email, @RequestParam NotificationType notificationType) {
        CodeService codeService = factory.getCodeService(notificationType);
        return codeService.getCode(email);
    }

    @Override
    public Mono<@NonNull Void> deleteCode(@RequestParam("email") String email, @RequestParam NotificationType notificationType) {
        CodeService codeService = factory.getCodeService(notificationType);
        return codeService.deleteCode(email);
    }
}
