package ru.itis.jwtserver.services;

import ru.itis.jwtserver.dto.JwtUserDto;

public interface JwtUserService extends CrudService<JwtUserDto, Long> {

    JwtUserDto findByLogin(String login);
    JwtUserDto findByMail(String mail);

}
