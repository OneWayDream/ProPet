package ru.itis.imagesserver.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pet_image")
public class PetImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "account_id", unique = true)
    protected Long accountId;

    @Column(name = "pet_id", unique = true)
    protected Long petId;

    @Column(name = "image_key", unique = true, nullable = false)
    protected String imageKey;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    protected Boolean isDeleted = false;

}
