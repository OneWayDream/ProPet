package ru.itis.backend.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "appeal_answer")
public class AppealAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @OneToOne
    @JoinColumn(name = "appeal_id", referencedColumnName = "id",
            nullable = false, insertable = false, updatable = false)
    protected UserAppeal appeal;

    @Column(name = "appeal_id")
    protected Long appealId;

    @Column(name = "send_date", nullable = false)
    protected LocalDate sendDate;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id",
            nullable = false, insertable = false, updatable = false)
    protected Account author;

    @Column(name = "author_id")
    protected Long authorId;

    @Column(nullable = false)
    protected String text;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    protected Boolean isDeleted = false;

}
