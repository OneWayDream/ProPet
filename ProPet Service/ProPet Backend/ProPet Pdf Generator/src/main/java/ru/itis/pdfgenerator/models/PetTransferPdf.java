package ru.itis.pdfgenerator.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pet_transfer_pdf")
public class PetTransferPdf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "apply_id", unique = true)
    protected Long applyId;

    @Column(name = "image_key", unique = true, nullable = false)
    protected String imageKey;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    protected Boolean isDeleted = false;

}
