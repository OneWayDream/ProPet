package ru.itis.backend.dto.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.backend.dto.rest.AccountRestDto;
import ru.itis.backend.models.TreatyInfo;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TreatyInfoDto {

    protected Long id;
    protected Long accountId;
    protected String name;
    protected String surname;
    protected String patronymic;
    protected Integer passportSeries;
    protected Integer passportNumber;
    protected String passportIssuingPlace;
    protected String placeOfResidence;
    protected String phone;

    public static TreatyInfoDto from(TreatyInfo treatyInfo){
        return (treatyInfo == null) ? null : TreatyInfoDto.builder()
                .id(treatyInfo.getId())
                .accountId(treatyInfo.getAccountId())
                .name((treatyInfo.getAccount() == null) ? null : treatyInfo.getAccount().getName())
                .surname((treatyInfo.getAccount() == null) ? null : treatyInfo.getAccount().getSurname())
                .patronymic(treatyInfo.getPatronymic())
                .passportSeries(treatyInfo.getPassportSeries())
                .passportNumber(treatyInfo.getPassportNumber())
                .passportIssuingPlace(treatyInfo.getPassportIssuingPlace())
                .placeOfResidence(treatyInfo.getPlaceOfResidence())
                .phone((treatyInfo.getAccount() == null) ? null : treatyInfo.getAccount().getPhone())
                .build();
    }

    public static TreatyInfo to(TreatyInfoDto treatyInfo){
        return (treatyInfo == null) ? null : TreatyInfo.builder()
                .id(treatyInfo.getId())
                .accountId(treatyInfo.getAccountId())
                .patronymic(treatyInfo.getPatronymic())
                .passportSeries(treatyInfo.getPassportSeries())
                .passportNumber(treatyInfo.getPassportNumber())
                .passportIssuingPlace(treatyInfo.getPassportIssuingPlace())
                .placeOfResidence(treatyInfo.getPlaceOfResidence())
                .build();
    }

    public static List<TreatyInfoDto> from(List<TreatyInfo> treatyInfos){
        return (treatyInfos == null) ? null : treatyInfos.stream()
                .map(TreatyInfoDto::from)
                .collect(Collectors.toList());
    }

    public static List<TreatyInfo> to(List<TreatyInfoDto> treatyInfos){
        return (treatyInfos == null) ? null : treatyInfos.stream()
                .map(TreatyInfoDto::to)
                .collect(Collectors.toList());
    }

}
