package org.tggc.notificationservice.service.factory;

import org.springframework.stereotype.Service;
import org.tggc.notificationapi.dto.NotificationType;
import org.tggc.notificationservice.service.SenderService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SenderFactory {
    private final Map<NotificationType, SenderService> senderMap;

    public SenderFactory(List<SenderService> senders) {
        this.senderMap = senders.stream()
                .collect(Collectors.toMap(SenderService::getNotificationType, Function.identity()));
    }

    public SenderService getSenderService(NotificationType notificationType) {
        return senderMap.get(notificationType);
    }
}
