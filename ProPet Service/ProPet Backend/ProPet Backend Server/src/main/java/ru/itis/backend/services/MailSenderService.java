package ru.itis.backend.services;

import ru.itis.backend.dto.forms.ActivationMailForm;

public interface MailSenderService {

    void sendConfirmMail(ActivationMailForm form);

}
