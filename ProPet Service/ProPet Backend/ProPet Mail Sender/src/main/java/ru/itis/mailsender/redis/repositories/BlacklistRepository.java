package ru.itis.mailsender.redis.repositories;

public interface BlacklistRepository {

    void save(String token);

    boolean exists(String token);

}
