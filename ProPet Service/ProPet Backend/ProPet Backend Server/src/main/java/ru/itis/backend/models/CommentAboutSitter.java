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
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    protected User user;

    @Column(name = "user_id")
    protected Long userId;

    @Column(name = "image_key")
    protected String imageKey;

    protected Integer rate;

    protected String review;

    @Column(name = "is_deleted")
    protected Boolean isDeleted;

}
