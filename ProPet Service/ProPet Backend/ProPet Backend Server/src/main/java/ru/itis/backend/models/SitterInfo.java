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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    protected Account account;

    @Column(name = "account_id")
    protected Long accountId;

    protected Integer age;

    @Column(name = "info_about")
    protected String infoAbout;

    protected String animals;

    protected String services;

    @Column(name = "rate_1", nullable = false)
    protected Integer rateOne;

    @Column(name = "rate_2", nullable = false)
    protected Integer rateTwo;

    @Column(name = "rate_3", nullable = false)
    protected Integer rateThree;

    @Column(name = "rate_4", nullable = false)
    protected Integer rateFour;

    @Column(name = "rate_5", nullable = false)
    protected Integer rateFive;

    @Column(name = "rating", nullable = false)
    protected Double rating;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    protected Boolean isDeleted = false;

    @Column(name = "sitter_status", nullable = false)
    @Builder.Default
    protected Boolean sitterStatus = true;

    @Version
    protected Integer version;

}
