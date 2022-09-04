package ru.itis.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.backend.dto.app.PetInfoDto;
import ru.itis.backend.dto.app.SitterInfoDto;
import ru.itis.backend.dto.app.TreatyInfoDto;
import ru.itis.backend.exceptions.persistence.EntityNotExistsException;
import ru.itis.backend.exceptions.persistence.EntityNotFoundException;
import ru.itis.backend.exceptions.persistence.SitterInfoAlreadyExistsException;
import ru.itis.backend.exceptions.persistence.TreatyInfoAlreadyExistsException;
import ru.itis.backend.models.PetInfo;
import ru.itis.backend.models.SitterInfo;
import ru.itis.backend.models.TreatyInfo;
import ru.itis.backend.repositories.TreatyInfoRepository;
import ru.itis.backend.utils.PropertiesUtils;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TreatyInfoServiceImpl implements TreatyInfoService {

    protected final TreatyInfoRepository repository;

    @Override
    public List<TreatyInfoDto> findAll() {
        return TreatyInfoDto.from(repository.findAll().stream()
                .filter(entry -> !entry.getIsDeleted())
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(TreatyInfoDto treatyInfoDto) {
        try{
            TreatyInfo entityToDelete = repository.findById(treatyInfoDto.getId())
                    .filter(entry -> !entry.getIsDeleted())
                    .orElseThrow(() -> new EntityNotExistsException(" treaty info."));
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
    public TreatyInfoDto add(TreatyInfoDto treatyInfoDto) {
        try {
            TreatyInfo newEntity = TreatyInfoDto.to(treatyInfoDto);
            repository.save(newEntity);
            repository.refresh(newEntity);
            return TreatyInfoDto.from(newEntity);
        } catch (Exception ex){
            try{
                String message = ex.getCause().getCause().getMessage();
                if (message.contains("treaty_info_account_id_key")){
                    throw new TreatyInfoAlreadyExistsException(ex);
                }
            } catch (NullPointerException exception){
                //ignore
            }
            throw ex;
        }
    }

    @Override
    public TreatyInfoDto findById(Long aLong) {
        return TreatyInfoDto.from(repository.findById(aLong)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(() -> new EntityNotFoundException(" treaty info.")));
    }

    @Override
    public TreatyInfoDto update(TreatyInfoDto treatyInfoDto) {
        TreatyInfoDto entity = findById(treatyInfoDto.getId());
        PropertiesUtils.copyNonNullProperties(treatyInfoDto, entity);
        TreatyInfo updatedEntity = repository.save(TreatyInfoDto.to(entity));
        return TreatyInfoDto.from(updatedEntity);
    }

    @Override
    public TreatyInfoDto getByAccountId(Long accountId) {
        return TreatyInfoDto.from(repository.findByAccountId(accountId)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(() -> new EntityNotFoundException(" treaty info.")));
    }

    @Override
    public void deleteByAccountId(Long accountId) {
        try{
            TreatyInfo entityToDelete = repository.findByAccountId(accountId)
                    .filter(entry -> !entry.getIsDeleted())
                    .orElseThrow(() -> new EntityNotExistsException(" treaty info."));
            entityToDelete.setIsDeleted(true);
            repository.save(entityToDelete);
        } catch (Exception ex){
            if (ex instanceof EntityNotExistsException){
                throw ex;
            }
            throw new PersistenceException(ex);
        }
    }
}
