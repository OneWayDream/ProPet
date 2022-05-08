package ru.itis.backend.exceptions.handlers;

public enum HttpErrorStatus {

    BANNED_TOKEN(452),
    DIFFERENT_PASSWORDS(453),
    EXPIRED_TOKEN(454),
    INCORRECT_TOKEN(455),
    EXISTED_LOGIN(456),
    EXISTED_MAIL(457),
    ACCESS_DENIED(458),
    INCORRECT_SEARCH_ORDER(459),
    INCORRECT_SEARCH_VARIABLE(460),
    INCORRECT_SEARCH_SETTINGS(461),
    ALREADY_CREATED_SITTER_INFO(462),
    IMAGE_LOAD_ERROR(463),
    IMAGE_STORE_ERROR(464),
    INCORRECT_IMAGE_TYPE(465);

    private final int value;

    HttpErrorStatus(int value){
        this.value = value;
    }

    public int value(){
        return value;
    }

}
