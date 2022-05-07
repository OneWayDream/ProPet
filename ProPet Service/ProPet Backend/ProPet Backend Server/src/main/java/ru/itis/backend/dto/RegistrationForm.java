package ru.itis.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationForm {

    protected String login;
    protected String mail;
    protected String password;
    protected String repeatedPassword;
    protected String city;

}
