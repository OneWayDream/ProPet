package ru.itis.backend.services;

import org.springframework.stereotype.Service;
import ru.itis.backend.dto.AuthorizationForm;
import ru.itis.backend.dto.UserDto;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Override
    public UserDto authorizeUser(AuthorizationForm authorizationForm) {
        return null;
    }

}
