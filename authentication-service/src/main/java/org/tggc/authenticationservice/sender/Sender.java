package org.tggc.authenticationservice.sender;

import org.tggc.notificationapi.dto.NotificationType;
import reactor.core.publisher.Mono;

public interface Sender {

    Mono<Void> send(String email, NotificationType type);

    NotificationType getNotificationType();
}
