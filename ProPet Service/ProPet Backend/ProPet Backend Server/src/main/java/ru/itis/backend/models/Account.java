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
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false, unique = true)
    protected String mail;

    @Column(nullable = false, unique = true)
    protected String login;

    @Column(name = "hash_password", nullable = false, unique = true)
    protected String hashPassword;

    @Column(name = "last_login_date")
    protected Date lastLogin;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    protected UserState state;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    protected UserRole role;

    protected String name;

    protected String surname;

    protected String city;

    protected String phone;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    protected Boolean isDeleted = false;

    @Column(name = "registration_date", nullable = false)
    protected Date registrationDate;

    @OneToMany(targetEntity = PetInfo.class, mappedBy = "account", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    protected List<PetInfo> pets;

    @OneToMany(targetEntity = CommentAboutSitter.class, mappedBy = "account",
            cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<CommentAboutSitter> comments;

    @OneToMany(targetEntity = UserAppeal.class, mappedBy = "account",
            cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<UserAppeal> appeals;

    @OneToOne(targetEntity = SitterInfo.class, mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected SitterInfo sitterInfo;

    @OneToOne(targetEntity = ActivationLink.class, mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected ActivationLink activationLink;

}
