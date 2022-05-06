package ru.itis.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.backend.models.SitterInfo;

import java.util.Optional;

public interface SitterInfoRepository extends JpaRepository<SitterInfo, Long> {

    Optional<SitterInfo> findByAccountId(Long accountId);

}
