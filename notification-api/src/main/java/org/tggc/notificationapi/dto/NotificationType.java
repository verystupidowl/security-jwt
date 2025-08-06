package org.tggc.notificationapi.dto;

public enum NotificationType {
    CHANGE_PASSWORD("change-password"),
    EMAIL_CONFIRMATION("email-confirmation"),
    TWO_FACTOR("two-factor");

    private final String key;

    NotificationType(String key) {
        this.key = key;
    }

    public String getKey(String email) {
        return this.key + "::" + email;
    }
}
