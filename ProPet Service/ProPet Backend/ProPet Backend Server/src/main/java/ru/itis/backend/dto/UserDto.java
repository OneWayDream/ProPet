package ru.itis.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserDto {

    protected Long id;
    protected String login;
    protected String hashPassword;
    protected String male;
    protected Date lastLogin;
    protected Boolean isActivated;
    protected Boolean isBanned;
    protected Boolean isDeleted;
    protected Date registrationData;
    protected String country;

}
