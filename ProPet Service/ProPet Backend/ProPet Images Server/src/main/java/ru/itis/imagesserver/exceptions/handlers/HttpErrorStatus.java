package ru.itis.imagesserver.exceptions.handlers;

public enum HttpErrorStatus {

    BANNED_TOKEN(452),
    EXPIRED_TOKEN(454),
    INCORRECT_TOKEN(455),
    ACCESS_DENIED(458),
    IMAGE_LOAD_ERROR(463),
    IMAGE_STORE_ERROR(464),
    INCORRECT_IMAGE_TYPE(463);

    private final int value;

    HttpErrorStatus(int value){
        this.value = value;
    }

    public int value(){
        return value;
    }

}
