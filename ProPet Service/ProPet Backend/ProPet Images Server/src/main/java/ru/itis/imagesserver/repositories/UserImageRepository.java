package ru.itis.imagesserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.imagesserver.models.UserImage;

import java.util.Optional;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {

    Optional<UserImage> findByAccountId(Long accountId);

}
