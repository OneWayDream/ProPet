package ru.itis.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.backend.models.SitterInfo;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SitterInfoDto {

    protected Long id;
    protected Long accountId;
    protected String name;
    protected String surname;
    protected String city;
    protected Integer age;
    protected String infoAbout;
    protected Integer rateOne;
    protected Integer rateTwo;
    protected Integer rateThree;
    protected Integer rateFour;
    protected Integer rateFive;

    public static SitterInfoDto from(SitterInfo info){
        return (info == null) ? null : SitterInfoDto.builder()
                .id(info.getId())
                .accountId(info.getAccountId())
                .name(info.getName())
                .surname(info.getSurname())
                .city(info.getCity())
                .age(info.getAge())
                .infoAbout(info.getInfoAbout())
                .rateOne(info.getRateOne())
                .rateTwo(info.getRateTwo())
                .rateThree(info.getRateThree())
                .rateFour(info.getRateFour())
                .rateFive(info.getRateFive())
                .build();
    }

    public static SitterInfo to(SitterInfoDto infoDto){
        return (infoDto == null) ? null : SitterInfo.builder()
                .id(infoDto.getId())
                .accountId(infoDto.getAccountId())
                .name(infoDto.getName())
                .surname(infoDto.getSurname())
                .city(infoDto.getCity())
                .age(infoDto.getAge())
                .infoAbout(infoDto.getInfoAbout())
                .rateOne(infoDto.getRateOne())
                .rateTwo(infoDto.getRateTwo())
                .rateThree(infoDto.getRateThree())
                .rateFour(infoDto.getRateFour())
                .rateFive(infoDto.getRateFive())
                .isDeleted(false)
                .build();
    }

    public static List<SitterInfoDto> from(List<SitterInfo> infos){
        return infos.stream()
                .map(SitterInfoDto::from)
                .collect(Collectors.toList());
    }

    public static List<SitterInfo> to(List<SitterInfoDto> infos){
        return infos.stream()
                .map(SitterInfoDto::to)
                .collect(Collectors.toList());
    }

}
