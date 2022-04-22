package ru.itis.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.backend.models.AppealAnswer;

public interface AppealAnswerRepository extends JpaRepository<AppealAnswer, Long> {
}
