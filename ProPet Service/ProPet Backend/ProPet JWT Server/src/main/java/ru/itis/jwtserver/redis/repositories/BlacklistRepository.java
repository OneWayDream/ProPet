package ru.itis.jwtserver.redis.repositories;

public interface BlacklistRepository {

    void save(String token);

    boolean exists(String token);

}
