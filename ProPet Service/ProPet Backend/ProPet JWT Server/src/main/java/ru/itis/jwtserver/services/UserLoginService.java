package ru.itis.jwtserver.services;

import ru.itis.jwtserver.dto.AccessTokenResponse;
import ru.itis.jwtserver.dto.RefreshTokenResponse;
import ru.itis.jwtserver.dto.UserAuthorizationForm;

public interface UserLoginService {

    RefreshTokenResponse login(UserAuthorizationForm emailPasswordDto);
    AccessTokenResponse authenticate(RefreshTokenResponse refreshTokenDto);

}
