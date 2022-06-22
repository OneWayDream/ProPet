package ru.itis.pdfgenerator.services;

import ru.itis.pdfgenerator.models.PetTransferPdf;

public interface PetTransferPdfService extends CrudService<PetTransferPdf, Long> {

    PetTransferPdf findByApplyId(Long applyId);

}
