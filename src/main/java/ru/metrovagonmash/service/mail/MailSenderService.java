package ru.metrovagonmash.service.mail;

public interface MailSenderService {
    void send(String emailTo, String subject, String message);
}
