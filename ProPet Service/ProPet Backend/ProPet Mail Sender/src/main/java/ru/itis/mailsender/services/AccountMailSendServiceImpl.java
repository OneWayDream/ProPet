package ru.itis.mailsender.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.itis.mailsender.dto.ActivationMailForm;
import ru.itis.mailsender.utils.EmailUtils;
import ru.itis.mailsender.utils.MailsGenerator;

@Service
public class AccountMailSendServiceImpl implements AccountMailSendService {

    protected final MailsGenerator mailsGenerator;
    protected final EmailUtils emailUtils;

    protected final String serverUrl;
    protected final Integer activationPort;
    protected final String activationHost;
    protected final String fromAddress;

    @Autowired
    public AccountMailSendServiceImpl(
            MailsGenerator mailsGenerator,
            EmailUtils emailUtils,
            @Value("${mail.server-url}") String serverUrl,
            @Value("${mail.activation-port}") Integer activationPort,
            @Value("${mail.activation-host}") String activationHost,
            @Value("${spring.mail.username}") String fromAddress
    ){
        this.mailsGenerator = mailsGenerator;
        this.emailUtils = emailUtils;
        this.serverUrl = serverUrl;
        this.activationPort = activationPort;
        this.activationHost = activationHost;
        this.fromAddress = fromAddress;
    }

    @Override
    public void sendActivationMail(ActivationMailForm form) {
        String confirmMail = mailsGenerator.getMailForConfirm(form,
                serverUrl + ":" + activationPort + activationHost);
        emailUtils.sendMail(form.getMail(), fromAddress, "Registration Confirmation", confirmMail);
    }

}
