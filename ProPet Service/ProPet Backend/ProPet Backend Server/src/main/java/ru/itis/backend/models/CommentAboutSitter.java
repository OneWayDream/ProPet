package ru.itis.backend.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comment_about_sitter")
public class CommentAboutSitter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "sitter_info_id", referencedColumnName = "id",
            nullable = false, insertable = false, updatable = false)
    protected SitterInfo sitterInfo;

    @Column(name = "sitter_info_id")
    protected Long sitterInfoId;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    protected Account account;

    @Column(name = "account_id")
    protected Long accountId;

    @Column(name = "image_key", nullable = false)
    protected String imageKey = "default.png";

    @Column(nullable = false)
    protected Integer rate;

    protected String review;

    @Column(name = "is_deleted", nullable = false)
    protected Boolean isDeleted = false;

}
