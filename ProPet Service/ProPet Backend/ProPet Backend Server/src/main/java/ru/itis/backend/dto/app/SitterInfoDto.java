package ru.itis.backend.dto.app;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.itis.backend.models.SitterInfo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SitterInfoDto {

    protected Long id;
    protected Long accountId;

    @Positive
    @Max(150)
    protected Integer age;

    @Size(min = 10, max = 200)
    protected String infoAbout;

    @Size(min = 1, max = 200)
    protected String animals;

    @Size(min = 1, max = 200)
    protected String services;

    protected Boolean sitterStatus;
    protected Integer rateOne;
    protected Integer rateTwo;
    protected Integer rateThree;
    protected Integer rateFour;
    protected Integer rateFive;
    protected Double rating;

    public static SitterInfoDto from(SitterInfo info){
        return (info == null) ? null : SitterInfoDto.builder()
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
                .build();
    }

    public static SitterInfo to(SitterInfoDto infoDto){
        return (infoDto == null) ? null : SitterInfo.builder()
                .id(infoDto.getId())
                .accountId(infoDto.getAccountId())
                .age(infoDto.getAge())
                .infoAbout(infoDto.getInfoAbout())
                .animals(infoDto.getAnimals())
                .services(infoDto.getServices())
                .sitterStatus(infoDto.getSitterStatus())
                .build();
    }

    public static SitterInfo toRest(SitterInfoDto infoDto){
        if (infoDto == null){
            return null;
        }
        SitterInfo sitterInfo = SitterInfoDto.to(infoDto);
        sitterInfo.setRateOne(infoDto.getRateOne());
        sitterInfo.setRateTwo(infoDto.getRateTwo());
        sitterInfo.setRateThree(infoDto.getRateThree());
        sitterInfo.setRateFour(infoDto.getRateFour());
        sitterInfo.setRateFive(infoDto.getRateFive());
        sitterInfo.setRating(infoDto.getRating());
        return sitterInfo;
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

    public static List<SitterInfo> toRest(List<SitterInfoDto> infos){
        return infos.stream()
                .map(SitterInfoDto::toRest)
                .collect(Collectors.toList());
    }

}
