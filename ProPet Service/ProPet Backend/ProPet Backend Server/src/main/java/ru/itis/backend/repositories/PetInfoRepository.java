package ru.itis.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.backend.models.PetInfo;
import ru.itis.backend.models.User;

import java.util.List;

public interface PetInfoRepository extends JpaRepository<PetInfo, Long> {

    List<PetInfo> findAllByUserId(Long id);

}
