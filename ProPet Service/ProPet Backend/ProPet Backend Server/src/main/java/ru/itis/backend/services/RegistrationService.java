package ru.itis.backend.services;

import ru.itis.backend.dto.RegistrationForm;
import ru.itis.backend.dto.AccountDto;

public interface RegistrationService {

    AccountDto registerNewAccount(RegistrationForm registrationForm);

}
