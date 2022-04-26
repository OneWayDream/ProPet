package ru.itis.jwtserver.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.itis.jwtserver.entities.JwtRole;
import ru.itis.jwtserver.entities.JwtState;

@Data
@NoArgsConstructor
@SuperBuilder
public abstract class JwtEntityDto {

    protected Long id;
    protected String login;
    protected String hashPassword;
    protected JwtState state;
    protected JwtRole role;
    protected String redisId;

}
