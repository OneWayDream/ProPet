package ru.itis.backend.services;

import ru.itis.backend.dto.SitterInfoDto;

public interface SitterInfoService extends CrudService<SitterInfoDto, Long> {

    SitterInfoDto findByUserId(Long userId);

}
