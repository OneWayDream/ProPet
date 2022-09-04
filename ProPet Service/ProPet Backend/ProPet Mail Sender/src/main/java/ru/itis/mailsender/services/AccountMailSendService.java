package ru.itis.mailsender.services;

import ru.itis.mailsender.dto.ActivationMailForm;

public interface AccountMailSendService {

    void sendActivationMail(ActivationMailForm form);

}
