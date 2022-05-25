package ru.itis.backend.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.app.PetInfoDto;
import ru.itis.backend.exceptions.persistence.EntityNotExistsException;
import ru.itis.backend.exceptions.persistence.EntityNotFoundException;
import ru.itis.backend.models.PetInfo;
import ru.itis.backend.repositories.PetInfoRepository;
import ru.itis.backend.utils.PropertiesUtils;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetInfoServiceImpl implements PetInfoService {

    @NonNull
    protected PetInfoRepository repository;

    @Override
    public List<PetInfoDto> findAll() {
        return PetInfoDto.from(repository.findAll().stream()
                .filter(entry -> !entry.getIsDeleted())
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(PetInfoDto petInfoDto) {
        try{
            PetInfo entityToDelete = repository.findById(petInfoDto.getId())
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
    public PetInfoDto add(PetInfoDto petInfoDto) {
        PetInfo newEntity = PetInfoDto.to(petInfoDto);
        repository.save(newEntity);
        return PetInfoDto.from(newEntity);
    }

    @Override
    public PetInfoDto findById(Long aLong) {
        return PetInfoDto.from(repository.findById(aLong)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public PetInfoDto update(PetInfoDto petInfoDto) {
        PetInfoDto entity = findById(petInfoDto.getId());
        PropertiesUtils.copyNonNullProperties(petInfoDto, entity);
        PetInfo updatedEntity = repository.save(PetInfoDto.to(entity));
        return PetInfoDto.from(updatedEntity);
    }

    @Override
    public List<PetInfoDto> findAllByUserId(Long userId) {
        return PetInfoDto.from(repository.findAllByAccountId(userId)
                        .stream()
                        .filter(entry -> !entry.getIsDeleted())
                        .collect(Collectors.toList()));
    }
}
