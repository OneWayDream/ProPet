package ru.itis.backend.dto.forms;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActivationMailForm {

    protected String mail;
    protected String login;
    protected String activationLink;

}
