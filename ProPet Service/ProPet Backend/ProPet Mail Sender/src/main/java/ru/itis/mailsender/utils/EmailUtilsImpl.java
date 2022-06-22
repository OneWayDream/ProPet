package ru.itis.mailsender.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Component
@RequiredArgsConstructor
public class EmailUtilsImpl implements EmailUtils {

    protected final JavaMailSender javaMailSender;
    protected final ExecutorService executorService;

    @Override
    public void sendMail(String toAddress, String fromAddress, String subject, String text) {
        executorService.submit(() -> javaMailSender.send(mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(fromAddress);
            messageHelper.setTo(toAddress);
            messageHelper.setSubject(subject);
            messageHelper.setText(text, true);
        }));

    }

}
