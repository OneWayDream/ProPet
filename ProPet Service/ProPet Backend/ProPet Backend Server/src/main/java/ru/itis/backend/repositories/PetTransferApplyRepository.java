package ru.itis.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.backend.models.PetTransferApply;

import java.util.List;
import java.util.Optional;

public interface PetTransferApplyRepository extends CustomRepository<PetTransferApply, Long> {

    List<PetTransferApply> findByCustomerId(Long customerId);
    List<PetTransferApply> findByPerformerId(Long performerId);
    Optional<PetTransferApply> findByIdAndCustomerId(Long id, Long customerId);
    Optional<PetTransferApply> findByIdAndPerformerId(Long id, Long performerId);

}
