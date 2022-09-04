package ru.itis.pdfgenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetTransferTreatyData {

    protected Long applyId;

    protected Long customerId;
    protected String customerName;
    protected String customerSurname;
    protected String customerPatronymic;
    protected Integer customerPassportSeries;
    protected Integer customerPassportNumber;
    protected String customerPassportIssuingPlace;
    protected String customerPlaceOfResidence;
    protected String customerPhone;

    protected Long performerId;
    protected String performerName;
    protected String performerSurname;
    protected String performerPatronymic;
    protected Integer performerPassportSeries;
    protected Integer performerPassportNumber;
    protected String performerPassportIssuingPlace;
    protected String performerPlaceOfResidence;
    protected String performerPhone;

    protected Long petId;
    protected String petKind;
    protected String petBreed;
    protected String petNickname;
    protected Integer petYearsAge;
    protected Integer petMonthAge;

    protected int creationDay;
    protected String creationMonth;
    protected int creationYear;
    protected String city;

}
