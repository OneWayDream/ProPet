package ru.itis.jwtserver.services;

public interface JwtBlacklistService {

    void add(String token);

    boolean exists(String token);

}