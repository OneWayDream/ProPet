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
@Table(name = "pet_transfer_apply")
public class PetTransferApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    protected Account customer;

    @Column(name = "customer_id")
    protected Long customerId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "performer_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    protected Account performer;

    @Column(name = "performer_id")
    protected Long performerId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    protected PetInfo pet;

    @Column(name = "pet_id")
    protected Long petId;

    @Builder.Default
    @Column(name = "customer_agreement", nullable = false)
    protected Boolean customerAgreement = false;

    @Builder.Default
    @Column(name = "performer_agreement", nullable = false)
    protected Boolean performerAgreement = false;

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    protected PetTransferApplyStatus status = PetTransferApplyStatus.UNCONFIRMED;

    @Column(name = "creation_date")
    protected LocalDate creationDate;

    @Column(nullable = false)
    protected String city;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    protected Boolean isDeleted = false;

}
