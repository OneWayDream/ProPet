package ru.itis.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.backend.dto.app.PetTransferApplyDto;
import ru.itis.backend.exceptions.pdf.NoTreatyInfoException;
import ru.itis.backend.exceptions.pdf.SelfTreatyException;
import ru.itis.backend.exceptions.persistence.EntityNotExistsException;
import ru.itis.backend.exceptions.persistence.EntityNotFoundException;
import ru.itis.backend.exceptions.registration.LoginAlreadyInUseException;
import ru.itis.backend.exceptions.registration.MailAlreadyInUseException;
import ru.itis.backend.models.PetTransferApply;
import ru.itis.backend.models.PetTransferApplyStatus;
import ru.itis.backend.models.TreatyInfo;
import ru.itis.backend.repositories.PetTransferApplyRepository;
import ru.itis.backend.repositories.TreatyInfoRepository;
import ru.itis.backend.utils.PropertiesUtils;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetTransferApplyServiceImpl implements PetTransferApplyService {

    protected final PetTransferApplyRepository repository;
    protected final TreatyInfoRepository treatyInfoRepository;

    @Override
    public List<PetTransferApplyDto> findAll() {
        return PetTransferApplyDto.from(repository.findAll().stream()
                .filter(entry -> !entry.getIsDeleted())
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(PetTransferApplyDto petTransferApplyDto) {
        try{
            PetTransferApply entityToDelete = repository.findById(petTransferApplyDto.getId())
                    .filter(entry -> !entry.getIsDeleted())
                    .orElseThrow(() -> new EntityNotExistsException(" pet transfer apply."));
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
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public PetTransferApplyDto add(PetTransferApplyDto petTransferApplyDto) {
        if (petTransferApplyDto.getCustomerId().equals(petTransferApplyDto.getPerformerId())){
            throw new SelfTreatyException();
        }
        try{
            PetTransferApply newEntity = PetTransferApplyDto.to(petTransferApplyDto);
            repository.save(newEntity);
            repository.refresh(newEntity);
            return PetTransferApplyDto.from(newEntity);
        } catch (Exception ex){
            try {
                String message = ex.getCause().getCause().getMessage();
                if (message.contains("performer_id") && message.contains("отсутствует в таблице")) {
                    throw new EntityNotExistsException("This performer is not exists");
                }
            } catch (NullPointerException exception) {
                //ignore
            }
            throw ex;
        }

    }

    @Override
    public PetTransferApplyDto findById(Long aLong) {
        return PetTransferApplyDto.from(repository.findById(aLong)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(() -> new EntityNotFoundException(" pet transfer apply.")));
    }

    @Override
    public PetTransferApplyDto update(PetTransferApplyDto petTransferApplyDto) {
        PetTransferApplyDto entity = findById(petTransferApplyDto.getId());
        PropertiesUtils.copyNonNullProperties(petTransferApplyDto, entity);
        PetTransferApply updatedEntity = repository.save(PetTransferApplyDto.to(entity));
        return PetTransferApplyDto.from(updatedEntity);
    }

    @Override
    public List<PetTransferApplyDto> findAllByCustomerId(Long customerId) {
        return PetTransferApplyDto.from(repository.findByCustomerId(customerId).stream()
                .filter(entry -> !entry.getIsDeleted())
                .collect(Collectors.toList()));
    }

    @Override
    public List<PetTransferApplyDto> findAllByPerformerId(Long performerId) {
        return PetTransferApplyDto.from(repository.findByPerformerId(performerId).stream()
                .filter(entry -> !entry.getIsDeleted())
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public PetTransferApplyDto confirmByCustomer(Long applyId, Long customerId) {
        TreatyInfo treatyInfo = treatyInfoRepository.findByAccountId(customerId)
                .filter(entity -> !entity.getIsDeleted())
                .orElseThrow(NoTreatyInfoException::new);
        treatyInfo.checkTreatyFields();
        PetTransferApply apply = repository.findByIdAndCustomerId(applyId, customerId)
                .filter(entity -> !entity.getIsDeleted())
                .orElseThrow(() -> new EntityNotExistsException(" pet transfer apply."));
        apply.setCustomerAgreement(true);
        if (apply.getCustomerAgreement() && apply.getPerformerAgreement()){
            apply.setStatus(PetTransferApplyStatus.CONFIRMED);
        }
        return PetTransferApplyDto.from(repository.save(apply));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public PetTransferApplyDto confirmByPerformer(Long applyId, Long performerId) {
        TreatyInfo treatyInfo = treatyInfoRepository.findByAccountId(performerId)
                .filter(entity -> !entity.getIsDeleted())
                .orElseThrow(NoTreatyInfoException::new);
        treatyInfo.checkTreatyFields();
        PetTransferApply apply = repository.findByIdAndPerformerId(applyId, performerId)
                .filter(entity -> !entity.getIsDeleted())
                .orElseThrow(() -> new EntityNotExistsException(" pet transfer apply."));
        apply.setPerformerAgreement(true);
        if (apply.getCustomerAgreement() && apply.getPerformerAgreement()){
            apply.setStatus(PetTransferApplyStatus.CONFIRMED);
        }
        return PetTransferApplyDto.from(repository.save(apply));
    }

}
