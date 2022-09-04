package ru.itis.pdfgenerator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.pdfgenerator.models.PetTransferPdf;

import java.util.Optional;

public interface PetTransferPdfRepository extends JpaRepository<PetTransferPdf, Long> {

    Optional<PetTransferPdf> findByApplyId(Long applyId);

}
