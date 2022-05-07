package ru.itis.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.itis.backend.models.SitterInfo;

import java.util.Optional;

public interface SitterInfoRepository extends JpaRepository<SitterInfo, Long>,
        PagingAndSortingRepository<SitterInfo, Long> {

    Optional<SitterInfo> findByAccountId(Long accountId);

}
