package ru.itis.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.backend.redis.repositories.BlacklistRepository;

@RequiredArgsConstructor
@Service
public class JwtBlacklistServiceImpl implements JwtBlacklistService {

    protected final BlacklistRepository blacklistRepository;

    @Override
    public boolean exists(String token) {
        return blacklistRepository.exists(token);
    }

}
