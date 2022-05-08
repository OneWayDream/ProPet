package ru.itis.backend.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pet_info")
public class PetInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    protected Account account;

    @Column(name = "account_id", nullable = false)
    protected Long accountId;

    @Column(nullable = false)
    protected String nickname;

    @Column(name = "is_deleted", nullable = false)
    protected Boolean isDeleted;

}
