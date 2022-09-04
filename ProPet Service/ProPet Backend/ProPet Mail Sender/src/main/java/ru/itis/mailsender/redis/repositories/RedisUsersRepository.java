package ru.itis.mailsender.redis.repositories;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import ru.itis.mailsender.redis.repositories.models.RedisUser;

public interface RedisUsersRepository extends KeyValueRepository<RedisUser, String> {

}
