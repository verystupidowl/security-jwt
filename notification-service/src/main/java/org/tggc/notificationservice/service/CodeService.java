package org.tggc.notificationservice.service;

import org.tggc.notificationapi.dto.NotificationType;

public interface CodeService {

    String getCode(String email);

    void deleteCode(String email);

    NotificationType getNotificationType();
}
