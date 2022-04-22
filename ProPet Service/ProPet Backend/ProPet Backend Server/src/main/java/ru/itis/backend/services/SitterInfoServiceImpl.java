package ru.itis.backend.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.SitterInfoDto;
import ru.itis.backend.exceptions.EntityNotExistsException;
import ru.itis.backend.exceptions.EntityNotFoundException;
import ru.itis.backend.models.SitterInfo;
import ru.itis.backend.repositories.SitterInfoRepository;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SitterInfoServiceImpl implements SitterInfoService {

    @NonNull
    protected SitterInfoRepository repository;

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
    public SitterInfoDto add(SitterInfoDto sitterInfoDto) {
        SitterInfo newEntity = SitterInfoDto.to(sitterInfoDto);
        repository.save(newEntity);
        return SitterInfoDto.from(newEntity);
    }

    @Override
    public SitterInfoDto findById(Long aLong) {
        return SitterInfoDto.from(repository.findById(aLong)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public SitterInfoDto update(SitterInfoDto sitterInfoDto) {
        findById(sitterInfoDto.getId());
        SitterInfo updatedEntity = repository.save(SitterInfoDto.to(sitterInfoDto));
        return SitterInfoDto.from(updatedEntity);
    }

    @Override
    public SitterInfoDto findByUserId(Long userId) {
        return SitterInfoDto.from(repository.findByUserId(userId)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

}
