package ru.itis.backend.redis.repositories;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import ru.itis.backend.redis.repositories.models.RedisUser;

public interface RedisUsersRepository extends KeyValueRepository<RedisUser, String> {

}
