package ru.itis.backend.services;

import ru.itis.backend.dto.AuthorizationForm;
import ru.itis.backend.dto.UserDto;

public interface AuthorizationService {

    UserDto authorizeUser(AuthorizationForm authorizationForm);

}
