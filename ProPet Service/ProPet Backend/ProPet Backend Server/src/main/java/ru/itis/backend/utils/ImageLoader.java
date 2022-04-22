package ru.itis.backend.utils;

import org.springframework.stereotype.Component;
import ru.itis.backend.dto.CommentAboutSitterDto;
import ru.itis.backend.dto.UserDto;
import ru.itis.backend.dto.PetInfoDto;

import java.awt.image.BufferedImage;

@Component
public class ImageLoader {

    public static BufferedImage loadImageByKey(String key, ImageType imageType) {
        return null;
    }

    public static String getImageKeyForUser(UserDto userDto){
        return userDto.getImageKey();
    }

    public static String getImageKeyForPet(PetInfoDto petInfoDto){
        return petInfoDto.getImageKey();
    }

    public static String getImageKeyCommentAboutSitter(CommentAboutSitterDto commentAboutSitterDto){
        return commentAboutSitterDto.getImageKey();
    }

}
