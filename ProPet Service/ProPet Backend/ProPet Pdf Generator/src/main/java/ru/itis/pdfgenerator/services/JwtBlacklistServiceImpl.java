package ru.itis.pdfgenerator.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.pdfgenerator.redis.repositories.BlacklistRepository;

@RequiredArgsConstructor
@Service
public class JwtBlacklistServiceImpl implements JwtBlacklistService {

    protected final BlacklistRepository blacklistRepository;

    @Override
    public void add(String token) {
        blacklistRepository.save(token);
    }

    @Override
    public boolean exists(String token) {
        return blacklistRepository.exists(token);
    }

}
