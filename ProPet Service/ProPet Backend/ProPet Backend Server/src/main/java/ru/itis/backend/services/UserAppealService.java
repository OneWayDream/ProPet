package ru.itis.backend.services;

import ru.itis.backend.dto.app.UserAppealDto;

import java.util.List;

public interface UserAppealService extends CrudService<UserAppealDto, Long> {

    List<UserAppealDto> getAllByUserId(Long userId);

}
