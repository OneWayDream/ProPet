package ru.itis.backend.models;

import lombok.*;
import ru.itis.backend.exceptions.pdf.EmptyProfileFieldException;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "treaty_info")
public class TreatyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    protected Account account;

    @Column(name = "account_id", nullable = false)
    protected Long accountId;

    @Column(nullable = false)
    protected String patronymic;

    @Column(name = "passport_series", nullable = false)
    protected Integer passportSeries;

    @Column(name = "passport_number", nullable = false)
    protected Integer passportNumber;

    @Column(name = "passport_issuing_Place", nullable = false)
    protected String passportIssuingPlace;

    @Column(name = "place_of_residence", nullable = false)
    protected String placeOfResidence;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    protected Boolean isDeleted = false;

    public void checkTreatyFields(){
        String message = null;
        if (account.getName() == null){
            message = "Name is not specified";
        } else if (account.getSurname() == null){
            message = "Surname is not specified";
        } else if (account.getPhone() == null){
            message = "Phone is not specified";
        }
        if (message != null){
            throw new EmptyProfileFieldException(message);
        }
    }

}
