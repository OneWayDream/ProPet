package ru.itis.backend.services;

import ru.itis.backend.dto.rest.ActivationLinkDto;

public interface ActivationLinkService extends CrudService<ActivationLinkDto, Long> {

    ActivationLinkDto findByLinkValue(String linkValue);
    ActivationLinkDto findByAccountId(Long id);

}
