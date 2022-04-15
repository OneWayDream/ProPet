package ru.itis.backend.services;

import ru.itis.backend.dto.UserDto;

public interface UsersService extends CrudService<UserDto, Long> {

    UserDto activateUser(String linkValue);
    void banUser(Long userId);
    UserDto findUserByLogin(String login);
    UserDto findUserByMail(String mail);

}
