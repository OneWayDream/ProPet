package ru.itis.jwtserver.services;

import ru.itis.jwtserver.dto.JwtModuleDto;

public interface JwtModuleService extends CrudService<JwtModuleDto, Long> {

    JwtModuleDto findByLogin(String login);

}
