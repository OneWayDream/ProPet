package ru.itis.jwtserver.redis.services;

import ru.itis.jwtserver.models.DataAccessUser;

public interface RedisUsersService {

    void addTokenToUser(DataAccessUser user, String token);

    void addAllTokensToBlackList(DataAccessUser user);

}
