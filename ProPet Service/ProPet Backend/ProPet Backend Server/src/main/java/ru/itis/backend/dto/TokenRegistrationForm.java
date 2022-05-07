package ru.itis.backend.dto;

import lombok.Builder;
import lombok.Data;
import ru.itis.backend.models.UserRole;
import ru.itis.backend.models.UserState;

@Data
@Builder
public class TokenRegistrationForm {

    protected Long accountId;
    protected String login;
    protected String mail;
    protected String hashPassword;
    protected UserState state;
    protected UserRole role;

}
