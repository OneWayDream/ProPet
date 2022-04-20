package ru.itis.jwtserver.redis.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.jwtserver.models.DataAccessUser;
import ru.itis.jwtserver.redis.models.RedisUser;
import ru.itis.jwtserver.redis.repositories.RedisUsersRepository;
import ru.itis.jwtserver.repositories.UsersRepository;
import ru.itis.jwtserver.services.JwtBlacklistService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RedisUsersServiceImpl implements RedisUsersService {

    protected final UsersRepository usersRepository;
    protected final JwtBlacklistService blacklistService;
    protected final RedisUsersRepository redisUsersRepository;

    @Override
    public void addTokenToUser(DataAccessUser user, String token) {
        String redisId = user.getRedisId();

        RedisUser redisUser;
        if (redisId != null) {
            redisUser = redisUsersRepository.findById(redisId).orElseThrow(IllegalArgumentException::new);
            if (redisUser.getTokens() == null) {
                redisUser.setTokens(new ArrayList<>());
            }
            redisUser.getTokens().add(token);
        } else {
            redisUser = RedisUser.builder()
                    .userId(user.getId())
                    .tokens(Collections.singletonList(token))
                    .build();
        }
        redisUsersRepository.save(redisUser);
        user.setRedisId(redisUser.getId());
        usersRepository.save(user);
    }

    @Override
    public void addAllTokensToBlackList(DataAccessUser user) {
        if (user.getRedisId() != null) {
            RedisUser redisUser = redisUsersRepository.findById(user.getRedisId())
                    .orElseThrow(IllegalArgumentException::new);

            List<String> tokens = redisUser.getTokens();
            for (String token : tokens) {
                blacklistService.add(token);
            }
            redisUser.getTokens().clear();
            redisUsersRepository.save(redisUser);
        }
    }
}
