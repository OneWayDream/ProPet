package ru.itis.backend.utils;

import ru.itis.backend.exceptions.images.IncorrectImageTypeException;

import java.util.Arrays;

public enum ImageType {

    PNG("image/png", "png"),
    JPEG("image/jpeg", "jpg");

    private final String mediaType;
    private final String value;

    ImageType(String mediaType, String value){
        this.mediaType = mediaType;
        this.value = value;
    }

    public String value(){
        return value;
    }

    public String mediaType(){
        return mediaType;
    }

    public static ImageType get(String mediaType) {
        return Arrays.stream(ImageType.values())
                .filter(env -> env.mediaType.equals(mediaType))
                .findFirst().orElseThrow(IncorrectImageTypeException::new);
    }

}
