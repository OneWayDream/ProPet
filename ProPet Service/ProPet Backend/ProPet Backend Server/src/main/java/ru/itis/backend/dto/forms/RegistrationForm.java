package ru.itis.backend.dto.forms;

import lombok.Builder;
import lombok.Data;
import ru.itis.backend.validation.ValidPasswords;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@ValidPasswords(
        password = "password",
        repeatedPassword = "repeatedPassword"
)
public class RegistrationForm {

    @NotBlank
    @Size(min = 4, max = 50)
    protected String login;

    @NotBlank
    @Size(max = 50)
    @Email
    protected String mail;

    @NotBlank
    @Size(min = 8, max = 50)
    protected String password;

    @NotBlank
    @Size(min = 8, max = 50)
    protected String repeatedPassword;

    @NotBlank
    protected String city;

}
