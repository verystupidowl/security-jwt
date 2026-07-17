package org.tggc.notificationservice.service;

import lombok.NonNull;
import org.tggc.notificationservice.dto.NotificationRq;
import reactor.core.publisher.Mono;

public interface MailSender {

    Mono<@NonNull Void> send(NotificationRq notificationRq);
}
