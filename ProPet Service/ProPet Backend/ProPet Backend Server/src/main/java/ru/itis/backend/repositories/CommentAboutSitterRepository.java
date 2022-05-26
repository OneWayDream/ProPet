package ru.itis.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.backend.models.CommentAboutSitter;

import java.util.List;
import java.util.Optional;

public interface CommentAboutSitterRepository extends JpaRepository<CommentAboutSitter, Long> {

    List<CommentAboutSitter> findAllByAccountId(Long accountId);

    @Query(value = "select * from comment_about_sitter INNER JOIN account " +
            "on account.id = comment_about_sitter.account_id " +
            "where comment_about_sitter.is_deleted=false and comment_about_sitter.sitter_info_id=:id " +
            "limit :limit offset :offset",
            nativeQuery = true)
    List<CommentAboutSitter> getSearchPage(@Param("id") Long id, @Param("limit") Integer limit,
                                           @Param("offset") Long offset);

    Optional<CommentAboutSitter> findByAccountIdAndSitterInfoId(Long accountId, Long sitterInfoId);

}
