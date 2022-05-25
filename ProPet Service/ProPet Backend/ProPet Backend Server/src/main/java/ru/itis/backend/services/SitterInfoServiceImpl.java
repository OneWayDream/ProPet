package ru.itis.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.app.SitterInfoDto;
import ru.itis.backend.exceptions.persistence.EntityNotExistsException;
import ru.itis.backend.exceptions.persistence.EntityNotFoundException;
import ru.itis.backend.exceptions.persistence.SitterInfoAlreadyExistsException;
import ru.itis.backend.models.SitterInfo;
import ru.itis.backend.repositories.SitterInfoRepository;
import ru.itis.backend.utils.PropertiesUtils;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SitterInfoServiceImpl implements SitterInfoService {

    protected final SitterInfoRepository repository;

    @Override
    public List<SitterInfoDto> findAll() {
        return SitterInfoDto.from(repository.findAll().stream()
                .filter(entry -> !entry.getIsDeleted())
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(SitterInfoDto sitterInfoDto) {
        try{
            SitterInfo entityToDelete = repository.findById(sitterInfoDto.getId())
                    .filter(entry -> !entry.getIsDeleted())
                    .orElseThrow(EntityNotExistsException::new);
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
    public SitterInfoDto addRest(SitterInfoDto sitterInfo){
        return SitterInfoDto.from(addSitterInfo(SitterInfoDto.toRest(sitterInfo)));
    }

    @Override
    public SitterInfoDto add(SitterInfoDto sitterInfo){
        SitterInfo newInfo = SitterInfoDto.to(sitterInfo);
        newInfo.setRateOne(0);
        newInfo.setRateTwo(0);
        newInfo.setRateThree(0);
        newInfo.setRateFour(0);
        newInfo.setRateFive(0);
        newInfo.setRating(0.0);
        return SitterInfoDto.from(addSitterInfo(newInfo));
    }

    protected SitterInfo addSitterInfo(SitterInfo newEntity) {
        try {
            return repository.save(newEntity);
        } catch (Exception ex){
            try{
                String message = ex.getCause().getCause().getMessage();
                if (message.contains("sitter_info_account_id_key")){
                    throw new SitterInfoAlreadyExistsException(ex);
                }
            } catch (NullPointerException exception){
                //ignore
            }
            throw ex;
        }
    }

    @Override
    public SitterInfoDto findById(Long aLong) {
        return SitterInfoDto.from(repository.findById(aLong)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public SitterInfoDto update(SitterInfoDto sitterInfo){
        return SitterInfoDto.from(updateSitterInfo(SitterInfoDto.to(sitterInfo)));
    }

    @Override
    public SitterInfoDto updateRest(SitterInfoDto sitterInfo){
        return SitterInfoDto.from(updateSitterInfo(SitterInfoDto.toRest(sitterInfo)));
    }

    protected SitterInfo updateSitterInfo(SitterInfo sitterInfo) {
        SitterInfo entity = repository.findById(sitterInfo.getId())
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new);
        PropertiesUtils.copyNonNullProperties(sitterInfo, entity);
        return repository.save(entity);
    }

    @Override
    public SitterInfoDto findByUserId(Long userId) {
        return SitterInfoDto.from(repository.findByAccountId(userId)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

}
