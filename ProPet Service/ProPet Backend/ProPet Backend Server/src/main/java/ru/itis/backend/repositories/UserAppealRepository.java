package ru.itis.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.backend.models.UserAppeal;

import java.util.List;

public interface UserAppealRepository extends JpaRepository<UserAppeal, Long> {

    List<UserAppeal> findAllByUserId(Long userId);

}
