package org.tggc.notificationapi.dto;

public enum NotificationType {
    CHANGE_PASSWORD_CONFIRMATION("change-password-confirmation"),
    EMAIL_CONFIRMATION("email-confirmation"),
    TWO_FACTOR("two-factor"),
    CHANGED_PASSWORD("changed-password"),
    START_EVENT(null);

    private final String key;

    NotificationType(String key) {
        this.key = key;
    }

    public String getKey(String email) {
        return this.key + "::" + email;
    }
}
