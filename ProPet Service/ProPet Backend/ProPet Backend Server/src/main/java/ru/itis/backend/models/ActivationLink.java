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

    @Column(name = "account_id", unique = true)
    protected Long accountId;

    @Column(name = "link_value", unique = true)
    protected String linkValue;

    @Column(name = "is_deleted")
    protected Boolean isDeleted = false;

}
