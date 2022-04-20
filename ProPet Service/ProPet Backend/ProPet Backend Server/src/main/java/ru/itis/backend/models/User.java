package ru.itis.backend.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "account")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String mail;

    protected String login;

    @Column(name = "hash_password")
    protected String hashPassword;

    @Column(name = "last_login_date")
    protected Date lastLogin;

    @Enumerated(value = EnumType.STRING)
    protected UserState state;

    @Column(name = "is_deleted")
    protected Boolean isDeleted = false;

    @Column(name = "registration_date")
    protected Date registrationDate;

    protected String country;

    @Column(name = "image_key")
    protected String imageKey = "default.png";

    @OneToMany(targetEntity = PetInfo.class, mappedBy = "user", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    protected List<PetInfo> pets;

}
