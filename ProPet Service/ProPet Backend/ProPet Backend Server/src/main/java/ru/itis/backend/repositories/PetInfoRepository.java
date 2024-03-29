package ru.itis.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.backend.models.PetInfo;

import java.util.List;

public interface PetInfoRepository extends CustomRepository<PetInfo, Long> {

    List<PetInfo> findAllByAccountId(Long id);

}
