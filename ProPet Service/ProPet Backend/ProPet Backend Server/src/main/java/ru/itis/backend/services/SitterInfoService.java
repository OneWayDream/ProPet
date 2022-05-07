package ru.itis.backend.services;

import ru.itis.backend.dto.SitterInfoDto;
import ru.itis.backend.entities.SortingOrder;
import ru.itis.backend.entities.SortingVariable;

import java.util.List;

public interface SitterInfoService extends CrudService<SitterInfoDto, Long> {

    SitterInfoDto findByUserId(Long userId);
    List<SitterInfoDto> getSearchPage(Integer page, Integer size, SortingVariable sortedBy, SortingOrder order);

}
