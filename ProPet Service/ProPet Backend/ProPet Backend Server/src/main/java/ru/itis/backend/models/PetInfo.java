package ru.itis.backend.models;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsExclude;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pet_info")
//@ToString(exclude = {"user"})
public class PetInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    protected User user;

    @Column(name = "user_id")
    protected Long userId;

    @Column(name = "image_key")
    protected String imageKey;

    protected String nickname;

    @Column(name = "is_deleted")
    protected Boolean isDeleted;

}
