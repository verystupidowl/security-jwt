package org.tggc.authenticationservice.sender;

import lombok.NonNull;
import org.tggc.notificationapi.dto.NotificationType;
import reactor.core.publisher.Mono;

public interface Sender {

    Mono<@NonNull Void> send(String email, NotificationType type);

    NotificationType getNotificationType();
}
