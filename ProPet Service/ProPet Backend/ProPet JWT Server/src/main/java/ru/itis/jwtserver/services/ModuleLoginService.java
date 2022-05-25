package ru.itis.jwtserver.services;


import ru.itis.jwtserver.dto.AccessTokenResponse;
import ru.itis.jwtserver.dto.ModuleAuthorizationForm;
import ru.itis.jwtserver.dto.RefreshTokenResponse;

public interface ModuleLoginService {

    RefreshTokenResponse login(ModuleAuthorizationForm emailPasswordDto);
    AccessTokenResponse authenticate(RefreshTokenResponse refreshTokenDto);

}
