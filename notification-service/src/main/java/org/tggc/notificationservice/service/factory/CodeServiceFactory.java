package org.tggc.notificationservice.service.factory;

import org.springframework.stereotype.Service;
import org.tggc.notificationapi.dto.NotificationType;
import org.tggc.notificationservice.service.CodeService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CodeServiceFactory {
    private final Map<NotificationType, CodeService> codeServiceMap;

    public CodeServiceFactory(List<CodeService> codeServices) {
        this.codeServiceMap = codeServices.stream()
                .collect(Collectors.toMap(CodeService::getNotificationType, Function.identity()));
    }

    public CodeService getCodeService(NotificationType notificationType) {
        return codeServiceMap.get(notificationType);
    }
}
