package ru.itis.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.backend.models.UserAppeal;

import java.util.List;

public interface UserAppealRepository extends CustomRepository<UserAppeal, Long> {

    List<UserAppeal> findAllByAccountId(Long accountId);

}
