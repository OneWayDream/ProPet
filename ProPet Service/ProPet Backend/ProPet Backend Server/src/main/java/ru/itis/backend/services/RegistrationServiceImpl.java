package ru.itis.backend.services;

import org.springframework.stereotype.Service;
import ru.itis.backend.dto.RegistrationForm;
import ru.itis.backend.dto.UserDto;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public UserDto registerNewUser(RegistrationForm registrationForm) {
        return null;
    }

}
