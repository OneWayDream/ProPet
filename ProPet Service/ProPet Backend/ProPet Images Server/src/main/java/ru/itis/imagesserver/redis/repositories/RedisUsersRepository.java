package ru.itis.imagesserver.redis.repositories;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import ru.itis.imagesserver.redis.repositories.models.RedisUser;

public interface RedisUsersRepository extends KeyValueRepository<RedisUser, String> {

}
