package ru.itis.jwtserver.redis.repositories;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import ru.itis.jwtserver.redis.repositories.models.RedisUser;

public interface RedisUsersRepository extends KeyValueRepository<RedisUser, String> {

}
