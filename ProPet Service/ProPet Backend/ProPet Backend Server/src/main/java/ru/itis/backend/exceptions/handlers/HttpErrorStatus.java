package ru.itis.backend.exceptions.handlers;

public enum HttpErrorStatus {

    BANNED_TOKEN(452),
    EXPIRED_TOKEN(454),
    INCORRECT_TOKEN(455),
    EXISTING_LOGIN(456),
    EXISTING_MAIL(457),
    ACCESS_DENIED(458),
    INCORRECT_SEARCH_ORDER(459),
    INCORRECT_SORTING_VARIABLE(460),
    INCORRECT_SEARCH_SETTINGS(461),
    ALREADY_CREATED_SITTER_INFO(462),
    INCORRECT_SEARCH_VARIABLE(466),
    SELF_COMMENT(467),
    EXISTED_COMMENT(468),
    PDF_GENERATION_FAULT(469),
    UNCONFIRMED_APPLY(470),
    NO_TREATY_INFO(471),
    EMPTY_PROFILE_FIELD(472),
    SELF_TREATY(473),
    ALREADY_CREATED_TREATY_INFO(474);

    private final int value;

    HttpErrorStatus(int value){
        this.value = value;
    }

    public int value(){
        return value;
    }

}
