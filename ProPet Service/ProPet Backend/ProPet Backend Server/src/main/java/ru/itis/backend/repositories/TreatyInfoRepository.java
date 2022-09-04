package ru.itis.backend.repositories;

import ru.itis.backend.models.TreatyInfo;

import java.util.Optional;

public interface TreatyInfoRepository extends CustomRepository<TreatyInfo, Long> {

    Optional<TreatyInfo> findByAccountId(Long accountId);

}
