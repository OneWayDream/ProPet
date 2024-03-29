package ru.itis.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.backend.models.ActivationLink;

import java.util.Optional;

public interface ActivationLinkRepository extends CustomRepository<ActivationLink, Long> {

    Optional<ActivationLink> findByLinkValue(String linkValue);
    Optional<ActivationLink> findByAccountId(Long accountId);

}
