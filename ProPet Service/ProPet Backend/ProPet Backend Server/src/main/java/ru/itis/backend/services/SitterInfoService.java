package ru.itis.backend.services;

import ru.itis.backend.dto.app.SitterInfoDto;
import ru.itis.backend.entities.SortingOrder;
import ru.itis.backend.entities.SortingVariable;

import java.util.List;

public interface SitterInfoService extends CrudService<SitterInfoDto, Long> {

    SitterInfoDto addRest(SitterInfoDto sitterInfo);
    SitterInfoDto updateRest(SitterInfoDto sitterInfo);

    SitterInfoDto findByUserId(Long userId);

}
