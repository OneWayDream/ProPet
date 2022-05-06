package ru.itis.backend.services;

public interface JwtBlacklistService {

    boolean exists(String token);

}