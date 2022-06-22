package ru.itis.mailsender.exceptions.handlers;

public enum HttpErrorStatus {

    BANNED_TOKEN(452),
    EXPIRED_TOKEN(454),
    INCORRECT_TOKEN(455),
    ACCESS_DENIED(458);

    private final int value;

    HttpErrorStatus(int value){
        this.value = value;
    }

    public int value(){
        return value;
    }

}
