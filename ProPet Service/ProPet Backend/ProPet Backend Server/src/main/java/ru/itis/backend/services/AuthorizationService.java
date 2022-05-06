package ru.itis.backend.services;

import ru.itis.backend.dto.AuthorizationForm;
import ru.itis.backend.dto.AccountDto;

public interface AuthorizationService {

    AccountDto authorizeUser(AuthorizationForm authorizationForm);

}
