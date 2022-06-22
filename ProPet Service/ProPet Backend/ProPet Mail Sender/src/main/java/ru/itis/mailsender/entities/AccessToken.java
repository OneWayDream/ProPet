package ru.itis.mailsender.entities;

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

    protected Long accountId;
    protected String login;
    protected Date expiration;
    protected UserRole role;
    protected UserState state;

    public boolean isActive() {
        return this.state == UserState.ACTIVE;
    }

    public boolean isBanned() {
        return this.state == UserState.BANNED;
    }

    public boolean isAdmin() {
        return this.role == UserRole.ADMIN;
    }
}
