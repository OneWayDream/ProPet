package ru.itis.mailsender.utils;

import ru.itis.mailsender.dto.ActivationMailForm;

public interface MailsGenerator {

    String getMailForConfirm(ActivationMailForm form, String activationUrl);

}
