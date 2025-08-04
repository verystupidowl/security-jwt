package ru.tggc.securityjwt.service;

public interface MailSender {

    void sendEmail(String to, String text);
}
