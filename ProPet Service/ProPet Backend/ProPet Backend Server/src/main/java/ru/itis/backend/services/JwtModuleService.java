package ru.itis.backend.services;

import ru.itis.backend.dto.forms.JwtUpdateForm;
import ru.itis.backend.dto.forms.TokenRegistrationForm;

public interface JwtModuleService {

    void deleteUserOnAuthorizationServer(Long id);
    void updateUserOnAuthorizationServer(JwtUpdateForm form);
    void registerOnAuthorizationServer(TokenRegistrationForm form);

}
