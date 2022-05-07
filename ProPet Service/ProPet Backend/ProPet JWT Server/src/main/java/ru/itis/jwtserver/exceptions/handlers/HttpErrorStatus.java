package ru.itis.jwtserver.exceptions.handlers;


public enum HttpErrorStatus {

    BANNED_TOKEN(452),
    BANNED_USER(453),
    EXPIRED_TOKEN(454),
    INCORRECT_TOKEN(455),
    INCORRECT_USER_DATA(456),
    ACCESS_DENIED(457);

    private final int value;

    HttpErrorStatus(int value){
        this.value = value;
    }

    public int value(){
        return value;
    }

}
