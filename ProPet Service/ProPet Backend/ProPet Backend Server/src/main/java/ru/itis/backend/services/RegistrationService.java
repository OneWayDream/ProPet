package ru.itis.backend.services;

import ru.itis.backend.dto.forms.RegistrationForm;
import ru.itis.backend.dto.app.AccountDto;

public interface RegistrationService {

    AccountDto registerNewAccount(RegistrationForm registrationForm);

}
