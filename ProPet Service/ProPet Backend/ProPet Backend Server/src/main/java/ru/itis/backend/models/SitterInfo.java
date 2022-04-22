package ru.itis.backend.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sitter_info")
public class SitterInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    protected User user;

    @Column(name = "user_id")
    protected Long userId;

    protected String name;

    protected String surname;

    protected String city;

    protected Integer age;

    @Column(name = "info_about")
    protected String infoAbout;

    @Column(name = "rate_1")
    protected Integer rateOne;

    @Column(name = "rate_2")
    protected Integer rateTwo;

    @Column(name = "rate_3")
    protected Integer rateThree;

    @Column(name = "rate_4")
    protected Integer rateFour;

    @Column(name = "rate_5")
    protected Integer rateFive;

    @Column(name = "is_deleted")
    protected Boolean isDeleted;

    @OneToMany(targetEntity = CommentAboutSitter.class, mappedBy = "user",
            cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<PetInfo> pets;

}
