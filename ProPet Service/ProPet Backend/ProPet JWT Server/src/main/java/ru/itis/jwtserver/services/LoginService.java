package ru.itis.jwtserver.services;


import ru.itis.jwtserver.dto.AccessTokenDto;
import ru.itis.jwtserver.dto.LoginPasswordDto;
import ru.itis.jwtserver.dto.RefreshTokenDto;

public interface LoginService {

    RefreshTokenDto login(LoginPasswordDto emailPasswordDto);
    AccessTokenDto authenticate(RefreshTokenDto refreshTokenDto);

}
