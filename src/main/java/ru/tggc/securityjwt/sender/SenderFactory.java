package ru.tggc.securityjwt.sender;

import org.springframework.stereotype.Service;
import ru.tggc.securityjwt.dto.notification.NotificationType;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SenderFactory {
    private final Map<NotificationType, Sender> senders;

    public SenderFactory(List<Sender> senders) {
        this.senders = senders.stream()
                .collect(Collectors.toMap(Sender::getNotificationType, Function.identity()));
    }

    public Sender getSender(NotificationType type) {
        return senders.get(type);
    }
}
