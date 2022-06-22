package ru.itis.imagesserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.imagesserver.models.PetImage;

import java.util.List;
import java.util.Optional;

public interface PetImageRepository extends JpaRepository<PetImage, Long> {

    Optional<PetImage> findByPetId(Long petId);
    List<PetImage> findAllByAccountId(Long accountId);

}
