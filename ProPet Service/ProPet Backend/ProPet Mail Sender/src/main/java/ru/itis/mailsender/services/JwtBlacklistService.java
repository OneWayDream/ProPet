package ru.itis.mailsender.services;

public interface JwtBlacklistService {

    boolean exists(String token);

}