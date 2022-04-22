package ru.itis.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.backend.models.CommentAboutSitter;

import java.util.List;

public interface CommentAboutSitterRepository extends JpaRepository<CommentAboutSitter, Long> {

    List<CommentAboutSitter> findAllByUserId(Long userId);

}
