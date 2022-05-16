package ru.itis.backend.services;

import ru.itis.backend.dto.app.PetInfoDto;

import java.util.List;

public interface PetInfoService extends CrudService<PetInfoDto, Long> {

    List<PetInfoDto> findAllByUserId(Long userId);

}
