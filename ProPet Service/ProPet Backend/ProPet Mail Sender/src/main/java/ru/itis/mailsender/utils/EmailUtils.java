package ru.itis.mailsender.utils;

public interface EmailUtils {

    void sendMail(String toAddress, String fromAddress, String subject, String text);

}
