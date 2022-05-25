package ru.itis.jwtserver.models;

import lombok.*;
import ru.itis.jwtserver.entities.JwtRole;
import ru.itis.jwtserver.entities.JwtState;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "jwt_user")
public class JwtUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String login;

    protected String mail;

    @Column(name = "account_id", nullable = false, unique = true)
    protected Long accountId;

    @Column(name = "hash_password")
    protected String hashPassword;

    @Enumerated(value = EnumType.STRING)
    protected JwtState state;

    @Enumerated(value = EnumType.STRING)
    protected JwtRole role;

    @Column(name = "redis_id")
    protected String redisId;

    @Column(name = "is_deleted")
    protected Boolean isDeleted;

}
