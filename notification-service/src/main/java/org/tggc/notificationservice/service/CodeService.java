package org.tggc.notificationservice.service;

import lombok.NonNull;
import org.tggc.notificationapi.dto.NotificationType;
import reactor.core.publisher.Mono;

public interface CodeService {

    Mono<@NonNull String> getCode(String email);

    Mono<@NonNull Void> deleteCode(String email);

    NotificationType getNotificationType();
}
