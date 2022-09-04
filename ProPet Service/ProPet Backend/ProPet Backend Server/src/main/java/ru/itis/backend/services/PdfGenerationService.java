package ru.itis.backend.services;

import java.nio.file.AccessDeniedException;

public interface PdfGenerationService {

    byte[] generatePetTransferTreaty(Long applyId, Long accountId) throws AccessDeniedException;

}
