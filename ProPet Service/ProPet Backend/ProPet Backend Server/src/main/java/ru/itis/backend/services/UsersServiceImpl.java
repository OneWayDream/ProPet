package ru.itis.backend.services;

import org.springframework.stereotype.Service;
import ru.itis.backend.dto.UserDto;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    @Override
    public List<UserDto> findAll() {
        return null;
    }

    @Override
    public void delete(UserDto userDto) {

    }

    @Override
    public UserDto add(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto findById(Long aLong) {
        return null;
    }

    @Override
    public UserDto update(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto activateUser(String linkId) {
        return null;
    }

    @Override
    public void banUser(Long userId) {

    }
}
