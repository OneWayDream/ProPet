package ru.itis.backend.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_appeal")
public class UserAppeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    protected Account account;

    @Column(name = "account_id")
    protected Long accountId;

    @Column(name = "send_date", nullable = false)
    protected Date sendDate;

    @Column(nullable = false)
    protected String text;

    @Column(name = "is_closed", nullable = false)
    protected Boolean isClosed = false;

    @Column(name = "is_deleted", nullable = false)
    protected Boolean isDeleted = false;

    @OneToOne(targetEntity = AppealAnswer.class, mappedBy = "appeal",
            cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected AppealAnswer appealAnswer;


}
