package ru.itis.backend.services;

import ru.itis.backend.dto.app.PetTransferApplyDto;

import java.util.List;

public interface PetTransferApplyService extends CrudService<PetTransferApplyDto, Long> {

    List<PetTransferApplyDto> findAllByCustomerId(Long customerId);
    List<PetTransferApplyDto> findAllByPerformerId(Long performerId);
    PetTransferApplyDto confirmByCustomer(Long applyId, Long customerId);
    PetTransferApplyDto confirmByPerformer(Long applyId, Long performerId);

}
