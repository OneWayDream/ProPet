package ru.itis.backend.services;

import ru.itis.backend.dto.UserDto;

public interface UsersService extends CrudService<UserDto, Long> {

    UserDto activateUser(String linkId);
    void banUser(Long userId);

}
