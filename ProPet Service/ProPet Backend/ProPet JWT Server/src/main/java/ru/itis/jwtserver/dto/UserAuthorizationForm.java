package ru.itis.jwtserver.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAuthorizationForm {

    protected String login;
    protected String mail;
    protected String password;

}
