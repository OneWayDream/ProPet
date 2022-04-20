package ru.itis.jwtserver.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "data_access_user")
public class DataAccessUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String login;

    @Column(name = "hash_password")
    protected String hashPassword;

    @Enumerated(value = EnumType.STRING)
    protected AccessUserState state;

    @Enumerated(value = EnumType.STRING)
    protected AccessUserRole role;

    @Column(name = "redis_id")
    protected String redisId;

    @Column(name = "is_deleted")
    protected Boolean isDeleted;

}
