package ru.itis.backend.dto.app;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.itis.backend.models.SitterInfo;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SitterSearchDto extends SitterInfoDto{

    protected String login;
    protected String mail;
    protected String name;
    protected String surname;
    protected String city;

    public static SitterSearchDto fromSearch(SitterInfo info){
        return (info == null) ? null : SitterSearchDto.builder()
                .id(info.getId())
                .accountId(info.getAccountId())
                .age(info.getAge())
                .infoAbout(info.getInfoAbout())
                .animals(info.getAnimals())
                .services(info.getServices())
                .sitterStatus(info.getSitterStatus())
                .rateOne(info.getRateOne())
                .rateTwo(info.getRateTwo())
                .rateThree(info.getRateThree())
                .rateFour(info.getRateFour())
                .rateFive(info.getRateFive())
                .rating(info.getRating())
                .login(info.getAccount().getLogin())
                .mail(info.getAccount().getMail())
                .name(info.getAccount().getName())
                .surname(info.getAccount().getSurname())
                .city(info.getAccount().getCity())
                .build();
    }

    public static List<SitterSearchDto> fromSearch(List<SitterInfo> infos){
        return infos.stream()
                .map(SitterSearchDto::fromSearch)
                .collect(Collectors.toList());
    }

}
