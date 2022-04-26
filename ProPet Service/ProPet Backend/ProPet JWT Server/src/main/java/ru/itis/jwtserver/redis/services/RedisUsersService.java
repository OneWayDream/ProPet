package ru.itis.jwtserver.redis.services;

import ru.itis.jwtserver.dto.JwtModuleDto;
import ru.itis.jwtserver.dto.JwtUserDto;
import ru.itis.jwtserver.models.JwtUser;

public interface RedisUsersService {

    void addTokenToUser(JwtUserDto user, String token);

    void addAllTokensToBlackList(JwtUser user);

}
