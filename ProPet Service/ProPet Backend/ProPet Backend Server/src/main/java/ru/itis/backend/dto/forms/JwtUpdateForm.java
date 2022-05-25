package ru.itis.backend.dto.forms;

import lombok.Builder;
import lombok.Data;
import ru.itis.backend.models.UserRole;
import ru.itis.backend.models.UserState;

@Data
@Builder
public class JwtUpdateForm {

    protected Long accountId;
    protected String login;
    protected String mail;
    protected String hashPassword;
    protected UserRole role;
    protected UserState state;

}
