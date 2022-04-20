package ru.itis.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.backend.models.PetInfo;
import ru.itis.backend.models.User;
import ru.itis.backend.utils.ImageLoader;
import ru.itis.backend.utils.ImageType;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetInfoDto {

    protected Long id;
    protected Long userId;
    protected String imageKey;
    protected String nickname;

    public static PetInfoDto from(PetInfo info){
        return PetInfoDto.builder()
                .id(info.getId())
                .userId(info.getUserId())
                .imageKey(info.getImageKey())
                .nickname(info.getNickname())
                .build();
    }

    public static PetInfo to(PetInfoDto infoDto){
        return PetInfo.builder()
                .id(infoDto.getId())
                .imageKey(ImageLoader.getImageKeyForPet(infoDto))
                .nickname(infoDto.getNickname())
                .userId(infoDto.getUserId())
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
