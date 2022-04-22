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
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    protected User user;

    @Column(name = "user_id")
    protected Long userId;

    @Column(name = "send_date")
    protected Date sendDate;

    protected String text;

    @Column(name = "is_closed")
    protected Boolean isClosed;

    @Column(name = "is_deleted")
    protected Boolean isDeleted;

    @OneToOne(targetEntity = AppealAnswer.class, mappedBy = "appeal",
            cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected AppealAnswer appealAnswer;


}
