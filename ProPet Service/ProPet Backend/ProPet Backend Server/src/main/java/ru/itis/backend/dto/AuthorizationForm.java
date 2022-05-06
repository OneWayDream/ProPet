package ru.itis.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorizationForm {

    protected String login;
    protected String mail;
    protected String password;

}
