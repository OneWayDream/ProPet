package ru.itis.backend.services;

import ru.itis.backend.dto.RegistrationForm;
import ru.itis.backend.dto.UserDto;

public interface RegistrationService {

    UserDto registerNewUser(RegistrationForm registrationForm);

}
