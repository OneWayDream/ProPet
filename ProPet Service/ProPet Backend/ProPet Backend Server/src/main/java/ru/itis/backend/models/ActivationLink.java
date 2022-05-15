package ru.itis.backend.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "activation_link")
public class ActivationLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id",
            nullable = false, insertable = false, updatable = false)
    protected Account account;

    @Column(name = "account_id", unique = true, nullable = false)
    protected Long accountId;

    @Column(name = "link_value", unique = true, nullable = false)
    protected String linkValue;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    protected Boolean isDeleted = false;

}
