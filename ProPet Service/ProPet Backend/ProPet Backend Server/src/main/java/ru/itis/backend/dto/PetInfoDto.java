package ru.itis.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.backend.models.PetInfo;
import ru.itis.backend.utils.ImageLoader;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetInfoDto {

    protected Long id;
    protected Long accountId;
    protected String imageKey;
    protected String nickname;

    public static PetInfoDto from(PetInfo info){
        return (info == null) ? null : PetInfoDto.builder()
                .id(info.getId())
                .accountId(info.getAccountId())
                .imageKey(info.getImageKey())
                .nickname(info.getNickname())
                .build();
    }

    public static PetInfo to(PetInfoDto infoDto){
        return (infoDto == null) ? null : PetInfo.builder()
                .id(infoDto.getId())
                .imageKey(ImageLoader.getImageKeyForPet(infoDto))
                .nickname(infoDto.getNickname())
                .accountId(infoDto.getAccountId())
                .isDeleted(false)
                .build();
    }

    public static List<PetInfoDto> from(List<PetInfo> petInfos){
        return petInfos.stream()
                .map(PetInfoDto::from)
                .collect(Collectors.toList());
    }

    public static List<PetInfo> to(List<PetInfoDto> infoDtos){
        return infoDtos.stream()
                .map(PetInfoDto::to)
                .collect(Collectors.toList());
    }

}
