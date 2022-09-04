package ru.itis.imagesserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.imagesserver.redis.repositories.BlacklistRepository;

@RequiredArgsConstructor
@Service
public class JwtBlacklistServiceImpl implements JwtBlacklistService {

    protected final BlacklistRepository blacklistRepository;

    @Override
    public boolean exists(String token) {
        return blacklistRepository.exists(token);
    }

}
