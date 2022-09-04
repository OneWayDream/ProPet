package ru.itis.imagesserver.services;

public interface JwtBlacklistService {

    boolean exists(String token);

}