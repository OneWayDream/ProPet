package ru.itis.jwtserver.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccessToken {

    protected Long id;
    protected String login;
    protected Date expiration;
    protected JwtRole role;
    protected JwtState state;

    public boolean isActive() {
        return this.state == JwtState.ACTIVE;
    }

    public boolean isBanned() {
        return this.state == JwtState.BANNED;
    }

    public boolean isAdmin() {
        return this.role == JwtRole.ADMIN;
    }
}
