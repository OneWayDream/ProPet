package ru.itis.backend.services;

import ru.itis.backend.dto.ActivationLinkDto;

public interface ActivationLinksService extends CrudService<ActivationLinkDto, Long> {

    ActivationLinkDto findByLinkValue(String linkValue);

}
