package org.tggc.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tggc.notificationapi.api.CodeApi;
import org.tggc.notificationapi.dto.NotificationType;
import org.tggc.notificationservice.service.CodeService;
import org.tggc.notificationservice.service.factory.CodeServiceFactory;

@RestController
@RequiredArgsConstructor
@RequestMapping("/code")
public class CodeController implements CodeApi {
    private final CodeServiceFactory factory;

    @Override
    public String getCode(@RequestParam("email") String email, @RequestParam NotificationType notificationType) {
        CodeService codeService = factory.getCodeService(notificationType);
        return codeService.getCode(email);
    }

    @Override
    public void deleteCode(@RequestParam("email") String email, @RequestParam NotificationType notificationType) {
        CodeService codeService = factory.getCodeService(notificationType);
        codeService.deleteCode(email);
    }
}
