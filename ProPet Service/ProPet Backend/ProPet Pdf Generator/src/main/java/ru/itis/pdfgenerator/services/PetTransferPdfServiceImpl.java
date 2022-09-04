package ru.itis.pdfgenerator.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.pdfgenerator.exceptions.persistence.EntityNotExistsException;
import ru.itis.pdfgenerator.exceptions.persistence.EntityNotFoundException;
import ru.itis.pdfgenerator.models.PetTransferPdf;
import ru.itis.pdfgenerator.repositories.PetTransferPdfRepository;
import ru.itis.pdfgenerator.utils.PropertiesUtils;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetTransferPdfServiceImpl implements PetTransferPdfService {

    protected final PetTransferPdfRepository repository;

    @Override
    public List<PetTransferPdf> findAll() {
        return repository.findAll().stream()
                .filter(entry -> !entry.getIsDeleted())
                .collect(Collectors.toList());
    }

    @Override
    public void delete(PetTransferPdf petTransferPdf) {
        try{
            PetTransferPdf entityToDelete = repository.findById(petTransferPdf.getId())
                    .filter(entry -> !entry.getIsDeleted())
                    .orElseThrow(() -> new EntityNotExistsException(" pet transfer pdf."));
            entityToDelete.setIsDeleted(true);
            repository.save(entityToDelete);
        } catch (Exception ex){
            if (ex instanceof EntityNotExistsException){
                throw ex;
            }
            throw new PersistenceException(ex);
        }
    }

    @Override
    public PetTransferPdf add(PetTransferPdf petTransferPdf) {
        return repository.save(petTransferPdf);
    }

    @Override
    public PetTransferPdf findById(Long aLong) {
        return repository.findById(aLong)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(() -> new EntityNotFoundException(" pet transfer pdf."));
    }

    @Override
    public PetTransferPdf update(PetTransferPdf petTransferPdf) {
        PetTransferPdf entity = repository.findById(petTransferPdf.getId())
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(() -> new EntityNotFoundException(" pet transfer pdf."));
        PropertiesUtils.copyNonNullProperties(petTransferPdf, entity);
        return repository.save(entity);
    }

    @Override
    public PetTransferPdf findByApplyId(Long applyId) {
        return repository.findByApplyId(applyId)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(() -> new EntityNotFoundException(" pet transfer pdf."));
    }
}
