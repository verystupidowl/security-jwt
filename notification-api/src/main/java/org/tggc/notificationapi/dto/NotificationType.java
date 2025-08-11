package org.tggc.notificationapi.dto;

public enum NotificationType {
    CHANGE_PASSWORD("change-password"),
    EMAIL_CONFIRMATION("email-confirmation"),
    TWO_FACTOR("two-factor"),
    START_EVENT(null);

    private final String key;

    NotificationType(String key) {
        this.key = key;
    }

    public String getKey(String email) {
        return this.key + "::" + email;
    }
}
