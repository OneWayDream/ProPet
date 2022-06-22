package ru.itis.backend.services;

import ru.itis.backend.dto.app.TreatyInfoDto;

public interface TreatyInfoService extends CrudService<TreatyInfoDto, Long> {

    TreatyInfoDto getByAccountId(Long accountId);
    void deleteByAccountId(Long accountId);

}
